package com.kevinhu.website.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.kevinhu.website.dto.PhoneDTO;
import com.kevinhu.website.repository.PhoneRepository;


public class PhoneService {

private ModelMapper modelMapper;
private PhoneRepository phoneRepository;
	
	@Autowired
	public PhoneService(ModelMapper modelMapper, PhoneRepository phoneRepository) {
		this.modelMapper = modelMapper;
		this.phoneRepository = phoneRepository;
	}
	
	public List<PhoneDTO> getAllPhones() {
		return phoneRepository.findAll().stream().map(x -> modelMapper.map(x, PhoneDTO.class)).collect(Collectors.toList());
	}

}
