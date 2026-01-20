package com.example.collab_tool.domain.project.controller;

import com.example.collab_tool.domain.project.dto.ProjectCreateRequest;
import com.example.collab_tool.domain.project.dto.ProjectResponse;
import com.example.collab_tool.domain.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody @Valid ProjectCreateRequest request,
            Principal principal // 토큰에서 이메일을 꺼냄
    ) {
        // principal.getName()에는 이메일이 들어있음
        ProjectResponse response = projectService.createProject(principal.getName(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
