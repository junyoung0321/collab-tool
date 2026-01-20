package com.example.collab_tool.domain.project.dto;

import jakarta.validation.constraints.NotBlank;

public class ProjectCreateRequest {

    @NotBlank(message = "프로젝트 제목은 필수입니다.")
    private String title;

    private String description; // 설명은 비어있어도 됨

    public ProjectCreateRequest() {}

    public ProjectCreateRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
