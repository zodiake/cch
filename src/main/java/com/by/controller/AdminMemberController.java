package com.by.controller;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.json.UpdateScoreJson;
import com.by.model.*;
import com.by.security.UserContext;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-9.
 */
@Controller
@RequestMapping("/admin/members")
public class AdminMemberController extends BaseController {
    private final String EDIT = "admin/member/edit";
    private final String CREATE = "admin/member/create";
    private final Menu subMenu = new Menu(2);
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberStaticsService staticsService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private CardService cardService;
    @Autowired
    private TradingService tradingService;
    @Autowired
    private ScoreHistoryService scoreHisotryService;
    @Autowired
    private ParkingHistoryService parkingHistoryService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private GiftCouponMemberService giftCouponMemberService;
    @Autowired
    private ShopCouponMemberService shopCouponMemberService;
    @Autowired
    private ParkingCouponService parkingCouponService;

    @ModelAttribute("cards")
    public List<Card> findAllCard() {
        return cardService.findAllCache().stream().filter(i -> i.getValid().equals(ValidEnum.VALID))
                .collect(Collectors.toList());
    }

    // 列表界面
    @RequestMapping(method = RequestMethod.GET)
    public String firstPage(AdminMemberForm form, Model uiModel,
                            @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberJson> members = memberService.findAll(form, pageable, ValidEnum.VALID);
        uiModel.addAttribute("lists", members);
        uiModel.addAttribute("last", computeLastPage(members.getTotalPages()));
        uiModel.addAttribute("form", form);
        addMenu(uiModel);
        return "admin/member/lists";
    }

    @RequestMapping(value = "/{card}/member", params = "form", method = RequestMethod.GET)
    public String cardForm(@PathVariable("card") int cardId, Model uiModel) {
        Member member = new Member();
        member.setCard(new Card(cardId));
        member.setMemberDetail(new MemberDetail());
        uiModel.addAttribute("member", member);
        return CREATE;
    }

    @RequestMapping(value = "/{card}/member", params = "form", method = RequestMethod.POST)
    public String create(@PathVariable("card") int card, @Valid @ModelAttribute("member") Member member,
                         BindingResult result, Model uiModel, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            uiModel.addAttribute("card", card);
            uiModel.addAttribute("message", failMessage(messageSource));
            addMenu(uiModel);
        }
        member.setCard(new Card(card));
        Member m = memberService.save(member);
        redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
        addMenu(uiModel);
        return "redirect:/admin/members/" + m.getId() + "?edit";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(AdminMemberForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MemberJson> results = memberService.findAll(form, pageable, ValidEnum.VALID);
        uiModel.addAttribute("results", results);
        return new Success<>(results);
    }

    @RequestMapping(value = "/{id}", params = "edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model uiModel) {
        Member member = memberService.findOne(id);
        if (member == null)
            throw new NotFoundException();
        uiModel.addAttribute("member", member);
        addMenu(uiModel);
        return EDIT;
    }

    @RequestMapping(value = "/score", method = RequestMethod.PUT)
    @ResponseBody
    public Status updateScore(@RequestBody UpdateScoreJson json) {
        Member member = memberService.addScore(new Member(json.getId()), json.getScore(), "admin",
                ScoreHistoryEnum.ADMIN);
        return new Success<>(member);
    }

    @RequestMapping(value = "/{id}/validate", method = RequestMethod.PUT)
    @ResponseBody
    public Status validate(@PathVariable("id") Long id) {
        User user = userContext.getCurrentUser();
        Member member = memberService.validateOrInValidate(new Member(id), user.getName());
        return new Success<>(new MemberJson(member));
    }

    @RequestMapping(value = "/{id}/use", method = RequestMethod.GET)
    @ResponseBody
    public Success<MemberStatics> consume(@PathVariable("id") Long id) {
        Member member = new Member(id);
        return new Success<>(staticsService.findOne(member));
    }

    @RequestMapping(value = "/{id}/trading", method = RequestMethod.GET)
    @ResponseBody
    public Status trading(@PathVariable("id") Long id,
                          @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(tradingService.findByMember(new Member(id), pageable));
    }

    @RequestMapping(value = "/{id}/score", method = RequestMethod.GET)
    @ResponseBody
    public Status score(@PathVariable("id") Long id,
                        @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(scoreHisotryService.findByMemberJson(new Member(id), pageable));
    }

    @RequestMapping(value = "/{id}/parking", method = RequestMethod.GET)
    @ResponseBody
    public Status parking(@PathVariable("id") Long id,
                          @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(parkingHistoryService.findByMember(new Member(id), pageable));
    }

    @RequestMapping(value = "/{id}/giftCoupons", method = RequestMethod.GET)
    @ResponseBody
    public Status giftCoupons(@PathVariable("id") Long id,
                              @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(giftCouponMemberService.findByMember(new Member(id), pageable));
    }

    @RequestMapping(value = "/{id}/shopCoupons", method = RequestMethod.GET)
    @ResponseBody
    public Status shopCoupons(@PathVariable("id") Long id,
                              @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "exchangedTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(shopCouponMemberService.findByMember(new Member(id), pageable));
    }

    @RequestMapping(value = "/{id}/parkingCoupons", method = RequestMethod.GET)
    @ResponseBody
    public Status parkingCoupons(@PathVariable("id") Long id,
                                 @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE) Pageable pageable) {

        return new Success<>(parkingCouponService.findByMemberHistory(new Member(id), pageable));
    }

    @RequestMapping(value = "/duplicate", method = RequestMethod.GET)
    @ResponseBody
    public String duplicate(@RequestParam("name") String name) {
        Long count = memberService.countByName(name);
        if (count > 0) {
            return "false";
        } else {
            return "true";
        }
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
