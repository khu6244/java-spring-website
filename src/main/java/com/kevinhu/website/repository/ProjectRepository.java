package com.kevinhu.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevinhu.website.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository <Project, Integer> {

}
