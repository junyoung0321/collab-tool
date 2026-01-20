package com.example.collab_tool.domain.member.service;

import com.example.collab_tool.domain.member.dto.SignupRequest;
import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.collab_tool.domain.member.dto.LoginRequest;
import com.example.collab_tool.global.util.JwtUtil;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public Long signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) { //중복 검사
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword()); // 비번 암호화
        Member member = request.toEntity(encodedPassword); //엔티티 생성
        Member savedMember = memberRepository.save(member); // DB 저장
        return savedMember.getId(); // 저장된 id 반환
    }

    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(member.getEmail());
    }
}
