package com.example.collab_tool.domain.task.service;

import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.domain.project.repository.ProjectRepository;
import com.example.collab_tool.domain.task.dto.TaskRequest;
import com.example.collab_tool.domain.task.dto.TaskResponse;
import com.example.collab_tool.domain.task.entity.Task;
import com.example.collab_tool.domain.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, String email, TaskRequest request, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("할 일이 없습니다."));

        if (!task.getProject().getOwner().getEmail().equals(email)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        task.update(request.getTitle(), request.getContent(), status);

        return new TaskResponse(task);
    }

    @Transactional
    public void deleteTask(Long taskId, String email) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("할 일이 없습니다."));

        if (!task.getProject().getOwner().getEmail().equals(email)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        taskRepository.delete(task);
    }
}
