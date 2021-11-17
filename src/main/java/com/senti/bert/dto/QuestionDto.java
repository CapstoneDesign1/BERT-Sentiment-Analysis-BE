package com.senti.bert.dto;

import com.senti.bert.domain.entity.Question;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestionDto {
    private Long id;
    private String content;

    public static Question toEntity(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .content(questionDto.getContent())
                .build();
    }
}
