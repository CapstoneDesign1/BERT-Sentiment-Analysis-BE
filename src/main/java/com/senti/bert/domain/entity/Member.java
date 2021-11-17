package com.senti.bert.domain.entity;

import com.senti.bert.dto.MemberDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
public class Member extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private Boolean isDepressed;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column
    private Long age;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String name;

    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .isDepressed(member.getIsDepressed())
                .userId(member.getUserId())
                .age(member.getAge())
                .password(member.getPassword())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
