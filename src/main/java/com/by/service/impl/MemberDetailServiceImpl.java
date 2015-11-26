package com.by.service.impl;

import com.by.model.Member;
import com.by.model.MemberDetail;
import com.by.repository.MemberDetailRepository;
import com.by.service.MemberDetailService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberDetailServiceImpl implements MemberDetailService {
    @Autowired
    private MemberDetailRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<MemberDetail> update(MemberDetail detail) {
        return repository.findByMember(detail.getMember()).map(i -> {
            i.setAddress(detail.getAddress());
            i.setRealName(detail.getRealName());
            i.setUpdatedBy(detail.getUpdatedBy());
            return i;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MemberDetail> findByMember(Member m) {
        return repository.findByMember(m);
    }

    @Override
    public MemberDetail save(MemberDetail detail) {
        return repository.save(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetail findAuditByRevision(Long id, int revision) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.find(MemberDetail.class, id, revision);
    }

    public List<MemberDetail> findAllAuditRevision(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        return auditReader.createQuery()
                .forRevisionsOfEntity(MemberDetail.class, true, true)
                .add(AuditEntity.id().eq(id))
                .addOrder(AuditEntity.revisionNumber().asc())
                .getResultList();
    }
}
