package com.by.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.by.model.Member;
import com.by.model.MemberDetail;

public interface MemberDetailRepository extends CrudRepository<MemberDetail, Long> {
	public Optional<MemberDetail> findById(Long id);

	public MemberDetail findByMember(Member member);
}
