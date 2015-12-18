package com.by.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import com.by.exception.*;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.json.ExchangeCouponJson;
import com.by.message.PreferentialCouponMessage;
import com.by.model.Member;
import com.by.model.PreferentialCoupon;
import com.by.service.CouponService;
import com.by.service.MemberService;
import com.by.service.PreferentialCouponMemberService;
import com.by.service.PreferentialCouponService;
import com.by.typeEnum.ValidEnum;
import com.by.utils.FailBuilder;
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
import org.springframework.web.bind.annotation.*;
import scala.concurrent.duration.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static com.by.SpringExtension.SpringExtProvider;

@Controller
@RequestMapping(value = "/api/preferentialCoupons")
public class PreferentialCouponController extends BaseController {
    private ApplicationContext ctx;
    private ActorSystem system;
    private ActorRef ref;
    private PreferentialCouponService preferentialCouponService;
    private PreferentialCouponMemberService preferentialCouponMemberService;
    private MemberService memberService;
    private CouponService couponService;
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    public PreferentialCouponController(ApplicationContext ctx, PreferentialCouponService preferentialCouponService,
                                        MemberService memberService, CouponService couponService,
                                        PreferentialCouponMemberService preferentialCouponMemberService, ShaPasswordEncoder passwordEncoder) {
        this.ctx = ctx;
        this.system = ctx.getBean(ActorSystem.class);
        this.ref = system.actorOf(SpringExtProvider.get(system).props("PreferentialCouponActor"), "couponActor");
        this.preferentialCouponService = preferentialCouponService;
        this.memberService = memberService;
        this.couponService = couponService;
        this.preferentialCouponMemberService = preferentialCouponMemberService;
        this.passwordEncoder = passwordEncoder;
    }

    // 可以兑换的卡券列表
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Success<Page<CouponTemplateJson>> list(HttpServletRequest request,
                                                  @PageableDefault(page = 0, size = 10, sort = "sortOrder", direction = Direction.DESC) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        isValidMember(memberService, member);
        Page<PreferentialCoupon> coupons = preferentialCouponService.findByValid(ValidEnum.VALID, pageable);
        List<CouponTemplateJson> results = coupons.getContent()
                .stream()
                .filter(i -> {
                    return couponService.isValidCoupon(i);
                })
                .map(i -> {
                    return new CouponTemplateJson(i);
                })
                .collect(Collectors.toList());
        return new Success<>(new PageImpl<>(results, pageable, coupons.getTotalElements()));
    }

    // 兑换卡券
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Status exchangeCoupon(HttpServletRequest request, @Valid @RequestBody ExchangeCouponJson json,
                                 BindingResult result) {
        Member m = (Member) request.getAttribute("member");
        isValidMember(memberService, m);
        if (result.hasErrors()) {
            return FailBuilder.buildFail(result);
        }
        Member member = memberService.findOneCache(m.getId());
        if (!StringUtils.isEmpty(json.getPassword())) {
            if (!passwordEncoder.encodePassword(json.getPassword(), null).equals(member.getMemberDetail().getPassword()))
                throw new PasswordNotMatchException();
        }
        PreferentialCoupon coupon = preferentialCouponService.findOne(json.getId());
        if (coupon == null)
            throw new NotFoundException();
        PreferentialCouponMessage message = new PreferentialCouponMessage(coupon, member, json.getTotal());

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


    // 用户兑换到的优惠券列表
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ResponseBody
    public Status couponList(HttpServletRequest request,
                             @PageableDefault(page = 0, size = 10, sort = "couponEndTime", direction = Direction.DESC) Pageable pageable) {
        Member member = (Member) request.getAttribute("member");
        isValidMember(memberService, member);
        List<CouponJson> result = preferentialCouponMemberService.findByMember(member, pageable);
        return new Success<>(result);
    }

    // 详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Status detail(@PathVariable("id") Long id) {
        PreferentialCoupon coupon = preferentialCouponService.findOneCache(id);
        if (coupon == null)
            throw new NotFoundException();
        return new Success<>(new CouponJson(coupon));
    }
}
