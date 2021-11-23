package com.senti.bert.domain.entity;

import com.senti.bert.dto.DiaryDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Diary extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column
    private String answer1;

    @Column
    private String answer2;

    @Column
    private String answer3;

    @Column
    private String answer4;

    @Column
    private String answer5;

    @Column
    private String questionIdList;

    @Column
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;

    public static DiaryDto toDto(Diary diary) {
        return DiaryDto.builder()
                .id(diary.getId())
                .userId(diary.getUserId())
                .answer1(diary.getAnswer1())
                .answer2(diary.getAnswer2())
                .answer3(diary.getAnswer3())
                .answer4(diary.getAnswer4())
                .answer5(diary.getAnswer5())
                .questionIdList(diary.getQuestionIdList())
                .createdDate(diary.getCreatedDate())
                .emotionType(diary.getEmotionType())
                .build();
    }
}
