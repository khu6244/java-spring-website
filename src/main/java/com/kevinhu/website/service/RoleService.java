package com.kevinhu.website.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinhu.website.dto.RoleDTO;
import com.kevinhu.website.repository.RoleRepository;

@Service
public class RoleService {

	private ModelMapper modelMapper;

	private RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}

	public List<RoleDTO> getAllRoles() {

		return roleRepository.findAll().stream().map(x -> modelMapper.map(x, RoleDTO.class))
				.collect(Collectors.toList());

	}

}
