package com.senti.bert.controller;

import com.senti.bert.domain.repository.DiaryRepository;
import com.senti.bert.dto.DiaryDto;
import com.senti.bert.dto.QuestionDto;
import com.senti.bert.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<DiaryDto>> getUserDiary(@PathVariable("user_id") String userId) {
        List<DiaryDto> userDiaryList = diaryService.getDiaryByUserId(userId);
        return new ResponseEntity<>(userDiaryList, HttpStatus.OK);
    }

    @GetMapping("/{diary_id}")
    public ResponseEntity<DiaryDto> getDiary(@PathVariable("diary_id") Long diaryId) {
        DiaryDto findDiary = diaryService.findOne(diaryId);
        if (findDiary != null) {
            return new ResponseEntity<>(findDiary, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody DiaryDto diaryDto) {
        Long registerId = diaryService.create(diaryDto);
        if (registerId != null) {
            return new ResponseEntity<>(registerId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{diary_id}")
    public ResponseEntity<Void> delete(@PathVariable("diary_id") Long diaryId) {
        diaryService.delete(diaryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
