package com.example.collab_tool.domain.task.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskRequest {

    @NotBlank(message = "할 일 제목은 필수입니다.")
    private String title;

    private String content;

    public TaskRequest() {}

    public TaskRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
}
