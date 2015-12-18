package com.by.service.impl;

import com.by.json.HelpJson;
import com.by.model.MemberHelp;
import com.by.repository.MemberHelperRepository;
import com.by.service.MemberHelperService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yagamai on 15-12-18.
 */
@Service
@Transactional
public class MemberHelperServiceImpl implements MemberHelperService {
	@Autowired
	private MemberHelperRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<MemberHelp> findAll() {
		return Lists.newArrayList(repository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "help")
	public List<HelpJson> findAllJson() {
		return findAll().stream().map(i -> {
			HelpJson json = new HelpJson();
			json.setId(i.getId());
			json.setTitle(i.getTitle());
			return json;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public MemberHelp findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "help")
	public HelpJson findOneJson(int id) {
		MemberHelp help = findOne(id);
		HelpJson json = new HelpJson();
		json.setContent(help.getContent().getSummary());
		json.setId(help.getId());
		json.setTitle(help.getTitle());
		return json;
	}

	@Override
	@CacheEvict("help")
	public MemberHelp save(MemberHelp memberHelp) {
		return repository.save(memberHelp);
	}

	@Override
	@CacheEvict("help")
	public MemberHelp update(MemberHelp memberHelp) {
		MemberHelp help = repository.findOne(memberHelp.getId());
		help.setContent(memberHelp.getContent());
		return help;
	}
}
