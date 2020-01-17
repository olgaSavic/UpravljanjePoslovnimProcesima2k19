package root.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
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
import root.demo.services.ValidacijaNOService;

@Controller
@RequestMapping("/newController")
public class NewController {
	
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
	ValidacijaNOService validacijaNOService ;

	// proces je zapocet, sad samo treba da se claim-uje novi task
	@GetMapping(path = "/novaNO/{id}", produces = "application/json")
    public @ResponseBody FormFieldsDto novaNO(@PathVariable String id) {

		Task task = taskService.createTaskQuery().processInstanceId(id).list().get(0);
		
		// imam task, treba mi forma
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		
		List<FormField> properties = tfd.getFormFields();
		// dobijem listu field-ova, samo treba da ih prikazem
		
        return new FormFieldsDto(task.getId(), id, properties);        
    }
	
	@PostMapping(path = "/posaljiNO/{taskId}", produces = "application/json")
    public @ResponseBody boolean posaljiNO(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);	
		
		boolean proslaValidacija = true ;
		
		// singleResult jer moze da vrati null ili samo jedan task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String processInstanceId = task.getProcessInstanceId();
		
		// postavim procesnu varijablu na bas tu instancu 
		// naucnaOblast je ime varijable, a dto je onaj koji je korisnik uneo
		runtimeService.setVariable(processInstanceId, "naucnaOblast", dto);
		
		formService.submitTaskForm(taskId, map);
		
		List<FormSubmissionDto> naucnaOblastForm = (List<FormSubmissionDto>)runtimeService.getVariable(processInstanceId, "naucnaOblast");
		proslaValidacija = validacijaNOService.proslaValidacija(naucnaOblastForm);
		
		
        return proslaValidacija;
    }
	// rezultat ovoga -> poslata forma za dodavanje nove naucne oblasti (zavrsen user task)
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	
	// za cekanje mejla
	@GetMapping(path = "/getEmail/{id}", produces = "application/json")
    public @ResponseBody FormFieldsDto getEmail(@PathVariable String id) {
		
		System.out.println("Get EMAIL usao sam!");

		Task task = taskService.createTaskQuery().processInstanceId(id).list().get(0);
		
		// imam task, treba mi forma
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		
		List<FormField> properties = tfd.getFormFields();
		// dobijem listu field-ova, samo treba da ih prikazem
		
        return new FormFieldsDto(task.getId(), id, properties);        
    }
	
	@PostMapping(path = "/posaljiEmailKraj/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity posaljiEmailKraj(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);	
		
		System.out.println("POSALJI EMAIL usao sam!");
		
		boolean proslaValidacija = true ;
		
		// singleResult jer moze da vrati null ili samo jedan task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String processInstanceId = task.getProcessInstanceId();
		
		// postavim procesnu varijablu na bas tu instancu 
		// naucnaOblast je ime varijable, a dto je onaj koji je korisnik uneo
		runtimeService.setVariable(processInstanceId, "mejlKraj", dto);
		
		formService.submitTaskForm(taskId, map);
		
		System.out.println("POSLAO SAM FORMU EMAIL");
		return new ResponseEntity<>(HttpStatus.OK);
		
    }
	// rezultat ovoga -> poslata forma za dodavanje nove naucne oblasti (zavrsen user task)
	
	
	
	
		
	
	
}
