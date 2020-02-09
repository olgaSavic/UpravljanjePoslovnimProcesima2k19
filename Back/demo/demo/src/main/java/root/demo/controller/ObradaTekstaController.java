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

import root.demo.model.FormSubmissionWithFileDto;
import root.demo.model.Casopis;
import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
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
	
	// ucitavanje forme gde korisnik potvrdjuje da zeli da nastavi dalje u proces
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
			
			TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
			List<FormField> properties = tfd.getFormFields();
			
	        return new FormFieldsDto(nextTask.getId(), processId, properties);
	    }
		
		@RequestMapping(value="/trenutniKorisnik",method = RequestMethod.GET)
		public Korisnik getCurrentUser() {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			System.out.println("Principal.toString() je: " + principal.toString());
			Korisnik k = korisnikRepository.findOneByUsername(principal.toString());
			if (k != null) 
			{
				String kIme = k.getUsername();
				System.out.println("Username getCurrentUser je: " + kIme);
			}
			else 
			{
				System.out.println("Korisnik je null!");
			}
			
			
			return k;
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
				
				Korisnik autor = getCurrentUser();
				System.out.println("Postavljen je autor nakon potvrde nastavka na: " + autor.getUsername());
				runtimeService.setVariable(processInstanceId, "autor", autor.getUsername());
		     				    
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
		
		Casopis casopis = new Casopis();
		for(FormSubmissonDTO item: formData){
			String fieldId = item.getFieldId();
			
			
			if(fieldId.equals("casopisiL"))
			{
				if(item.getCategories().size() != 1){
					System.out.println("Mora biti izabran tacno jedan casopis!");	
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				List<Casopis> allCasopisi = casopisRepository.findAll();
				  for(Casopis c : allCasopisi){
					  for(String selectedEd:item.getCategories())
					  {
						  String idS= c.getId().toString();
						  
						  if(idS.equals(selectedEd)){
							  System.out.println(c.getNaziv());
							  casopis = casopisRepository.findOneByIssn(c.getIssn());
							  System.out.println("Naziv izabranog casopisa je: " + casopis.getNaziv());
							  
							  break ;
							  
						  }
					  }
				  }
			}
		
			
		}
		
		try{
			System.out.println("Postavljen je glavniUrednikVar na: " + casopis.getGlavniUrednik().getUsername());
			runtimeService.setVariable(processInstanceId, "glavniUrednikVar", casopis.getGlavniUrednik().getUsername());
			
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
	
	@PostMapping(path = "/sacuvajRadSaPdf/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity sacuvajRadSaPdf(@RequestBody FormSubmissionWithFileDto dto, @PathVariable String taskId) throws IOException {
		System.out.println("Usao u sacuvaj rad sa pdf!");
		HashMap<String, Object> map = this.mapListToDto(dto.getForm());
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId, "infoRad", dto.getForm()); 
		formService.submitTaskForm(taskId, map);
		
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());

		File file = new File("pdf/" + dto.getFileName());
		
		runtimeService.setVariable(processInstanceId, "pdfRad", decodedBytes); 
		runtimeService.setVariable(processInstanceId, "pdfFileName", dto.getFileName()); 
		
		System.out.println("U varijablu je sacuvan rad sa nazivom: " + dto.getFileName());
		FileOutputStream fop = new FileOutputStream(file);

		fop.write(decodedBytes);
		fop.flush();
		fop.close();
		
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
	
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

		// metoda koja vraca taskove trenutnog korisnika (autora ili urednika - glavnog)
		 @GetMapping(path = "/getTasksUser/{processId}/{username}", produces = "application/json")
		   public @ResponseBody ResponseEntity<List<TaskDto>> getTasksUser(@PathVariable String processId, @PathVariable String username) {
		     Korisnik user = korisnikRepository.findOneByUsername(username);
		     List<TaskDto> dtos = new ArrayList<TaskDto>();
		     List<Korisnik> allUsers = korisnikRepository.findAll();

		       for(Korisnik u : allUsers){
		          if(u.getUsername().equals(username)){
		             user=u;
		          }
		       }
		       
		     List<Task> tasks = new ArrayList<Task>();
		     if(user.getTip().equals("UREDNIK")) // kupi taskove od urednika
		     {
		        tasks.addAll(taskService.createTaskQuery().processDefinitionKey("obrada_teksta_proces").taskAssignee(user.getUsername()).list());
		     }
		     
		     // KOMENTAR: mozda promeniti na trenutno ulogovanog, jer ima vise autora
		     else if (user.getTip().equals("AUTOR"))// kupi taskove od autora
		     {
		        tasks.addAll(taskService.createTaskQuery().processDefinitionKey("obrada_teksta_proces").taskAssignee(user.getUsername()).list());
		     }
		     for (Task task : tasks) {
		    	 TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
		        dtos.add(t);
		     }	    
		       return new ResponseEntity(dtos,  HttpStatus.OK);
		   }		
		 
		 // ucitavanje forme gde urednik pregleda ono uneseno o radu
		 @GetMapping(path = "/sledeciTaskPregledUrednik/{processId}", produces = "application/json")
		    public @ResponseBody FormFieldsDto sledeciTaskPregledUrednik(@PathVariable String processId) {

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
				
				TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
				List<FormField> properties = tfd.getFormFields();
				
				
				
		        return new FormFieldsDto(nextTask.getId(), processId, properties);
		    }
			
		 	// urednik nakon sto pregleda rad i klikne na submit
			@PostMapping(path = "/sacuvajPregledUrednika/{taskId}", produces = "application/json")
		    public @ResponseBody ResponseEntity sacuvajPregledUrednika(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
				
				HashMap<String, Object> map = this.mapListToDto(formData);
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				String processInstanceId = task.getProcessInstanceId();
				
				TaskFormData tfd = formService.getTaskFormData(taskId);
				List<FormField> formFields = tfd.getFormFields();
							
				try{
					runtimeService.setVariable(processInstanceId, "pregledUrednika", formData);
					formService.submitTaskForm(taskId, map);
			     				    
				}catch(FormFieldValidationException e){
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>(HttpStatus.OK);
		    }

			 // ucitavanje forme gde urednik pregleda pdf, nakon sto je potvrdio da je rad tematski prihvatljiv
			 @GetMapping(path = "/sledeciTaskPregledPdfUrednik/{processId}", produces = "application/json")
			    public @ResponseBody FormFieldsDto sledeciTaskPregledPdfUrednik(@PathVariable String processId) {

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
					
					TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
					List<FormField> properties = tfd.getFormFields();
	
					
			        return new FormFieldsDto(nextTask.getId(), processId, properties);
			    }	
			 
			 	// urednik nakon sto pregleda pdf i klikne na submit
				@PostMapping(path = "/sacuvajPregledUrednikaPdf/{taskId}", produces = "application/json")
			    public @ResponseBody ResponseEntity sacuvajPregledUrednikaPdf(@RequestBody List<FormSubmissonDTO> formData, @PathVariable String taskId) {
					
					HashMap<String, Object> map = this.mapListToDto(formData);
					Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
					String processInstanceId = task.getProcessInstanceId();
					
					TaskFormData tfd = formService.getTaskFormData(taskId);
					List<FormField> formFields = tfd.getFormFields();
	
								
					try{
						runtimeService.setVariable(processInstanceId, "pregledUrednikaPdf", formData);
						formService.submitTaskForm(taskId, map);
				     				    
					}catch(FormFieldValidationException e){
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
					return new ResponseEntity<>(HttpStatus.OK);
			    }	
				
				
				 // ucitavanje forme gde autor pregleda svoj rad
				 @GetMapping(path = "/sledeciTaskAutorKorekcija/{processId}", produces = "application/json")
				    public @ResponseBody FormFieldsDto sledeciTaskAutorKorekcija(@PathVariable String processId) {

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
						
						TaskFormData tfd = formService.getTaskFormData(nextTask.getId());
						List<FormField> properties = tfd.getFormFields();
						
				        return new FormFieldsDto(nextTask.getId(), processId, properties);
				    }
				 
					@PostMapping(path = "/sacuvajKorekcijuAutorSaPdf/{taskId}", produces = "application/json")
				    public @ResponseBody ResponseEntity sacuvajKorekcijuAutorSaPdf(@RequestBody FormSubmissionWithFileDto dto, @PathVariable String taskId) throws IOException {
						HashMap<String, Object> map = this.mapListToDto(dto.getForm());
						Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
						String processInstanceId = task.getProcessInstanceId();
						
						//runtimeService.setVariable(processInstanceId, "infoRad", dto.getForm()); 
						formService.submitTaskForm(taskId, map);
						
						BASE64Decoder decoder = new BASE64Decoder();
						byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());

						File file = new File("pdf/" + dto.getFileName());
						
						runtimeService.setVariable(processInstanceId, "pdfRad", decodedBytes); 
						runtimeService.setVariable(processInstanceId, "pdfFileName", dto.getFileName()); 
						
						System.out.println("U varijablu je sacuvan novi rad sa nazivom: " + dto.getFileName());
						FileOutputStream fop = new FileOutputStream(file);

						fop.write(decodedBytes);
						fop.flush();
						fop.close();
						
				        return new ResponseEntity<>(HttpStatus.ACCEPTED);
				    }

				 
			@RequestMapping(value="/getRecenzentiCasopis/{processId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)	
			public ResponseEntity<List<Korisnik>> getRecenzentiCasopis(@PathVariable String processId)
			{		

				List<Casopis> casopisi = casopisRepository.findAll();
				List<Korisnik> sviKorisnici = korisnikRepository.findAll();
				List<Korisnik> recenzenti = new ArrayList<Korisnik>();
				
				List<FormSubmissonDTO> izabranCasopisForm = (List<FormSubmissonDTO>)runtimeService.getVariable(processId, "izabranCasopis");
				Casopis casopis = new Casopis();
				 
				 for(FormSubmissonDTO item: izabranCasopisForm)
				  {
					  String fieldId=item.getFieldId();
					  
					 if(fieldId.equals("casopisiL")){
						  
						  List<Casopis> allCasopisi = casopisRepository.findAll();
						  for(Casopis c : allCasopisi){
							  for(String selectedEd:item.getCategories())
							  {
								  String idS= c.getId().toString();
								  
								  if(idS.equals(selectedEd)){
									  System.out.println(c.getNaziv());
									  casopis = casopisRepository.findOneByIssn(c.getIssn());
									  System.out.println("Naziv izabranog casopisa je: " + casopis.getNaziv());
									  break ;
									  
								  }
							  }
						  }
					 }
					 
					}
				for (Korisnik k: casopis.getRecenzentiCasopis())
				{
					recenzenti.add(k);
				}
				System.out.println("Recenzenata ima: " + recenzenti.size());
				return new ResponseEntity<List<Korisnik>>(recenzenti, HttpStatus.OK);
			}				

}
