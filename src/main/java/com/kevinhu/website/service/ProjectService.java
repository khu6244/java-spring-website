package com.kevinhu.website.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kevinhu.website.dto.ProjectDTO;
import com.kevinhu.website.model.Project;
import com.kevinhu.website.repository.ProjectRepository;

@Service
public class ProjectService {

	private static Logger logger = LoggerFactory.getLogger(ProjectService.class);
	private ProjectRepository projectRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper) {
		this.projectRepository = projectRepository;
		this.modelMapper = modelMapper;
	}

	public Page<ProjectDTO> getAllProject(Pageable pageable) {
		
		return projectRepository.findAll(pageable).map(x -> modelMapper.map(x,  ProjectDTO.class));
	}

	public void saveProject(ProjectDTO projectDTO) {
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		Project project = modelMapper.map(projectDTO, Project.class);
		
		projectRepository.save(project);
		
	}

	public ProjectDTO getProjectById(Integer id) {
		
		Optional<Project> project = projectRepository.findById(id);
		
		return modelMapper.map(project.get(), ProjectDTO.class);
		
	}

	public void deleteProject(Integer id) {
		projectRepository.deleteById(id);
		
	}
	
}
