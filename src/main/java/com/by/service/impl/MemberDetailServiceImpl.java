package com.by.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.model.MemberDetail;
import com.by.repository.MemberDetailRepository;
import com.by.service.MemberDetailService;

@Service
@Transactional
public class MemberDetailServiceImpl implements MemberDetailService {
	@Autowired
	private MemberDetailRepository repository;

	@Override
	public Optional<MemberDetail> update(MemberDetail detail) {
		return repository.findByMember(detail.getMember()).map(i -> {
			i.setAddress(detail.getAddress());
			i.setName(detail.getName());
			return i;
		});
	}
}
