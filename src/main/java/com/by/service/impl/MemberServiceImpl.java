package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.Member;
import com.by.repository.MemberRepository;
import com.by.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Optional<Member> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Member> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Member save(Member member) {
		return repository.save(member);
	}

	@Override
	public Optional<Member> countByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Member> update(Member member) {
		return repository.findById(member.getId()).map(i->{
			i.setPassword(member.getPassword());
			return i;
		});
	}

}
