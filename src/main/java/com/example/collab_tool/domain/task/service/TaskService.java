package com.example.collab_tool.domain.task.service;

import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.domain.project.repository.ProjectRepository;
import com.example.collab_tool.domain.task.dto.TaskRequest;
import com.example.collab_tool.domain.task.dto.TaskResponse;
import com.example.collab_tool.domain.task.entity.Task;
import com.example.collab_tool.domain.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TaskResponse createTask(Long projectId, String email, TaskRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        // 권한 확인 예외 처리
        if (!project.getOwner().getEmail().equals(email)) {
            throw new IllegalArgumentException("이 프로젝트에 대한 권한이 없습니다.");
        }

        // 할 일 생성
        Task task = new Task(request.getTitle(), request.getContent(), "TODO", project, null);

        Task savedTask = taskRepository.save(task); //저장, 반환
        return new TaskResponse(savedTask);
    }
}
