package com.example.collab_tool.domain.task.entity;

import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.global.common.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String status; // "TODO", "IN_PROGRESS", "DONE"

    // 프로젝트가 삭제 시, 할 일도 삭제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 담당자가 탈퇴 시 DB에 SET NULL
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id") // nullable = true (기본값)
    private Member assignee;

    protected Task() {}

    public Task(String title, String content, String status, Project project, Member assignee) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.project = project;
        this.assignee = assignee;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public Project getProject() { return project; }
    public Member getAssignee() { return assignee; }

    public void assignTo(Member member) {
        this.assignee = member;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void update(String title, String content, String status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }
}
