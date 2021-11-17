package com.senti.bert.domain.entity;

import com.senti.bert.dto.AnswerDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
public class Answer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column
    private String content;

    @Column
    private Long diaryId;

    @Column
    private Long questionId;

    @Column
    private String userId;

    public static AnswerDto toDto(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .diaryId(answer.getDiaryId())
                .questionId(answer.getQuestionId())
                .userId(answer.getUserId())
                .build();
    }
}
