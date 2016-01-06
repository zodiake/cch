package com.by.service;

import com.by.json.MemberDetailJson;
import com.by.model.Member;
import com.by.model.MemberDetail;

import java.util.List;

public interface MemberDetailService {
    Member update(Long memberId, MemberDetailJson detail);

    MemberDetail findByMember(Member m);

    MemberDetail save(MemberDetail detail);

    MemberDetail findAuditByRevision(Long id, int revision);

    List<MemberDetail> findAllAuditRevision(Long id);

}
