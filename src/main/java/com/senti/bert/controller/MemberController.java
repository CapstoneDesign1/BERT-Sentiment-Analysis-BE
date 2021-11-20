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
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userId));
        // true가 나오면 중복임
    }

    @PostMapping("")
    public ResponseEntity<Long> registerMember(@RequestBody MemberDto memberDto){
        if(memberService.register(memberDto) != null){
            return new ResponseEntity<>(memberDto.getId(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }
    /*
    TODO:: flask 통신하여 점수 계산 로직
     */

}
