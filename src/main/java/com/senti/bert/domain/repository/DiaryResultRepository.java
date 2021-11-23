package com.senti.bert.domain.repository;

import com.senti.bert.domain.entity.DiaryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DiaryResultRepository extends JpaRepository<DiaryResult, Long> {
    Optional<DiaryResult> findByDiaryId(Long diaryId);
}
