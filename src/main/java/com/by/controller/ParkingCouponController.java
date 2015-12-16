package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.by.exception.*;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.json.ExchangeCouponJson;
import com.by.json.UseCouponJson;
import com.by.message.ParkingCouponMessage;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.service.ParkingCouponMemberService;
import com.by.service.ParkingCouponService;
import com.by.typeEnum.ValidEnum;
import com.by.utils.FailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.concurrent.duration.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static com.by.SpringExtension.SpringExtProvider;

/**
 * Created by yagamai on 15-12-3.
 */
@Controller
@RequestMapping(value = "/api/parkingCoupons")
public class ParkingCouponController extends BaseController {
    private ApplicationContext ctx;
    private CouponService couponService;
    private ParkingCouponService parkingCouponService;
    private ActorSystem system;
    private ActorRef ref;
    private MemberService memberService;
    private ParkingCouponMemberService parkingCouponMemberService;
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    public ParkingCouponController(ApplicationContext ctx, CouponService couponService,
                                   ParkingCouponService parkingCouponService, ShaPasswordEncoder passwordEncoder, MemberService memberService,
                                   ParkingCouponMemberService parkingCouponMemberService) {
        this.ctx = ctx;
        this.couponService = couponService;
        this.parkingCouponService = parkingCouponService;
        this.memberService = memberService;
        this.parkingCouponMemberService = parkingCouponMemberService;
        this.passwordEncoder = passwordEncoder;
        system = ctx.getBean(ActorSystem.class);
        ref = system.actorOf(SpringExtProvider.get(system).props("ParkingCouponActor"), "parkingCouponActor");
    }

    // 兑换停车券
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Status exchangeParkingCoupon(HttpServletRequest request, @Valid @RequestBody ExchangeCouponJson json,
                                        BindingResult result) {
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        Member m = (Member) request.getAttribute("member");
        isValidMember(memberService, m);
        Member member = memberService.findOne(m.getId());
        if (!StringUtils.isEmpty(json.getPassword())) {
            if (!passwordEncoder.encodePassword(json.getPassword(), null).equals(member.getPassword()))
                throw new PasswordNotMatchException();
        }
        ParkingCoupon coupon = parkingCouponService.findOne(json.getId());
        ParkingCouponMessage message = new ParkingCouponMessage(coupon, member, json.getTotal());

        validateCoupon(member, coupon, json.getTotal());

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

    // 使用停车券
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Status useCoupon(HttpServletRequest request, @RequestBody UseCouponJson json) {
        Member member = (Member) request.getAttribute("member");
        isValidMember(memberService, member);
        parkingCouponMemberService.useCoupon(member, new ParkingCoupon(json.getCouponId()), json.getTotal(),
                json.getLicense());
        return new Status("success");
    }

    // 可以兑换的停车券模板列表
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Success<List<CouponTemplateJson>> list(HttpServletRequest request,
                                                  @PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        isValidMember(memberService, member);
        List<CouponTemplateJson> coupons = parkingCouponService.findByValid(ValidEnum.VALID, pageable).getContent()
                .stream().filter(i -> {
                    return couponService.isWithinValidDate(i);
                }).map(i -> {
                    return new CouponTemplateJson(i);
                }).collect(Collectors.toList());
        return new Success<>(coupons);
    }

    // 用户兑换到的停车券列表
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status couponList(HttpServletRequest request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        isValidMember(memberService, member);
        List<CouponJson> result = parkingCouponMemberService.findByMember(member, pageable);
        return new Success<>(result);
    }


    private void validateCoupon(Member member, ParkingCoupon coupon, int total) {
        if (member == null)
            throw new MemberNotFoundException();
        if (member.getValid().equals(ValidEnum.INVALID))
            throw new NotValidException();
        if (coupon == null)
            throw new CouponNotFoundException();
        if (coupon.getScore() * total > member.getScore())
            throw new NotEnoughScoreException();
        if (!couponService.isWithinValidDate(coupon))
            throw new NotValidException();
    }

}
