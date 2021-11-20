package com.senti.bert.controller;

import com.senti.bert.dto.MemberDto;
import com.senti.bert.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/check/{user_id}")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable("user_id") String userId){
        return new ResponseEntity<>(memberService.checkUserIdDuplicate(userId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> registerMember(@RequestBody MemberDto memberDto){
        Long registerId = memberService.register(memberDto);
        if (registerId != null){
            return new ResponseEntity<>(registerId, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    /*
    TODO:: flask 통신하여 점수 계산 로직
     */

}
