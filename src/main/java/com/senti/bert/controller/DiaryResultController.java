package com.senti.bert.controller;

import com.senti.bert.dto.ResultDto;
import com.senti.bert.service.DiaryResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
public class DiaryResultController {

    private final DiaryResultService diaryResultService;

    @GetMapping("/{user_id}")
    public ResponseEntity<ResultDto> getDiaryResult(@PathVariable("user_id") String userId) {
        return new ResponseEntity<>(diaryResultService.getResult(userId), HttpStatus.OK);
    }
}
