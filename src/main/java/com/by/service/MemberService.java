package com.by.service;

import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.model.Card;
import com.by.model.Member;
import com.by.model.ScoreAddHistory;
import com.by.typeEnum.ScoreHistoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member findOne(Long id);

    Member findOneCache(Long id);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Long countByName(String name);

    Member save(Member member);

    Member minusScore(Member member, int total, String reason, ScoreHistoryEnum type);

    Member addScore(Member member, int total, String reason, ScoreHistoryEnum type);

    List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total);

    Page<MemberJson> findAll(AdminMemberForm form, Pageable pageable);

    Page<Member> findFirstPage(int size);

    boolean isValidMember(Member member);

    Long countByCard(Card card);

    Member validateOrInValidate(Member member, String name);
}
