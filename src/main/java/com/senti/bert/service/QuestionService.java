package com.senti.bert.service;

import com.senti.bert.domain.entity.Question;
import com.senti.bert.domain.repository.QuestionRepository;
import com.senti.bert.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public List<QuestionDto> findQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(Question::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionDto findQuestionByQuestionId(Long id){
        Optional<Question> question = questionRepository.findById(id);
        return Question.toDto(question.get());
    }
}
