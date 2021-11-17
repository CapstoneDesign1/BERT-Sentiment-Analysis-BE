package com.senti.bert.controller;

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

    @GetMapping("")
    public ResponseEntity<List<QuestionDto>> getQuestion() {
        List<QuestionDto> questions = questionService.findQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
