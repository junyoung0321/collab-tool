package com.example.collab_tool.domain.project.service;

import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.member.repository.MemberRepository;
import com.example.collab_tool.domain.project.dto.ProjectCreateRequest;
import com.example.collab_tool.domain.project.dto.ProjectResponse;
import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.domain.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.collab_tool.domain.project.entity.ProjectMember;
import com.example.collab_tool.domain.project.entity.ProjectMemberRole;
import com.example.collab_tool.domain.project.repository.ProjectMemberRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository, MemberRepository memberRepository,
                          ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.projectMemberRepository = projectMemberRepository;
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
    public List<ProjectResponse> getProjects(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Project> myProjects = projectRepository.findByOwnerId(member.getId());

        List<ProjectMember> invitedMemberships = projectMemberRepository.findByMemberEmail(email);

        List<Project> invitedProjects = invitedMemberships.stream()
                .map(ProjectMember::getProject)
                .toList();

        List<Project> allProjects = new ArrayList<>();
        allProjects.addAll(myProjects);
        allProjects.addAll(invitedProjects);

        return allProjects.stream()
                .map(ProjectResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void inviteMember(Long projectId, String ownerEmail, String inviteEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트가 없습니다."));

        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new IllegalArgumentException("초대 권한이 없습니다. (프로젝트 소유자만 가능)");
        }

        Member invitee = memberRepository.findByEmail(inviteEmail)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 유저입니다."));

        if (projectMemberRepository.existsByProjectAndMember(project, invitee)) {
            throw new IllegalArgumentException("이미 프로젝트 멤버입니다.");
        }

        ProjectMember newMember = new ProjectMember(project, invitee, ProjectMemberRole.MEMBER);
        projectMemberRepository.save(newMember);
    }
}
