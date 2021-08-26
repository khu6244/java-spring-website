package com.kevinhu.website.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kevinhu.website.dto.ProjectDTO;
import com.kevinhu.website.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectsController {
	
	private ProjectService projectService;
	private static Logger logger = LoggerFactory.getLogger(ProjectsController.class);
	private static int pageSize = 5;
	
	@Autowired
	public ProjectsController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@GetMapping("")
	public String index(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = 1;
		
		if(page.isPresent())
			currentPage = page.get();
		size.ifPresent(s -> pageSize = s);
		
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<ProjectDTO> projectPage = projectService.getAllProject(pageable);
		
		model.addAttribute("projectPage", projectPage);
		
		int totalPages= projectPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		return "projects";
	}
	
	@GetMapping(value = "/add")
	public String addProject(@Valid Model model) {
		model.addAttribute("project", new ProjectDTO());
		return "addproject";
	}
	
	@PostMapping(value = "/save") 
	public String save(ProjectDTO projectDTO) throws Exception {
		logger.info("project name: {0}", projectDTO.getName());
		projectService.saveProject(projectDTO);
		
		return "redirect:/projects";
	}
	
	@GetMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		ProjectDTO project = projectService.getProjectById(id);
		model.addAttribute("project", project);
		
		return "editproject";
	}
	
	@GetMapping(value="project-pages/{link}")
	public String showProject(@PathVariable("link") String link) {
		return "project-pages/" + link;
	}
	
	@GetMapping(value = "/deleteproject/{id}")
	public String deleteProjectPage(@PathVariable("id") Integer id, Model model) {
		ProjectDTO project = projectService.getProjectById(id);
		model.addAttribute("project", project);
		
		return "deleteproject";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteProject(@PathVariable("id") Integer id) {
		projectService.deleteProject(id);
		
		return "redirect:/projects";
	
	}
	
}
