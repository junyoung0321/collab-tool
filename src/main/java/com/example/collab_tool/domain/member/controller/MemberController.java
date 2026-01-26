package com.example.collab_tool.domain.member.controller;

import com.example.collab_tool.domain.member.dto.SignupRequest;
import com.example.collab_tool.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.collab_tool.domain.member.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request, HttpServletRequest httpRequest) {

        memberService.login(request.getEmail(), request.getPassword());
        HttpSession session = httpRequest.getSession();
        session.setAttribute("LOGIN_USER", request.getEmail());

        return ResponseEntity.ok("로그인 성공! (세션 생성됨)");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
