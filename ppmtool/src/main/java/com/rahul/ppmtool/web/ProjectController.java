package com.rahul.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.ppmtool.domain.Project;
import com.rahul.ppmtool.services.MapValidationErrorService;
import com.rahul.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService ;
	
	  @PostMapping("")
	    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){
		  //Reduces the error provided by postman
//		  if(result.hasErrors()) {
//			  return new ResponseEntity<String>("Invalid Project Object", HttpStatus.BAD_REQUEST);
//		  }
		 
		  //Structuring the postman errors in map
//		  if(result.hasErrors()) {
//			  Map<String, String> errorMap = new HashMap<>();
//			  
//			  for(FieldError error : result.getFieldErrors()) {
//				  errorMap.put(error.getField(), error.getDefaultMessage());
//			  }
//			  
//			  return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
//		  }
		  
		    ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
	        if(errorMap!=null) return errorMap;
		  
	        Project project1 = projectService.saveOrUpdateProject(project);
	        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	    }
	  
	  @GetMapping("/{projectId}")
	  public ResponseEntity<?> getProjectId(@PathVariable String projectId){
		  
		  Project project = projectService.findProjectByIdentifier(projectId);
		  
		  return new ResponseEntity<Project>(project,HttpStatus.OK);
	  }
	  
	  
	  @GetMapping("/all")
	  public Iterable<Project> getAllProjects(){return projectService.findAllProjects();}

	  
	  @DeleteMapping("/{projectId}")
	  public ResponseEntity<?> deleteProject(@PathVariable String projectId){
		  projectService.deleteProjectByIdentifier(projectId);
		  
		  return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
	  }
}
