package com.by.service;

import java.util.List;
import java.util.Optional;

import com.by.json.MemberDetailJson;
import com.by.model.Member;
import com.by.model.MemberDetail;

public interface MemberDetailService {
    Optional<MemberDetail> update(MemberDetail detail);

    Optional<MemberDetail> findByMember(Member m);

    MemberDetail save(MemberDetail detail);

    MemberDetail findAuditByRevision(Long id, int revision);

    List<MemberDetail> findAllAuditRevision(Long id);

	Optional<MemberDetail> update(Member member, MemberDetailJson json);
}
