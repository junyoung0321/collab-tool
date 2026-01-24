package com.example.collab_tool.domain.task.controller;

import com.example.collab_tool.domain.task.dto.TaskRequest;
import com.example.collab_tool.domain.task.dto.TaskResponse;
import com.example.collab_tool.domain.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @RequestBody @Valid TaskRequest request,
            @SessionAttribute(name = "LOGIN_USER", required = false) String email
    ) {
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 권한 없음
        }
        TaskResponse response = taskService.createTask(projectId, email, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
