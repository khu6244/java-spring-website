package com.kevinhu.website.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinhu.website.dto.AddressDTO;
import com.kevinhu.website.repository.AddressRepository;

@Service
public class AddressService {
	
	private ModelMapper modelMapper;
	private AddressRepository addressRepository;
	
	@Autowired
	public AddressService(ModelMapper modelMapper, AddressRepository addressRepository) {
		this.modelMapper = modelMapper;
		this.addressRepository = addressRepository;
	}

	public List<AddressDTO> getAllAddresses() {
		return addressRepository.findAll().stream().map(x -> modelMapper.map(x, AddressDTO.class)).collect(Collectors.toList());
	}

}
