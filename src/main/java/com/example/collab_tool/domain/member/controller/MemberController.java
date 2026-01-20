package com.example.collab_tool.domain.member.controller;

import com.example.collab_tool.domain.member.dto.SignupRequest;
import com.example.collab_tool.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.collab_tool.domain.member.dto.LoginRequest;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request) {
        String token = memberService.login(request);

        return ResponseEntity.ok(Map.of("token", token)); // JSON 형태로 반환
    }
}
