package com.by.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.model.License;
import com.by.model.Member;
import com.by.repository.LicenseRepository;
import com.by.service.LicenseService;

@Service
public class LicenseServiceImpl implements LicenseService {
	@Autowired
	private LicenseRepository repository;

	@Override
	public License save(Member member, String licenseName) {
		Optional<License> license = repository.findByName(licenseName);
		if (license.isPresent()) {
			License l = license.get();
			if (!l.getMembers().contains(member)) {
				l.getMembers().add(member);
			}
			return l;
		}
		License l = new License();
		l.setName(licenseName);
		List<Member> members = new ArrayList<Member>();
		members.add(member);
		l.setMembers(members);
		repository.save(l);
		return l;
	}

}
