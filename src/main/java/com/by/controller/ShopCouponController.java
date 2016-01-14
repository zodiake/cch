package com.by.controller;

import static com.by.SpringExtension.SpringExtProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Fail;
import com.by.exception.MemberNotValidException;
import com.by.exception.NotFoundException;
import com.by.exception.PasswordNotMatchException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.json.CouponTemplateJson;
import com.by.json.ExchangeCouponJson;
import com.by.json.ShopCouponJson;
import com.by.json.ShopCouponMemberJson;
import com.by.message.ShopCouponMessage;
import com.by.model.Member;
import com.by.model.ShopCoupon;
import com.by.service.MemberService;
import com.by.service.ShopCouponMemberService;
import com.by.service.ShopCouponService;
import com.by.typeEnum.ValidEnum;
import com.by.utils.FailBuilder;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import scala.concurrent.duration.Duration;

/**
 * Created by yagamai on 15-12-8.
 */
@Controller
@RequestMapping(value = "/api/shopCoupons")
public class ShopCouponController implements UtilContoller {
	private ActorSystem system;
	private ActorRef ref;
	private ShopCouponService shopCouponService;
	private ShopCouponMemberService shopCouponMemberService;
	private MemberService memberService;
	private ShaPasswordEncoder passwordEncoder;

	@Autowired
	public ShopCouponController(ApplicationContext ctx, ActorSystem system, ShopCouponService shopCouponService,
			ShopCouponMemberService shopCouponMemberService, MemberService memberService) {
		this.system = system;
		this.ref = system.actorOf(SpringExtProvider.get(system).props("ShopCouponActor"), "shopCouponActor");
		this.shopCouponService = shopCouponService;
		this.shopCouponMemberService = shopCouponMemberService;
		this.memberService = memberService;
	}

	// 可以兑换的卡券列表
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Status list(HttpServletRequest request,
			@PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Direction.DESC) Pageable pageable) {
		Member m = (Member) request.getAttribute("member");
		Member member = memberService.findOneCache(m.getId());
		if (!isValidMember(member)) {
			throw new MemberNotValidException();
		}
		Page<ShopCoupon> coupons = shopCouponService.findByValid(ValidEnum.VALID, pageable);
		List<ShopCouponJson> results = coupons.getContent().stream().map(i -> {
			ShopCouponJson json = new ShopCouponJson(i);
			json.setShopName(i.getShop().getName());
			return json;
		}).collect(Collectors.toList());
		return new Success<>(new PageImpl<>(results, pageable, coupons.getTotalElements()));
	}

	// 用户兑换到的优惠券列表
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	@ResponseBody
	public Status couponList(HttpServletRequest request,
			@PageableDefault(page = 0, size = 10, sort = "exchangedTime", direction = Direction.DESC) Pageable pageable) {
		Member member = (Member) request.getAttribute("member");
		Member m = memberService.findOneCache(member.getId());
		if (!isValidMember(m)) {
			throw new MemberNotValidException();
		}
		Page<ShopCouponMemberJson> list = shopCouponMemberService.findByMemberAndValid(member, pageable);
		return new Success<>(list);
	}

	// 兑换卡券
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Status exchangeCoupon(HttpServletRequest request, @Valid @RequestBody ExchangeCouponJson json,
			BindingResult result) {
		Member m = (Member) request.getAttribute("member");
		Member member = memberService.findOneCache(m.getId());
		if (!isValidMember(member)) {
			throw new MemberNotValidException();
		}
		if (result.hasErrors()) {
			return FailBuilder.buildFail(result);
		}

		if (!StringUtils.isEmpty(json.getPassword())) {
			if (!passwordEncoder.encodePassword(json.getPassword(), null)
					.equals(member.getMemberDetail().getPassword()))
				throw new PasswordNotMatchException();
		}
		ShopCoupon coupon = shopCouponService.findOne(json.getId());
		if (coupon == null)
			throw new NotFoundException();
		ShopCouponMessage message = new ShopCouponMessage(coupon, member, json.getTotal());

		final Inbox inbox = Inbox.create(system);
		inbox.send(ref, message);
		try {
			String code = (String) inbox.receive(Duration.create(2, TimeUnit.SECONDS));
			if (code.equals("success"))
				return new Success<String>("success");
			return new Fail(code);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return new Fail("system error");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Status detail(@PathVariable("id") int id) {
		ShopCoupon shopCoupon = shopCouponService.findOneCache(id);
		if (shopCoupon == null)
			throw new NotFoundException();
		return new Success<>(new CouponTemplateJson(shopCoupon));
	}

}
