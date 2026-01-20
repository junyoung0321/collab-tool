package com.example.collab_tool.domain.project.entity;

import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.task.entity.Task; // Task는 나중에 만들 거라 빨간줄 뜰 수 있음
import com.example.collab_tool.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 (성능 최적화 필수)
    @JoinColumn(name = "owner_id", nullable = false) // 외래키 이름
    private Member owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    protected Project() {}

    public Project(String title, String description, Member owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Member getOwner() { return owner; }
    public List<Task> getTasks() { return tasks; }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
