package com.senti.bert.domain.repository;

import com.senti.bert.domain.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(String userId);
}
