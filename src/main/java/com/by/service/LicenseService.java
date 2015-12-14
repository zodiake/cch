package com.by.service;

import com.by.json.LicenseJson;
import com.by.model.License;
import com.by.model.Member;

import java.util.List;

public interface LicenseService {
    License save(Member member, String licenseName);

    License findByName(String name);

    List<LicenseJson> findByMember(Member member);
}
