package com.senti.bert.dto;

import com.senti.bert.domain.entity.Diary;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DiaryDto {
    private Long id;
    private String userId;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private String questionIdList;
    private LocalDateTime createdDate;

    private List<QuestionDto> questionDtoList;

    public static Diary toEntity(DiaryDto diaryDto) {
        return Diary.builder()
                .id(diaryDto.getId())
                .userId(diaryDto.getUserId())
                .answer1(diaryDto.getAnswer1())
                .answer2(diaryDto.getAnswer2())
                .answer3(diaryDto.getAnswer3())
                .answer4(diaryDto.getAnswer4())
                .answer5(diaryDto.getAnswer5())
                .questionIdList(diaryDto.getQuestionIdList())
                .build();
    }
}