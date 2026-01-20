package com.example.collab_tool.domain.member.dto;

import com.example.collab_tool.domain.member.entity.Member;

public class MemberResponse {

    private Long id;
    private String email;
    private String name;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }

    public String getName() { return name; }
}
