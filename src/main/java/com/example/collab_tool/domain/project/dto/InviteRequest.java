package com.example.collab_tool.domain.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class InviteRequest {
    @NotBlank
    @Email
    private String email; // 초대할 이메일

    public String getEmail() { return email; }
}
