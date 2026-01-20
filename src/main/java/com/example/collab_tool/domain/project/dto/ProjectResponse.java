package com.example.collab_tool.domain.project.dto;

import com.example.collab_tool.domain.project.entity.Project;

public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private String ownerEmail; // 만든 사람 이메일

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.ownerEmail = project.getOwner().getEmail();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getOwnerEmail() { return ownerEmail; }
}
