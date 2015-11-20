package com.by.service;

import com.by.model.License;
import com.by.model.Member;

public interface LicenseService {

	License save(Member member, String licenseName);
}
