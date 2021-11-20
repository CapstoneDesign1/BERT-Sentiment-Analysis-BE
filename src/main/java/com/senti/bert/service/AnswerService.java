package com.senti.bert.service;

import com.senti.bert.domain.entity.Answer;
import com.senti.bert.domain.repository.AnswerRepository;
import com.senti.bert.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Long create(AnswerDto answerDto) {
        if (answerDto != null) {
            Answer answer = AnswerDto.toEntity(answerDto);
            answerRepository.save(answer);
            return answer.getId();
        }
        return null;
    }

    @Transactional
    public List<AnswerDto> getUserAnswerList(String userId) {
        List<Answer> byUserId = answerRepository.findByUserId(userId);
        return byUserId.stream().map(Answer::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AnswerDto getUserAnswerByQuestionId(String userId, Long questionId){
        Answer answer = answerRepository.findByUserIdAndQuestionId(userId, questionId);
        return Answer.toDto(answer);
    }
}
