package com.senti.bert.dto;

import com.senti.bert.domain.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    private Long id;
    private Boolean isDepressed;
    private String userId;
    private Long age;
    private String password;
    private String email;
    private String name;

    public static Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .isDepressed(memberDto.getIsDepressed())
                .userId(memberDto.getUserId())
                .age(memberDto.getAge())
                .password(memberDto.getPassword())
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .build();
    }
}
