package com.example.collab_tool.domain.project.entity;

import com.example.collab_tool.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class ProjectMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ProjectMemberRole role;

    protected ProjectMember() {}

    public ProjectMember(Project project, Member member, ProjectMemberRole role) {
        this.project = project;
        this.member = member;
        this.role = role;
    }

    public Project getProject() { return project; }
    public Member getMember() { return member; }
    public ProjectMemberRole getRole() { return role; }
}
