package com.example.demo.project;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {
	private final ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		super();
		this.projectRepository = projectRepository;
	}

	public List<Project> getProjects() {
		return projectRepository.findAll();
	}

	public void addNewProject(Project project) {
		LocalDate startDate = project.getProject_start_date();
		LocalDate yearAfterStartDate = startDate.plusYears(1);
		LocalDate dueDate = project.getProject_due_date();
		
		// is year after start less than due date?
		boolean isStartLessThanDue = yearAfterStartDate.isBefore(dueDate);
		// is due date before start date?
		boolean isDueLessThanStart = dueDate.isBefore(startDate);
		
		if (isDueLessThanStart) {
			throw new IllegalStateException("Project end date " + dueDate + " cannot be before start date " + startDate);
		}
		
		if (isStartLessThanDue) {
			throw new IllegalStateException("Project end date " + dueDate + " exceeds a year after start date " + startDate);
		}
		
		projectRepository.save(project);
	}

	public void deleteProject(Long projectId) {
		boolean exists = projectRepository.existsById(projectId);
		
		if(!exists) {
			throw new IllegalStateException("Project with id " + projectId + " does not exist");
		}
		
		projectRepository.deleteById(projectId);
	}

	@Transactional
	public void updateProject(Long projectId, String project_name, LocalDate project_due_date) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new IllegalStateException("Project with id " + projectId + " does not exist"));
		
		if (project_name != null &&
				project_name.length() > 0 &&
				!Objects.equals(project.getProject_name(), project_name)) {
			project.setProject_name(project_name.trim());
		}
		
		if (project_due_date != null &&
				!Objects.equals(project.getProject_due_date(), project_due_date)) {
			LocalDate startDate = project.getProject_start_date();
			LocalDate yearAfterStartDate = startDate.plusYears(1);
			LocalDate dueDate = project_due_date;
			
			// is year after start less than due date?
			boolean isStartLessThanDue = yearAfterStartDate.isBefore(dueDate);
			// is due date before start date?
			boolean isDueLessThanStart = dueDate.isBefore(startDate);
			
			if (isDueLessThanStart) {
				throw new IllegalStateException("Project end date " + dueDate + " cannot be before start date " + startDate);
			}
			
			if (isStartLessThanDue) {
				throw new IllegalStateException("Project end date " + dueDate + " exceeds a year after start date " + startDate);
			}
			
			project.setProject_end_date(project_due_date);
		}
	}
}
