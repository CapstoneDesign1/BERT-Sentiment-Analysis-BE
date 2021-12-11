package com.senti.bert.service;

import com.nimbusds.jose.util.IOUtils;
import com.senti.bert.domain.entity.*;
import com.senti.bert.domain.repository.DiaryRepository;
import com.senti.bert.domain.repository.DiaryResultRepository;
import com.senti.bert.domain.repository.QuestionRepository;
import com.senti.bert.dto.DiaryDto;
import com.senti.bert.dto.QuestionDto;
import com.senti.bert.utils.EmotionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final QuestionRepository questionRepository;
    private final DiaryResultRepository diaryResultRepository;
    private final EmotionUtil emotionUtil;
    private final String FLASK_URL = "http://3.133.60.194:5000/predict";

    @Transactional
    public Long create(DiaryDto diaryDto) {
        if (diaryDto != null) {
            Diary diary = DiaryDto.toEntity(diaryDto);
            diaryRepository.save(diary);
            return diary.getId();
        }
        return null;
    }

    @Transactional
    public void analysisDiaryResult(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        findDiary.ifPresent(diary -> {
            List<String> answerList = new ArrayList<>();
            answerList.add(diary.getAnswer1());
            answerList.add(diary.getAnswer2());
            answerList.add(diary.getAnswer3());
            answerList.add(diary.getAnswer4());
            answerList.add(diary.getAnswer5());

            List<BigDecimal> resArray = new ArrayList<>();
            for (int i=0; i<6; i++) {
                resArray.add(BigDecimal.ZERO);
            }

            answerList.forEach(answer -> {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(FLASK_URL);

                MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(StandardCharsets.UTF_8);

                builder.addTextBody("input_sentence", answer, ContentType.MULTIPART_FORM_DATA.withCharset(StandardCharsets.UTF_8));

                HttpEntity httpBody = builder.build();
                httpPost.setEntity(httpBody);
                try {
                    CloseableHttpResponse response = httpClient.execute(httpPost);
                    HttpEntity responseEntity = response.getEntity();
                    String s = IOUtils.readInputStreamToString(responseEntity.getContent());

                    List<BigDecimal> decimalList = new ArrayList<>();
                    StringTokenizer stringTokenizer = new StringTokenizer(s, "#");

                    while (stringTokenizer.hasMoreTokens()) {
                        String number = stringTokenizer.nextToken();
                        BigDecimal bigDecimal = new BigDecimal(number);
                        decimalList.add(bigDecimal);
                    }
                    for (int i=0; i<6; i++) {
                        BigDecimal bigDecimal = resArray.get(i);
                        resArray.set(i, bigDecimal.add(decimalList.get(i)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            BigDecimal max = Collections.max(resArray);
            int i = resArray.indexOf(max);
            EmotionType emotionType = emotionUtil.getEmotionType(i);
            if (resArray.get(0).compareTo(BigDecimal.ZERO) > 0) {
                resArray.set(3, resArray.get(3).subtract(resArray.get(0)));
            }

            DiaryResult diaryResult = DiaryResult.builder()
                    .diaryId(diary.getId())
                    .result1(resArray.get(0).divide(BigDecimal.valueOf(5), RoundingMode.UNNECESSARY))
                    .result2(resArray.get(1).divide(BigDecimal.valueOf(5), RoundingMode.UNNECESSARY))
                    .result3(resArray.get(2).divide(BigDecimal.valueOf(5), RoundingMode.UNNECESSARY))
                    .result4(resArray.get(3).divide(BigDecimal.valueOf(5), RoundingMode.UNNECESSARY))
                    .result5(resArray.get(4).divide(BigDecimal.valueOf(5), RoundingMode.UNNECESSARY))
                    .result6(resArray.get(5).divide(BigDecimal.valueOf(5), RoundingMode.UNNECESSARY))
                    .emotionType(emotionType)
                    .build();
            diary.setEmotionType(emotionType);
            diaryResultRepository.save(diaryResult);
        });
    }

    @Transactional
    public List<DiaryDto> getDiaryByUserId(String userId) {
        List<Diary> byDiaryId = diaryRepository.findByUserId(userId);
        return byDiaryId.stream().map(Diary::toDto).collect(Collectors.toList());
    }

    @Transactional
    public Integer getTotalCountByUserId(String userId) {
        return diaryRepository.findByUserId(userId).size();
    }

    @Transactional
    public Boolean checkDiary(String userId) {
        LocalDateTime time = LocalDateTime.now();
        Optional<Diary> findRecentDiary = diaryRepository.findTop1ByUserIdOrderByIdDesc(userId);
        LocalDateTime userRecentTime = findRecentDiary.map(BaseEntity::getCreatedDate).orElse(null);
        if (userRecentTime != null) {
            int dayNow = time.getDayOfMonth();
            int dayRecent = userRecentTime.getDayOfMonth();
            return dayNow != dayRecent;
        }
        return true;
    }

    @Transactional
    public DiaryDto findOne(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if (findDiary.isPresent()) {
            Diary diaryEntity = findDiary.get();
            DiaryDto diaryDto = Diary.toDto(diaryEntity);
            List<Question> questionList = questionRepository.findByIdIn(findQuestions(diaryDto.getQuestionIdList()));
            List<QuestionDto> questionDtoList = questionList.stream().map(Question::toDto).collect(Collectors.toList());
            diaryDto.setQuestionDtoList(questionDtoList);
            return diaryDto;
        }
        return null;
    }

    @Transactional
    public void delete(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        findDiary.ifPresent(diaryRepository::delete);
    }

    public List<Long> findQuestions(String questionIdList) {
        StringTokenizer stringTokenizer = new StringTokenizer(questionIdList, "#");
        List<Long> questionDtoList = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String currentQuestion = stringTokenizer.nextToken();
            Long questionId = Long.valueOf(currentQuestion);
            questionDtoList.add(questionId);
        }
        return questionDtoList;
    }
}

