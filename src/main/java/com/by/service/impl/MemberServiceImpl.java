package com.by.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.by.exception.MemberAlreadyExistException;
import com.by.exception.MemberNotFoundException;
import com.by.exception.MemberNotValidException;
import com.by.exception.NotEnoughScoreException;
import com.by.exception.NotValidException;
import com.by.form.AdminMemberForm;
import com.by.json.MemberJson;
import com.by.model.Card;
import com.by.model.CardRule;
import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.model.RuleCategory;
import com.by.model.ScoreAddHistory;
import com.by.repository.CardRuleRepository;
import com.by.repository.MemberRepository;
import com.by.service.CardService;
import com.by.service.MemberService;
import com.by.service.RuleService;
import com.by.service.ScoreAddHistoryService;
import com.by.service.ScoreHistoryService;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	private final String reason = "";
	@Autowired
	private MemberRepository repository;
	@Autowired
	private ScoreAddHistoryService scoreAddHistoryService;
	@Autowired
	private ScoreHistoryService scoreHistoryService;
	@Autowired
	private CardService cardService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private CardRuleRepository cardRuleRepository;
	@Autowired
	private EntityManager em;
	private RuleCategory registerCategory = new RuleCategory(1L);

	@Override
	@Transactional(readOnly = true)
	public Optional<Member> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("member")
	public Optional<Member> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Member save(Member member) {
		Long i = repository.countByName(member.getName());
		member.setMemberDetail(new MemberDetail());
		if (i > 0)
			throw new MemberAlreadyExistException();
		Card card = cardService.findByIdAndValid(member.getCard().getId(), ValidEnum.VALID);
		if (card == null)
			throw new NotValidException();
		List<CardRule> rules = cardRuleRepository.findByRuleCategoryAndCardAndValid(registerCategory, card,
				ValidEnum.VALID);
		int score = card.getInitScore();
		if (rules.size() > 0) {
			score += ruleService.getMaxScore(rules);
		}
		member.setScore(score);
		scoreAddHistoryService.save(member, score, "sign in score");
		scoreHistoryService.save(member, score, "", ScoreHistoryEnum.SIGNUP);
		return repository.save(member);
	}

	@Override
	public Long countByName(String name) {
		return repository.countByName(name);
	}

	@Override
	public Optional<Member> update(Member member) {
		return repository.findById(member.getId()).map(i -> {
			i.setPassword(member.getPassword());
			return i;
		});
	}

	@Override
	public Member addScore(Member member, int total, String reason, ScoreHistoryEnum type) {
		Member source = repository.findOne(member.getId());
		source.setScore(source.getScore() + total);
		scoreAddHistoryService.save(member, total, reason);
		scoreHistoryService.save(member, total, reason, type);
		return source;
	}

	@Override
	public Member minusScore(Member member, int total, String reason, ScoreHistoryEnum type) {
		Member source = repository.findOne(member.getId());
		if (source.getScore() < total)
			throw new NotEnoughScoreException();
		List<ScoreAddHistory> historyList = scoreAddHistoryService.findByMember(member);
		List<ScoreAddHistory> results = extractScoreHistory(historyList, total);
		if (total > 0) {
			int sum = results.stream().map(ScoreAddHistory::getTotal).reduce((i, s) -> i + s).get();
			if (sum > total) {
				ScoreAddHistory last = results.get(results.size() - 1);
				last.setTotal(sum - total);
				results.remove(last);
				for (ScoreAddHistory h : results) {
					scoreAddHistoryService.delete(h.getId());
				}
			} else if (sum == total) {
				for (ScoreAddHistory h : results) {
					scoreAddHistoryService.delete(h.getId());
				}
			}
			source.setScore(source.getScore() - total);
		}
		scoreHistoryService.save(member, -total, reason, type);
		return source;
	}

	/*
	 * find which scoreAddHistory should deleted from history
	 *
	 * @param allList member all scoreAddedHistory list
	 */
	@Override
	public List<ScoreAddHistory> extractScoreHistory(List<ScoreAddHistory> allList, int total) {
		List<ScoreAddHistory> results = new ArrayList<>();
		int init = 0;
		for (ScoreAddHistory s : allList) {
			if (init < total) {
				init += s.getTotal();
				results.add(s);
			} else {
				break;
			}
		}
		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public Member findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("member")
	public Member findOneCache(Long id){
		return repository.findOne(id);
	}

	@Override
	public Page<MemberJson> findAll(AdminMemberForm form, Pageable pageable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> c = cb.createQuery(Member.class);
		Root<Member> root = c.from(Member.class);
		c.select(root);
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Member.class)));
		List<Predicate> criteria = new ArrayList<>();

		if (!StringUtils.isEmpty(form.getMobile()))
			criteria.add(cb.equal(root.get("name"), form.getMobile()));
		if (form.getCard() != null)
			criteria.add(cb.equal(root.get("card"), new Card(form.getCard())));
		if (form.getBeginTime() != null)
			criteria.add(cb.greaterThanOrEqualTo(root.get("createdTime"), form.getBeginTime()));
		if (form.getEndTime() != null)
			criteria.add(cb.lessThanOrEqualTo(root.get("createdTime"), form.getEndTime()));
		c.where(criteria.toArray(new Predicate[0]));
		cq.where(criteria.toArray(new Predicate[0]));

		List<Member> lists = em.createQuery(c).setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize()).getResultList();
		Long count = em.createQuery(cq).getSingleResult();

		List<MemberJson> results = lists.stream().map(i -> new MemberJson(i)).collect(Collectors.toList());
		return new PageImpl<>(results, pageable, count);
	}

	@Override
	public Page<Member> findFirstPage(int size) {
		return repository.findAll(new PageRequest(0, size, Sort.Direction.DESC, "createdTime"));
	}

	@Override
	public boolean isValidMember(Member member) {
		Member m = findOne(member.getId());
		if (m == null)
			return false;
		if (m.getValid().equals(ValidEnum.INVALID))
			return false;
		return true;
	}

	@Override
	public Long countByCard(Card card) {
		return repository.countByCard(card);
	}

	private void validMember(Member member) {
		if (member == null)
			throw new MemberNotFoundException();
		if (member.getValid().equals(ValidEnum.INVALID))
			throw new MemberNotValidException();
	}

	@Override
	public Optional<Member> updatePassword(Member member) {
		return repository.findByName(member.getName()).map(i -> {
			i.setPassword(member.getPassword());
			return i;
		});
	}

	@Override
	public Member validateOrInValidate(Member member, String name) {
		Member m = repository.findOne(member.getId());
		if (m.getValid().equals(ValidEnum.VALID)) {
			m.setValid(ValidEnum.INVALID);
		} else {
			m.setInValidBy(name);
			m.setInValidTime(Calendar.getInstance());
			m.setValid(ValidEnum.VALID);
		}
		return m;
	}

}
