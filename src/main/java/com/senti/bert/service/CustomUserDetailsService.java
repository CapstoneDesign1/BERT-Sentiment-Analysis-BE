package com.senti.bert.service;

import com.senti.bert.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId){
        return memberRepository.findByUserId(userId)
                .map(member -> new User(member.getUserId(), member.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 존재하지 않는 사용자입니다."));
    }
}
