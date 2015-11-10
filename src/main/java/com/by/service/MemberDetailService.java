package com.by.service;

import java.util.Optional;

import com.by.model.MemberDetail;

public interface MemberDetailService {
	public Optional<MemberDetail> update(MemberDetail detail);
}
