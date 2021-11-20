package com.senti.bert.service;

import com.senti.bert.domain.entity.Question;
import com.senti.bert.domain.repository.QuestionRepository;
import com.senti.bert.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public List<QuestionDto> findQuestions() {
        List<Question> allQuestionList = questionRepository.findAll();
        Collections.shuffle(allQuestionList);
        List<Question> questions = allQuestionList.subList(0, 5);
        List<QuestionDto> questionDtoList = questions.stream().map(Question::toDto).collect(Collectors.toList());
        Collections.sort(questionDtoList);
        return questionDtoList;
    }

    @Transactional
    public Long register(QuestionDto questionDto) {
        if (questionDto != null) {
            Question question = QuestionDto.toEntity(questionDto);
            questionRepository.save(question);
            return question.getId();
        }
        return null;
    }

}