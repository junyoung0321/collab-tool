package com.example.collab_tool.domain.project.repository;

import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.domain.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    // 이미 초대된 사람인지 확인용
    boolean existsByProjectAndMember(Project project, Member member);

    List<ProjectMember> findByMemberEmail(String email);

    List<ProjectMember> findByProjectId(Long projectId);
}
