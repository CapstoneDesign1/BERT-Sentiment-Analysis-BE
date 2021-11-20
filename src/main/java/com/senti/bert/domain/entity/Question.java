package com.senti.bert.domain.entity;

import com.senti.bert.dto.QuestionDto;
import lombok.*;

import javax.persistence.*;
import java.util.Comparator;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Question implements Comparable<Question> {

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

    @Override
    public int compareTo(Question question){
        return this.id.compareTo(question.getId());
    }
}
