package com.senti.bert.service;

import com.senti.bert.domain.entity.Diary;
import com.senti.bert.domain.entity.Question;
import com.senti.bert.domain.repository.DiaryRepository;
import com.senti.bert.domain.repository.QuestionRepository;
import com.senti.bert.dto.DiaryDto;
import com.senti.bert.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long create(DiaryDto diaryDto) {
        if (diaryDto != null) {
            Diary diary = DiaryDto.toEntity(diaryDto);
            diaryRepository.save(diary);
            return diary.getId();
        }
        return null;
    }

    @Transactional
    public List<DiaryDto> getDiaryByUserId(String userId) {
        List<Diary> byDiaryId = diaryRepository.findByUserId(userId);
        return byDiaryId.stream().map(Diary::toDto).collect(Collectors.toList());
    }

    @Transactional
    public Integer getTotalCountByUserId(String userId) {
        return diaryRepository.findByUserId(userId).size();
    }

    @Transactional
    public DiaryDto findOne(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if (findDiary.isPresent()) {
            Diary diaryEntity = findDiary.get();
            DiaryDto diaryDto = Diary.toDto(diaryEntity);
            List<Question> questionList = questionRepository.findByIdIn(findQuestions(diaryDto.getQuestionIdList()));
            List<QuestionDto> questionDtoList = questionList.stream().map(Question::toDto).collect(Collectors.toList());
            diaryDto.setQuestionDtoList(questionDtoList);
            return diaryDto;
        }
        return null;
    }

    @Transactional
    public void delete(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        findDiary.ifPresent(diaryRepository::delete);
    }

    public List<Long> findQuestions(String questionIdList) {
        StringTokenizer stringTokenizer = new StringTokenizer(questionIdList, "#");
        List<Long> questionDtoList = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String currentQuestion = stringTokenizer.nextToken();
            Long questionId = Long.valueOf(currentQuestion);
            questionDtoList.add(questionId);
        }
        return questionDtoList;
    }
}

