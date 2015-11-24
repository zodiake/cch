package com.by.service;

import java.util.Optional;

import com.by.model.Member;
import com.by.model.MemberDetail;

public interface MemberDetailService {
    public Optional<MemberDetail> update(MemberDetail detail);

    public Optional<MemberDetail> findByMember(Member m);

    public MemberDetail save(MemberDetail detail);

    public MemberDetail findAuditByRevision(Long id, int revision);
}
