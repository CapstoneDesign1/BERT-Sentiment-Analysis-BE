package com.senti.bert.utils;

import com.senti.bert.domain.entity.EmotionType;
import org.springframework.stereotype.Component;

@Component
public class EmotionUtil {

    public EmotionType getEmotionType(int number) {
        switch(number) {
            case 0:
                return EmotionType.HAPPY;
            case 1:
                return EmotionType.ANXIOUS;
            case 2:
                return EmotionType.EMBARRASS;
            case 3:
                return EmotionType.SAD;
            case 4:
                return EmotionType.ANGER;
            case 5:
                return EmotionType.HURT;
            default:
                return null;
        }
    }
}
