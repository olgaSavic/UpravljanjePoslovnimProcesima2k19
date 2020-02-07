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
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import root.demo.model.Casopis;
import root.demo.model.FormFieldsDto;

import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.NaucnaOblastCasopis;
import root.demo.model.TaskDto;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastCasopisRepository;
import root.demo.services.ValidacijaService;

import java.io.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
	
	@Autowired
	CasopisRepository casopisRepository ;
	
	@Autowired
	KorisnikRepository korisnikRepository ;	
	
	@Autowired
	NaucnaOblastCasopisRepository noRepository ;
	
	// klik na zapocni proces koje stoji gore
	@GetMapping(path = "/startObradaProcess", produces = "application/json")
    public @ResponseBody FormFieldsDto startObradaProcess(@Context HttpServletRequest request) {
		
		System.out.println("USAO");
		//startujemo proces sa id 
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("obrada_teksta_proces");
                
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	
	// kada izabere da li zeli da se registruje ili ima nalog
	@PostMapping(path = "/nastaviDaljeReg/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity nastaviDaljeReg(@RequestBody List<FormSubmissonDTO> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);	
				
		// singleResult jer moze da vrati null ili samo jedan task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId, "nastavakDaljeReg", dto);
		
		formService.submitTaskForm(taskId, map);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
		
		
    }
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissonDTO> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissonDTO temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	// ucitavanje forme gde korisnik bira casopis
		@GetMapping(path = "/potvrdaNastavak/{processId}", produces = "application/json")
	    public @ResponseBody FormFieldsDto potvrdaNastavak(@PathVariable String processId) {

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
				nextTask.setAssignee("autor"); // Asignee taska je urednik
				taskService.saveTask(nextTask);
			}
			
			TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
			List<FormField> properties = tfd.getFormFields();
			
	        return new FormFieldsDto(nextTask.getId(), processId, properties);
	    }
		
		// klik kada izabere casopis, prelazak na servisni task za cuvanje izabranog casopisa
		@PostMapping(path = "/sacuvajIzborNastavak/{taskId}", produces = "application/json")
	    public @ResponseBody ResponseEntity sacuvajIzborNastavak(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
			
			HashMap<String, Object> map = this.mapListToDto(formData);
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processInstanceId = task.getProcessInstanceId();
			
			try{
				runtimeService.setVariable(processInstanceId, "potvrdaNastavak", formData);
				formService.submitTaskForm(taskId, map);
		     				    
			}catch(FormFieldValidationException e){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.OK);
	    }

	
	
	
	// ucitavanje forme gde korisnik bira casopis
	@GetMapping(path = "/sledeciTaskIzbor/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto sledeciTaskIzbor(@PathVariable String processId) {

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
			nextTask.setAssignee("autor"); // Asignee taska je urednik
			taskService.saveTask(nextTask);
		}
		
		TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDto(nextTask.getId(), processId, properties);
    }
	
	// klik kada izabere casopis, prelazak na servisni task za cuvanje izabranog casopisa
	@PostMapping(path = "/sacuvajIzabranCasopis/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity sacuvajIzabranCasopis(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto(formData);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		for(FormSubmissonDTO item: formData){
			String fieldId = item.getFieldId();
			
			if(fieldId.equals("casopisiL"))
			{
				if(item.getCategories().size() != 1){
					System.out.println("Mora biti izabran tacno jedan casopis!");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		
			
		}
		
		try{
			runtimeService.setVariable(processInstanceId, "izabranCasopis", formData);
			formService.submitTaskForm(taskId, map);
	     				    
		}catch(FormFieldValidationException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	// vraca sve casopise u sistemu koji su aktivni
	@RequestMapping(value="/getAllCasopisi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<Casopis>> getAllCasopisi(){		

		List<Casopis> casopisi = casopisRepository.findAll();
		List<Casopis> aktivni = new ArrayList<Casopis>();
		
		for (Casopis c: casopisi)
		{
			if (c.isAktivan())
			{
				aktivni.add(c);
			}
		}
		
		System.out.println("Casopisa ima: " + casopisi.size());
		return new ResponseEntity<List<Casopis>>(aktivni, HttpStatus.OK);
	}
	
	// korisnik unosi informacije o radu
	@GetMapping(path = "/unosInfoRad/{processId}", produces = "application/json")
    public @ResponseBody FormFieldsDto unosInfoRad(@PathVariable String processId) {

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
			nextTask.setAssignee("autor"); // Asignee taska je urednik
			taskService.saveTask(nextTask);
		}
		
		TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDto(nextTask.getId(), processId, properties);
    }
	
	// klik kada izabere casopis, prelazak na servisni task za cuvanje izabranog casopisa
	@PostMapping(path = "/sacuvajRad/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity sacuvajRad(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
		
		HashMap<String, Object> map = this.mapListToDto(formData);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		for(FormSubmissonDTO item: formData){
			String fieldId = item.getFieldId();
			
			if(fieldId.equals("naucnaOblastL"))
			{
				if(item.getCategories().size() != 1){
					System.out.println("Mora biti izabrana tacno jedna naucna oblast!");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		
			
		}
		
		try{
			runtimeService.setVariable(processInstanceId, "infoRad", formData);
			formService.submitTaskForm(taskId, map);
	     				    
		}catch(FormFieldValidationException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	/*
	@SuppressWarnings("restriction")
	@PostMapping("/form/{taskId}")
	public ResponseEntity<String> postFormFileds(@PathVariable("taskId") String taskId, @RequestBody FormDTO formDTO) throws IOException{

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskFormData tfd = formService.getTaskFormData(taskId);
		List<FormField> formFields = tfd.getFormFields();
		
		//provjera ispravnosti polja
		for (FormField field : formFields) {
			FormFieldDTO fieldDTO = formDTO.getFieldById(field.getId());
			
			//validacija po constraintovima
			List<FormFieldValidationConstraint> constraints = field.getValidationConstraints();
			for (FormFieldValidationConstraint constraint : constraints) {
				if(constraint.getName().equals("required")) {
					if(fieldDTO.getValue() == null || fieldDTO.getValue().equals("")) {
						return new ResponseEntity<>(fieldDTO.getLabel() + " field is mandatory!",HttpStatus.BAD_REQUEST);
					}
				}
			}
			
			//validacija po tipu
			if(fieldDTO.getType().equals("long")){
				try {
					Long.parseLong(fieldDTO.getValue());
				} catch (Exception e) {
					// TODO: handle exception
					return new ResponseEntity<>(fieldDTO.getLabel() + " field must be number!",HttpStatus.BAD_REQUEST);
				}
			}
			
		}
		
		//ako su sva polja uredu ubacujem ih u varijable ako je fajl samo ime ubacim
		for (FormField field : formFields) {
			FormFieldDTO fieldDTO = formDTO.getFieldById(field.getId());
			
			if(fieldDTO.getType().equals("file")) {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] decodedBytes = decoder.decodeBuffer(fieldDTO.getValue());

				File file = new File("pdf/" + fieldDTO.getFileName());;
				FileOutputStream fop = new FileOutputStream(file);

				fop.write(decodedBytes);
				fop.flush();
				fop.close();
				
				runtimeService.setVariable(task.getProcessInstanceId(), fieldDTO.getId(), fieldDTO.getFileName());
			}else {
				runtimeService.setVariable(task.getProcessInstanceId(), fieldDTO.getId(), fieldDTO.getValue());
			}
		}
		
		return new ResponseEntity<>("",HttpStatus.OK);
	}
	
	*/
	
	

	
	// KOMENTAR: METODA TREBA DA VRACA NAUCNE OBLASTI SAMO IZABRANOG CASOPISA
	@RequestMapping(value="/getNOCasopis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<List<NaucnaOblastCasopis>> getNOCasopis(){		
		
		List<NaucnaOblastCasopis> no = noRepository.findAll();
		
		return new ResponseEntity<List<NaucnaOblastCasopis>>(no, HttpStatus.OK);
	}
	
	
	// ucitavanje forme gde korisnik unosi koautore
		@GetMapping(path = "/sledeciTaskKoautor/{processId}", produces = "application/json")
	    public @ResponseBody FormFieldsDto sledeciTaskKoautor(@PathVariable String processId) {

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
				nextTask.setAssignee("autor"); // Asignee taska je urednik
				taskService.saveTask(nextTask);
			}
			
			TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
			List<FormField> properties = tfd.getFormFields();
			
	        return new FormFieldsDto(nextTask.getId(), processId, properties);
	    }
		
		// klik kada doda koautora, da ga sacuva
		@PostMapping(path = "/sacuvajKoautore/{taskId}", produces = "application/json")
	    public @ResponseBody ResponseEntity sacuvajKoautore(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
			
			HashMap<String, Object> map = this.mapListToDto(formData);
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processInstanceId = task.getProcessInstanceId();
						
			try{
				runtimeService.setVariable(processInstanceId, "koautor", formData);
				formService.submitTaskForm(taskId, map);
		     				    
			}catch(FormFieldValidationException e){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.OK);
	    }

		
				
	
	
	 
	
	
	

}
