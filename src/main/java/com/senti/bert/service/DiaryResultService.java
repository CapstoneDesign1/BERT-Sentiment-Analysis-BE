package com.senti.bert.service;

import com.senti.bert.domain.entity.Diary;
import com.senti.bert.domain.entity.DiaryResult;
import com.senti.bert.domain.entity.EmotionType;
import com.senti.bert.domain.repository.DiaryRepository;
import com.senti.bert.domain.repository.DiaryResultRepository;
import com.senti.bert.dto.ResultDto;
import com.senti.bert.utils.EmotionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryResultService {
    private final DiaryResultRepository diaryResultRepository;
    private final DiaryRepository diaryRepository;
    private final EmotionUtil emotionUtil;

    @Transactional
    public ResultDto getResult(String userId) {
        List<Long> diaryIdList = diaryRepository.findByUserId(userId).stream().map(Diary::getId).collect(Collectors.toList());
        log.info(diaryIdList.toString());
        List<BigDecimal> resultList = new ArrayList<>();
        for (int i=0; i<6; i++) {
            resultList.add(BigDecimal.ZERO);
        }
        for (int i=1; i<=diaryIdList.size(); i++) {
            BigDecimal weight = BigDecimal.valueOf(Math.sqrt(i));
            Long diaryId = diaryIdList.get(i-1);
            Optional<DiaryResult> result = diaryResultRepository.findByDiaryId(diaryId);
            result.ifPresent(r -> {
                resultList.set(0, resultList.get(0).add(r.getResult1().multiply(weight)));
                resultList.set(1, resultList.get(1).add(r.getResult2().multiply(weight)));
                resultList.set(2, resultList.get(2).add(r.getResult3().multiply(weight)));
                resultList.set(3, resultList.get(3).add(r.getResult4().multiply(weight)));
                resultList.set(4, resultList.get(4).add(r.getResult5().multiply(weight)));
                resultList.set(5, resultList.get(5).add(r.getResult6().multiply(weight)));
            });
        }
        BigDecimal max = Collections.max(resultList);
        int i = resultList.indexOf(max);
        BigDecimal sadScore = resultList.get(3);
        log.info(sadScore.toString());
        if (sadScore.compareTo(BigDecimal.ZERO) < 0) {
            sadScore = BigDecimal.ZERO;
        }
        else {
            if (max.compareTo(BigDecimal.ZERO) > 0) {
                sadScore = sadScore.divide(max, RoundingMode.UP).multiply(BigDecimal.valueOf(100));
            }
            else {
                sadScore = BigDecimal.ZERO;
            }
        }
        log.info(sadScore + " change");
        EmotionType emotionType = emotionUtil.getEmotionType(i);
        return ResultDto.builder()
                .emotionType(emotionType)
                .sadScore(sadScore)
                .build();
    }
}
