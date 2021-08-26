package com.kevinhu.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kevinhu.website.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {


	@Query("select a from Address a where (a.apartment = :#{#address.apartment} or a.apartment is null) "
			+ "and a.houseNumber = :#{#address.houseNumber} and a.street = :#{#address.street} "
			+ "and a.city = :#{#address.city} and a.province = :#{#address.province} "
			+ "and a.country = :#{#address.country} and a.postal = :#{#address.postal}")
	List<Address> findAddress(@Param("address") Address address);
}
