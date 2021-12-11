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
        List<BigDecimal> resultList = new ArrayList<>();
        for (int i=0; i<6; i++) {
            resultList.add(BigDecimal.ZERO);
        }
        BigDecimal skrrt = BigDecimal.ZERO;
        for (int i=1; i<=diaryIdList.size(); i++) {
            BigDecimal weight = BigDecimal.valueOf(Math.sqrt(Math.sqrt(i)));
            skrrt = skrrt.add(weight.multiply(BigDecimal.valueOf(3)));
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
        log.info(resultList.toString());
        BigDecimal max = Collections.max(resultList);
        int i = resultList.indexOf(max);
        BigDecimal sadScore = resultList.get(3);
        if (sadScore.compareTo(BigDecimal.ZERO) < 0) {
            sadScore = BigDecimal.ZERO;
        }
        else {
            if (max.compareTo(BigDecimal.ZERO) > 0) {
                sadScore = sadScore.divide(skrrt, RoundingMode.UP).multiply(BigDecimal.valueOf(100));
            }
            else {
                sadScore = BigDecimal.ZERO;
            }
        }
        EmotionType emotionType = emotionUtil.getEmotionType(i);
        long depress = 0L;
        if (sadScore.compareTo(BigDecimal.valueOf(48)) < 0) {
            depress = 0L;
        }
        else if (sadScore.compareTo(BigDecimal.valueOf(63)) < 0) {
            depress = 1L;
        }
        else if (sadScore.compareTo(BigDecimal.valueOf(75)) < 0) {
            depress = 2L;
        }
        else {
            depress = 3L;
        }
        return ResultDto.builder()
                .emotionType(emotionType)
                .sadScore(sadScore)
                .depress(depress)
                .build();
    }
}
