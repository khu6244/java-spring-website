package com.kevinhu.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kevinhu.website.model.Phone;


@Repository
public interface PhoneRepository extends JpaRepository <Phone, Long> {

	@Query("select p from Phone p where p.countryCode = :#{#phone.countryCode} "
			+ "and p.areaCode = :#{#phone.areaCode} and p.prefix = :#{#phone.prefix} "
			+ "and p.lineNumber = :#{#phone.lineNumber} and (p.extension = :#{#phone.extension} or p.extension is null)")
	List<Phone> findPhone(@Param("phone") Phone phone);
	
}