package com.by.controller;

import com.by.exception.MemberNotValidException;
import com.by.model.Member;
import com.by.service.MemberService;
import com.by.typeEnum.ValidEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yagamai on 15-12-16.
 */
@Component
public class BaseController {
    @Autowired
    private MemberService memberService;

    protected void isValidMember(Member member) {
        Member cacheMember = memberService.findOneCache(member.getId());
        if (!cacheMember.getValid().equals(ValidEnum.VALID)) {
            throw new MemberNotValidException();
        }
    }
}
