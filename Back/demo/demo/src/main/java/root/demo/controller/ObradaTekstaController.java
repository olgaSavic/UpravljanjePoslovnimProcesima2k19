package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.cmd.GetDeploymentResourceNamesCmd;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.TaskDto;
import root.demo.services.ValidacijaService;

@Controller
@RequestMapping("/obrada")
public class ObradaTekstaController 
{

	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	ValidacijaService validationService ;
	
	@GetMapping(path = "/startObradaProcess", produces = "application/json")
    public @ResponseBody FormFieldsDto startObradaProcess(@Context HttpServletRequest request) {
		
		System.out.println("USAO");
		//startujemo proces sa id 
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("obrada_teksta_proces");
		
		/*
        Korisnik k=(Korisnik) request.getAttribute("logged");
        
        
        
        if (k != null) // neko je ulogovan
        {
        	runtimeService.setVariable(pi.getId(), "rezUlogovan" , true);
        }
        else
        {
        	runtimeService.setVariable(pi.getId(), "rezUlogovan" , false);
        }
        
        */
		
		runtimeService.setVariable(pi.getId(), "rezUlogovan" , true);
                
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }	

}
