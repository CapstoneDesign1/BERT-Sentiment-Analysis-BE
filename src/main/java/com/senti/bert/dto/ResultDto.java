package com.senti.bert.dto;

import com.senti.bert.domain.entity.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private EmotionType emotionType;
    private BigDecimal sadScore;
}
