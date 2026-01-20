package com.example.collab_tool.domain.member.repository;

import com.example.collab_tool.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    // 회원가입 시 중복 확인
    boolean existsByEmail(String email);
}
