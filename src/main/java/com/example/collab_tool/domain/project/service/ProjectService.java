package com.example.collab_tool.domain.project.service;

import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.member.repository.MemberRepository;
import com.example.collab_tool.domain.project.dto.ProjectCreateRequest;
import com.example.collab_tool.domain.project.dto.ProjectResponse;
import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.domain.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    public ProjectService(ProjectRepository projectRepository, MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public ProjectResponse createProject(String email, ProjectCreateRequest request) {

        Member owner = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Project project = new Project(request.getTitle(), request.getDescription(), owner);
        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(savedProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getMyProjects(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Project> projects = projectRepository.findByOwnerId(member.getId());

        return projects.stream()
                .map(ProjectResponse::new)
                .collect(Collectors.toList());
    }
}
