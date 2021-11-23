package com.senti.bert.domain.repository;

import com.senti.bert.domain.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(String userId);
    Optional<Diary> findTop1ByUserIdOrderByIdDesc(String userId);
}
