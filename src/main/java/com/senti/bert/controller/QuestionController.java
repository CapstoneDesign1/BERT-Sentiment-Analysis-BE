package com.senti.bert.controller;

import com.senti.bert.domain.entity.Question;
import com.senti.bert.dto.QuestionDto;
import com.senti.bert.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public ResponseEntity<List<QuestionDto>> getQuestionListWithRandomOrder() {
        List<QuestionDto> questions = questionService.findQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody QuestionDto questionDto) {
        Long registerId = questionService.register(questionDto);
        if (registerId != null) {
            return new ResponseEntity<>(registerId, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }
}
