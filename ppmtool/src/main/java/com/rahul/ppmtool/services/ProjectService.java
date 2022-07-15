package com.rahul.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.ppmtool.domain.Project;
import com.rahul.ppmtool.exceptions.ProjectIdException;
import com.rahul.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	 public Project saveOrUpdateProject(Project project){

	        //Logic
		 	try {
		 		 project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		 		return projectRepository.save(project);
		 	}
		 	catch(Exception e) {
		 		 throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
	        }

	        //Logic

	        //return projectRepository.save(project);
	    }
	 
	 public Project findProjectByIdentifier(String projectId) {
		 
		 Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		 
		 if(project == null) {
			 throw new ProjectIdException("Project ID '"+projectId+"' does not exists");
			 
		 }
		 
		 return project;
		 
	 }

	}
