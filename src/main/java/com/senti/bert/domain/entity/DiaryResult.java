package com.senti.bert.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DiaryResult extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long id;

    @Column
    private Long diaryId;

    @Column
    private BigDecimal result1;

    @Column
    private BigDecimal result2;

    @Column
    private BigDecimal result3;

    @Column
    private BigDecimal result4;

    @Column
    private BigDecimal result5;

    @Column
    private BigDecimal result6;

    @Column
    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;
}
