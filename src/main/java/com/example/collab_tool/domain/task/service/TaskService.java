package com.example.collab_tool.domain.task.service;

import com.example.collab_tool.domain.member.entity.Member;
import com.example.collab_tool.domain.member.repository.MemberRepository;
import com.example.collab_tool.domain.project.entity.Project;
import com.example.collab_tool.domain.project.repository.ProjectMemberRepository;
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
    private final MemberRepository memberRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       MemberRepository memberRepository,
                       ProjectMemberRepository projectMemberRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    public TaskResponse createTask(Long projectId, String email, TaskRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트 없음"));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        //권한 체크
        validatePermission(project, member);

        Task task = new Task(request.getTitle(), request.getContent(), "TODO", project, null); // 담당자는 null
        return new TaskResponse(taskRepository.save(task));
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

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        validatePermission(task.getProject(), member);

        task.update(request.getTitle(), request.getContent(), status);
        return new TaskResponse(task);
    }

    @Transactional
    public void deleteTask(Long taskId, String email) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("할 일이 없습니다."));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));

        validatePermission(task.getProject(), member);
        taskRepository.delete(task);
    }

    private void validatePermission(Project project, Member member) {
        if (project.getOwner().getId().equals(member.getId())) {
            return;
        }

        if (projectMemberRepository.existsByProjectAndMember(project, member)) {
            return;
        }

        throw new IllegalArgumentException("프로젝트 멤버만 접근할 수 있습니다.");
    }
}
