package com.example.collab_tool.domain.task.dto;

import com.example.collab_tool.domain.task.entity.Task;

public class TaskResponse {

    private Long id;
    private String title;
    private String content;
    private String status;
    private String assigneeName; // 담당자 이름 (없으면 null)

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.content = task.getContent();
        this.status = task.getStatus();

        // 담당자가 있으면 이름 넣기
        if (task.getAssignee() != null) {
            this.assigneeName = task.getAssignee().getName();
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public String getAssigneeName() { return assigneeName; }
}
