package com.example.collab_tool.domain.project.controller;

import com.example.collab_tool.domain.project.dto.ProjectCreateRequest;
import com.example.collab_tool.domain.project.dto.ProjectResponse;
import com.example.collab_tool.domain.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.security.Principal;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.collab_tool.domain.project.dto.InviteRequest;

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
            @SessionAttribute(name = "LOGIN_USER", required = false) String email
    ) {

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ProjectResponse response = projectService.createProject(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @SessionAttribute(name = "LOGIN_USER", required = false) String email
    ) {
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ProjectResponse> responses = projectService.getProjects(email);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{projectId}/invite")
    public ResponseEntity<String> inviteMember(
            @PathVariable Long projectId,
            @RequestBody @Valid InviteRequest request,
            @SessionAttribute(name = "LOGIN_USER", required = false) String email
    ) {
        if (email == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        projectService.inviteMember(projectId, email, request.getEmail());

        return ResponseEntity.ok("멤버 초대가 완료되었습니다.");
    }
}
