package com.example.collab_tool.domain.task.controller;

import com.example.collab_tool.domain.task.dto.TaskRequest;
import com.example.collab_tool.domain.task.dto.TaskResponse;
import com.example.collab_tool.domain.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TaskResponse response = taskService.createTask(projectId, email, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long projectId) {
        List<TaskResponse> responses = taskService.getTasks(projectId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskRequest request,
            @RequestParam(required = false, defaultValue = "TODO") String status, // ?status=DONE
            @SessionAttribute(name = "LOGIN_USER", required = false) String email
    ) {
        if (email == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        TaskResponse response = taskService.updateTask(taskId, email, request, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long taskId,
            @SessionAttribute(name = "LOGIN_USER", required = false) String email
    ) {
        if (email == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        taskService.deleteTask(taskId, email);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
