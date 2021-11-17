package com.senti.bert.dto;

import com.senti.bert.domain.entity.Answer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {
    private Long id;
    private String content;
    private Long diaryId;
    private Long questionId;
    private String userId;

    public static Answer toEntity(AnswerDto answerDto) {
        return Answer.builder()
                .id(answerDto.getId())
                .content(answerDto.getContent())
                .diaryId(answerDto.diaryId)
                .questionId(answerDto.getQuestionId())
                .userId(answerDto.getUserId())
                .build();
    }
}
