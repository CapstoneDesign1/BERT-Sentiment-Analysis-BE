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

    @GetMapping("")
    public ResponseEntity<List<QuestionDto>> getQuestionList() {
        List<QuestionDto> questions = questionService.findQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("{question_id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable("question_id") Long questionId){
        QuestionDto questionDto = questionService.findQuestionByQuestionId(questionId);
        if(questionDto != null){
            return new ResponseEntity<>(questionDto,HttpStatus.OK);
        }
        else return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
    }

}
