package com.example.collab_tool.domain.task.repository;

import com.example.collab_tool.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // 특정 프로젝트에 속한 모든 할 일 조회
    List<Task> findByProjectId(Long projectId);

    // 내가 담당자인 할 일 조회
    List<Task> findByAssigneeId(Long assigneeId);
}
