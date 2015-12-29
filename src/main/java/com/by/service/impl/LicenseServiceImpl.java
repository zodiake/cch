package com.by.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.by.json.LicenseJson;
import com.by.model.License;
import com.by.model.Member;
import com.by.repository.LicenseRepository;
import com.by.service.LicenseService;
import com.by.service.MemberService;

@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {
	@Autowired
	private LicenseRepository repository;
	@Autowired
	private MemberService memberService;

	@Override
	@CacheEvict(value = "license", key = "#member.id")
	public License save(Member member, String licenseName) {
		License license = repository.findByName(licenseName);
		if (license != null) {
			if (!license.getMembers().contains(member)) {
				license.getMembers().add(member);
			}
			return license;
		}
		License l = new License();
		l.setName(licenseName);
		List<Member> members = new ArrayList<>();
		members.add(member);
		l.setMembers(members);
		repository.save(l);
		return l;
	}

	@Override
	@Transactional(readOnly = true)
	public License findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "license", key = "#member.id")
	public List<LicenseJson> findByMember(Member member) {
		return memberService.findOne(member.getId()).getLicenses().stream().map(i -> {
			LicenseJson license = new LicenseJson();
			license.setId(i.getId());
			license.setLicenseName(i.getName());
			return license;
		}).collect(Collectors.toList());
	}
}
