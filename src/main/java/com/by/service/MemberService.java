package com.by.service;

import java.util.Optional;

import com.by.model.Member;

public interface MemberService {
	public Optional<Member> findById(Long id);
	
	public Optional<Member> findByName(String name);
	
	public Optional<Member> countByName(String name);
	
	public Member save(Member member);
}
