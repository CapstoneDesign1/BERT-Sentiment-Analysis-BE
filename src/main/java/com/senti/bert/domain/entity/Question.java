package com.senti.bert.domain.entity;

import com.senti.bert.dto.QuestionDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    public static QuestionDto toDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }
}
