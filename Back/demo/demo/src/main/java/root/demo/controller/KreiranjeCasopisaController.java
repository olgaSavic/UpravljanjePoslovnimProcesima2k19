package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.TaskDto;
import root.demo.repository.KorisnikRepository;
import root.demo.services.ValidacijaService;

@Controller
@RequestMapping("/kreiranjeCasopisa")
public class KreiranjeCasopisaController {
	
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
	
	@Autowired
	private KorisnikRepository korisnikRepository ;
	
	// PART 1
	@GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {

		ProcessInstance pi = runtimeService.startProcessInstanceByKey("kreiranjeCasopisa");
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		task.setAssignee("urednik");
		taskService.saveTask(task);
		
		// imam task, treba mi forma
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		
		List<FormField> properties = tfd.getFormFields();
		// dobijem listu field-ova, samo treba da ih prikazem
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
        
        // nakon ovoga gledam HTML
    }
	
	// PART 2
	// pomocu ove metode se vrsi SUBMIT forme
	// treba da complete-ujem task, da bih nastavila dalje
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissonDTO> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDTO2(dto);		
		
		boolean proslaValidacija = true ;
		
		// singleResult jer moze da vrati null ili samo jedan task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String processInstanceId = task.getProcessInstanceId();
		
		for(FormSubmissonDTO item: dto){
			String fieldId = item.getFieldId();
			System.out.println("Id polja je "+fieldId);
			System.out.println("Vrednost polja je "+item.getFieldValue());
			if(!fieldId.equals("openAccess")){
				if(item.getFieldValue()==null || item.getFieldValue().equals("") || item.getFieldValue().isEmpty()==true){
					System.out.println("Prazno je");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		
			
		}
		
		// postavim procesnu varijablu na bas tu instancu 
		// registration je ime varijable, a dto je onaj koji je korisnik uneo
		runtimeService.setVariable(processInstanceId, "kreiranjeCasopisa", dto);
		
		// try / catch ovde da ne bi puklo zbog validacije
		formService.submitTaskForm(taskId, map);
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
	// rezultat ovoga -> uspesna registracija
	

	
	private HashMap<String, Object> mapListToDTO2(List<FormSubmissonDTO> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissonDTO temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	

}
