package com.senti.bert.domain.repository;

import com.senti.bert.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);
    Member findByUserId(String userId);
}
