package com.example.collab_tool.domain.project.repository;

import com.example.collab_tool.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 특정 회원이 만든 프로젝트 목 조회
    List<Project> findByOwnerId(Long ownerId);
}
