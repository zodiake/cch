package com.by.service.impl;

import com.by.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.by.model.Member;
import com.by.model.ParkingCouponExchangeHistory;
import com.by.repository.ParkingCouponExchangeHistoryRepository;
import com.by.service.ParkingCouponExchangeHistroyService;

@Service
public class ParkingCouponExchangeHistoryServiceImpl implements ParkingCouponExchangeHistroyService {
    @Autowired
    private ParkingCouponExchangeHistoryRepository repository;

    @Override
    public ParkingCouponExchangeHistory save(ParkingCouponExchangeHistory pceh) {
        return repository.save(pceh);
    }

    @Override
    public ParkingCouponExchangeHistory save(Member member, int total, Shop shop) {
        return repository.save(new ParkingCouponExchangeHistory(member, total, shop));
    }

    @Override
    public Page<ParkingCouponExchangeHistory> findByMember(Member member, Pageable pageable) {
        return repository.findByMember(member, pageable);
    }
}
