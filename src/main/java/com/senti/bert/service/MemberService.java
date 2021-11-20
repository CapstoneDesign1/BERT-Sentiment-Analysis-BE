package com.senti.bert.service;

import com.senti.bert.domain.entity.Member;
import com.senti.bert.domain.repository.MemberRepository;
import com.senti.bert.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(MemberDto memberDto){
        if(memberDto != null){
            Member member = MemberDto.toEntity(memberDto);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);

            return member.getId();
        }
        else return null;
    }

    @Transactional
    public boolean checkUserIdDuplicate(String userId){
        /*System.out.println(userId);
        System.out.println(memberRepository.existsByUserId(userId));*/
        return memberRepository.existsByUserId(userId);
    }

    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId);
        return new User(member.getUserId(),member.getPassword(),null);
    }
}
