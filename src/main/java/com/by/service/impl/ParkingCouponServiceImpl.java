package com.by.service.impl;

import com.by.exception.NotFoundException;
import com.by.exception.NotValidException;
import com.by.exception.OutOfStorageException;
import com.by.form.BaseCouponForm;
import com.by.json.CouponTemplateJson;
import com.by.json.ParkingCouponHistoryJson;
import com.by.model.Member;
import com.by.model.ParkingCoupon;
import com.by.model.ParkingCouponExchangeHistory;
import com.by.model.ParkingCouponUseHistory;
import com.by.repository.ParkingCouponRepository;
import com.by.service.*;
import com.by.typeEnum.ScoreHistoryEnum;
import com.by.typeEnum.ValidEnum;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingCouponServiceImpl implements ParkingCouponService {
    @Autowired
    private ParkingCouponRepository repository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private ParkingCouponUseHistoryService useHistoryService;
    @Autowired
    private ParkingCouponExchangeHistoryService exchangeHistoryService;
    @Autowired
    private EntityManager em;
    @Value(value = "${coupon.amount}")
    private double amount;

    @Override
    public ParkingCoupon save(ParkingCoupon coupon) {
        Calendar time = coupon.getEndTime();
        coupon.setAmount(amount);
        time.set(Calendar.HOUR, 23);
        time.set(Calendar.MINUTE, 59);
        time.set(Calendar.SECOND, 59);
        coupon.setValid(ValidEnum.VALID);
        return repository.save(coupon);
    }

    @Override
    public Optional<ParkingCoupon> update(ParkingCoupon coupon) {
        return repository.findById(coupon.getId()).map(i -> {
            i.setAmount(coupon.getAmount());
            i.setName(coupon.getName());
            i.setScore(coupon.getScore());
            i.setSummary(coupon.getSummary());
            i.setContentImg(coupon.getContentImg());
            i.setCoverImg(coupon.getCoverImg());
            return i;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingCoupon> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParkingCoupon> findById(int id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingCoupon findOne(int id) {
        return repository.findOne(id);
    }

    @Override
    @Cacheable("coupon")
    public ParkingCoupon findOneCache(int id) {
        return repository.findOne(id);
    }

    @Override
    @Cacheable(value = "coupon")
    @Transactional(readOnly = true)
    public Page<ParkingCoupon> findByValid(ValidEnum valid, Pageable pageable) {
        return repository.findByValid(valid, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCoupon> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCoupon> findFirstPage(int size) {
        return repository.findAll(new PageRequest(0, size, Sort.Direction.DESC, "createdTime"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponTemplateJson> findAll(BaseCouponForm form, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ParkingCoupon> c = cb.createQuery(ParkingCoupon.class);
        Root<ParkingCoupon> root = c.from(ParkingCoupon.class);
        c.select(root);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(ParkingCoupon.class)));
        Predicate[] predicates = couponService.getPredicateList(form, root, cb).toArray(new Predicate[0]);
        c.where(predicates);
        cq.where(predicates);

        List<ParkingCoupon> lists = em.createQuery(c)
                .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery(cq).getSingleResult();
        List<CouponTemplateJson> results = lists.stream().map(i -> new CouponTemplateJson(i))
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, count);
    }

    @Override
    public ParkingCoupon validOrInValid(ParkingCoupon coupon) {
        ParkingCoupon c = repository.findOne(coupon.getId());
        if (c.getValid().equals(ValidEnum.VALID))
            c.setValid(ValidEnum.INVALID);
        else
            c.setValid(ValidEnum.VALID);
        return c;
    }

    @Override
    public Member useCoupon(Member member, int total, String license) {
        Member m = memberService.findOne(member.getId());
        int sourceTotal = m.getTotalParkingCoupon();
        if (sourceTotal < total)
            throw new OutOfStorageException();
        licenseService.save(m, license);
        useHistoryService.save(m, total, license);
        m.setTotalParkingCoupon(m.getTotalParkingCoupon() - total);
        return m;
    }

    @Override
    @Cacheable(value = "coupon", key = "T(com.by.utils.CalendarUtil).getToday()")
    public ParkingCoupon findActivate() {
        List<ParkingCoupon> lists = repository.findAll();
        Calendar today = Calendar.getInstance();
        List<ParkingCoupon> results = lists.stream().filter(i -> i.getValid().equals(ValidEnum.VALID)).filter(i -> {
            if (i.getBeginTime() == null && i.getEndTime() == null)
                return true;
            if (i.getBeginTime() != null && i.getEndTime() != null) {
                if (i.getBeginTime().before(today) && i.getEndTime().after(today))
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (results.size() == 0)
            return null;
        results.sort((r1, r2) -> {
            if (r1.getScore() > r2.getScore())
                return 1;
            return -1;
        });
        return results.get(0);
    }

    @Override
    public void exchangeCoupon(Member member, ParkingCoupon coupon, int total) {
        Member m = em.find(Member.class, member.getId());
        ParkingCoupon sourceCoupon = em.find(ParkingCoupon.class, coupon.getId());

        if (sourceCoupon == null)
            throw new NotFoundException();
        if (couponService.isValidCoupon(sourceCoupon)) {
            memberService.minusScore(m, sourceCoupon.getScore() * total, "", ScoreHistoryEnum.COUPONEXCHANGE);
            exchangeHistoryService.save(m, sourceCoupon, total);
            m.setTotalParkingCoupon(m.getTotalParkingCoupon() + total);
        } else {
            throw new NotValidException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParkingCouponHistoryJson> findByMemberHistory(Member member, Pageable pageable) {
        Page<ParkingCouponUseHistory> useHistory = useHistoryService.findByMember(member, pageable);
        Page<ParkingCouponExchangeHistory> exchangeHistory = exchangeHistoryService.findByMember(member, pageable);
        List<ParkingCouponHistoryJson> results = new ArrayList<>();
        List<ParkingCouponHistoryJson> useJson = useHistory.getContent().stream().map(i ->
                new ParkingCouponHistoryJson(i)
        ).collect(Collectors.toList());
        List<ParkingCouponHistoryJson> exchangeJson = exchangeHistory.getContent().stream().map(i -> new ParkingCouponHistoryJson(i)).collect(Collectors.toList());
        results.addAll(useJson);
        results.addAll(exchangeJson);
        results.sort((o1, o2) -> {
            if (o1.getCreatedTime().before(o2.getCreatedTime()))
                return 1;
            return -1;
        });
        Long max = Math.max(useHistory.getTotalElements(), exchangeHistory.getTotalElements());
        return new PageImpl<>(results, pageable, max);
    }
}
