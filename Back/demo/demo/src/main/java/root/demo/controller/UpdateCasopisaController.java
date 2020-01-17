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
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import root.demo.repository.KorisnikRepository;
import root.demo.services.ValidacijaService;
import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.TaskDto;


@RestController
@RequestMapping("/casopis")
public class UpdateCasopisaController {
	
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

	
	@PostMapping(path = "/update/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity update(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto(formData);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		for(FormSubmissonDTO item: formData){
			String fieldId = item.getFieldId();
			
			/*
			if(fieldId.equals("uredniciL"))
			{
				if(item.getCategories().size()>2)
				{
					System.out.println("Mora biti dva urednika naucne oblasti");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

				}
			}
			
			*/
			if(fieldId.equals("recenzentiL"))
			{
				if(item.getCategories().size()<2){
					System.out.println("Nisu 2 recenzenta");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		
			
		}
		
		try{
			runtimeService.setVariable(processInstanceId, "updateData", formData);
			formService.submitTaskForm(taskId, map);
	     				    
		}catch(FormFieldValidationException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/sledeciTaskCasopis/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getNextTask(@PathVariable String processId) {

		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
		List<TaskDto> taskDTOList = new ArrayList<TaskDto>();
		
		if(tasks.size()==0){
			System.out.println("Prazna lista, nema vise taskova");
		}
		for(Task T: tasks)
		{
			System.out.println("Dodaje task "+T.getName());
			taskDTOList.add(new TaskDto(T.getId(), T.getName(), T.getAssignee()));
		}
		
		Task nextTask = tasks.get(0);
		
		// ukoliko mu nije dodeljen Asignee, ili ako je dodeljen neko ko nije demo
		if(nextTask.getAssignee()==null || !nextTask.getAssignee().equals("demo")){
			nextTask.setAssignee("urednik"); // Asignee taska je urednik
			taskService.saveTask(nextTask);
		}
		
		TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDto(nextTask.getId(), processId, properties);
    }
	
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissonDTO> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissonDTO temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}


	@RequestMapping(value="/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<Korisnik>> getAllUsers(){		

		List<Korisnik> sviKorisnici = korisnikRepository.findAll();
		return new ResponseEntity<List<Korisnik>>(sviKorisnici, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllRecenzenti", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<Korisnik>> getAllRecenzenti(){		
		System.out.println("Usao u getAllRecenzenti");
		List<Korisnik> sviKorisnici = korisnikRepository.findAll();
		List<Korisnik> recenzenti = new ArrayList<Korisnik>();
		
		for (Korisnik k: sviKorisnici)
		{
			if (k.getTip().equals("RECENZENT"))
			{
				recenzenti.add(k);
			}
		}
		
		return new ResponseEntity<List<Korisnik>>(recenzenti, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllUrednici", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<Korisnik>> getAllUrednici(){		
		System.out.println("Usao u getAllUrednici");
		List<Korisnik> sviKorisnici = korisnikRepository.findAll();
		List<Korisnik> urednici = new ArrayList<Korisnik>();
		
		for (Korisnik k: sviKorisnici)
		{
			if (k.getTip().equals("UREDNIK"))
			{
				urednici.add(k);
			}
		}
		
		return new ResponseEntity<List<Korisnik>>(urednici, HttpStatus.OK);
	}
	


}
