package com.senti.bert.controller;

import com.senti.bert.dto.AnswerDto;
import com.senti.bert.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody AnswerDto answerDto) {
        Long answerId = answerService.create(answerDto);
        if (answerId != null) {
            return new ResponseEntity<>(answerId, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<AnswerDto>> getUserAnswer(@PathVariable("user_id") String userId) {
        List<AnswerDto> userAnswerList = answerService.getUserAnswerList(userId);
        return new ResponseEntity<>(userAnswerList, HttpStatus.OK);
    }

}
