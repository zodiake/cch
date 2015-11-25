package com.by.service.impl;

import com.by.model.License;
import com.by.model.Member;
import com.by.repository.LicenseRepository;
import com.by.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {
    @Autowired
    private LicenseRepository repository;

    @Override
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
        List<Member> members = new ArrayList<Member>();
        members.add(member);
        l.setMembers(members);
        repository.save(l);
        return l;
    }

    @Override
    public License findByName(String name) {
        return repository.findByName(name);
    }

}
