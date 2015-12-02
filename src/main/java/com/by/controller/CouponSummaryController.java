package com.by.controller;

import static com.by.SpringExtension.SpringExtProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.json.CouponSummaryJson;
import com.by.message.CouponMessage;
import com.by.model.CouponSummary;
import com.by.model.Member;
import com.by.service.CouponSummaryService;
import com.by.typeEnum.ValidEnum;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import scala.concurrent.duration.Duration;

@Controller
@RequestMapping(value = "/api/coupons")
public class CouponSummaryController {
	private CouponSummaryService service;
	private ApplicationContext ctx;

	private ActorSystem system;
	private ActorRef counter;

	@Autowired
	public CouponSummaryController(CouponSummaryService service, ApplicationContext ctx) {
		this.service = service;
		this.ctx = ctx;
		system = ctx.getBean(ActorSystem.class);
		counter = system.actorOf(SpringExtProvider.get(system).props("CouponActor"), "couponActor");

	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<CouponSummaryJson> list() {
		return service.findByValid(ValidEnum.VALID).stream().map(i -> new CouponSummaryJson(i))
				.collect(Collectors.toList());
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String exchangeCoupon(HttpServletRequest request, @RequestBody CouponSummaryJson json) {
		Member member = (Member) request.getAttribute("member");
		CouponSummary summary = service.findOne(json.getId());
		CouponMessage message = new CouponMessage(member, summary);
		final Inbox inbox = Inbox.create(system);
		inbox.send(counter, message);
		try {
			return (String) inbox.receive(Duration.create(1, TimeUnit.SECONDS));
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return "haha";
	}
}
