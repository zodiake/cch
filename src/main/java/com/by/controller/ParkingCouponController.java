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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
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
public class ParkingCouponController implements UtilContoller{
    private final String PASSWORD_ERROR_MESSAGE = "密码错误";
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
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }
        if (!StringUtils.isEmpty(json.getPassword())) {
            if (!passwordEncoder.encodePassword(json.getPassword(), null)
                    .equals(member.getMemberDetail().getPassword())) {
                result.addError(new FieldError("member", "password", PASSWORD_ERROR_MESSAGE));
            }
        }
        ParkingCoupon coupon = new ParkingCoupon(json.getId());
        ParkingCouponMessage message = new ParkingCouponMessage(coupon, member, json.getTotal());

        final Inbox inbox = Inbox.create(system);
        inbox.send(ref, message);
        try {
            String code = (String) inbox.receive(Duration.create(2, TimeUnit.SECONDS));
            if (code.equals("success"))
                return new Success<>("success");
            return new Fail(code);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return new Fail("system error");
    }

    // 使用停车券
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Status useCoupon(HttpServletRequest request, @RequestBody UseCouponJson json, BindingResult result) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }

        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        parkingCouponMemberService.useCoupon(member, new ParkingCoupon(json.getCouponId()), json.getTotal(),
                json.getLicense());
        return new Status("success");
    }

    // 可以兑换的停车券模板列表
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Status list(HttpServletRequest request,
                       @PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }
        List<CouponTemplateJson> coupons = parkingCouponService.findByValid(ValidEnum.VALID, pageable).getContent()
                .stream().filter(i -> couponService.isValidCoupon(i)).map(CouponTemplateJson::new)
                .collect(Collectors.toList());
        return new Success<>(coupons);
    }

    // 用户兑换到的停车券列表
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status couponList(HttpServletRequest request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Member m = (Member) request.getAttribute("member");
        Member member = memberService.findOneCache(m.getId());
        if (!isValidMember(member)) {
            throw new MemberNotValidException();
        }

        List<CouponJson> list = parkingCouponMemberService.findByMember(member, pageable);
        return new Success<>(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Status detail(@PathVariable("id") Long id) {
        ParkingCoupon coupon = parkingCouponService.findOneCache(id);
        if (coupon == null)
            throw new NotFoundException();
        return new Success<>(new CouponTemplateJson(coupon));
    }
}
