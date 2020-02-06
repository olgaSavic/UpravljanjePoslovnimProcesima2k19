package root.demo.controller;

import java.security.NoSuchAlgorithmException;
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
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.repository.KorisnikRepository;
import root.demo.services.TestService;
import root.demo.services.ValidacijaLoginService;
import root.demo.services.ValidacijaService;

@RestController
@RequestMapping(value = "korisnik")
public class KorisnikController {
	
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
	TestService testService ;
	
	@Autowired
	ValidacijaLoginService validationService ;
	
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
    public  ResponseEntity<Korisnik> login(@RequestBody Korisnik korisnik, @Context HttpServletRequest request){
	   
       String encryptedPass = "";
       String username = korisnik.getUsername();
       String password = korisnik.getPassword();

       Korisnik loginUser = korisnikRepository.findOneByUsername(username);
       
       if(loginUser == null){
    	    System.out.println("Ne postoji username");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
       }
       
	   if(!loginUser.isAktivan()){
   	        System.out.println("Nije aktiviran");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);

	   }  
	   
	   // hesovanje lozinke i provera
	   	try {
			encryptedPass = testService.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!loginUser.getPassword().equals(encryptedPass)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);

		}
	   
		request.getSession().setAttribute("logged", loginUser);

        System.out.println(username + " , " + password);
		return new ResponseEntity<Korisnik>(loginUser, HttpStatus.OK);
    }
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public Korisnik logout(@Context HttpServletRequest request)
	{
		Korisnik user = (Korisnik)request.getSession().getAttribute("logged");		
		System.out.println("Usao u funkciju logout");
		
		request.getSession().invalidate();
		if(user == null) 
		{
			return null;
		}
		return user;
	}
	
	
	// ADMIN LOGOVANJE
	@RequestMapping(value="/loginAdmin",method = RequestMethod.POST)
    public  ResponseEntity<Korisnik> loginAdmin(@RequestBody Korisnik korisnik, @Context HttpServletRequest request){
	   
	   String encryptedPass = "";
       String username = korisnik.getUsername();
       String password = korisnik.getPassword();

       Korisnik loginUser = korisnikRepository.findOneByUsername(username);
       
       if(loginUser == null){
    	    System.out.println("Ne postoji username");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
       }
       
	   if(!loginUser.isAktivan()){
   	        System.out.println("Nije aktiviran");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);

	   }
	   
	   
	   if (!loginUser.getTip().equals("ADMIN"))
	   {
		   System.out.println("Korisnik nije admin, pa ne moze da se uloguje preko ove forme!");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
	   }
	    

	   // hesovanje lozinke i provera
	   	try {
			encryptedPass = testService.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!loginUser.getPassword().equals(encryptedPass)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);

		}
	   
	   /*
	   if(!loginUser.getPassword().equals(password)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
			System.out.println("Ne poklapaju se sifre");
			return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
	   }
	   */
	   
	   
		request.getSession().setAttribute("logged", loginUser);

        System.out.println(username + " , " + password);
		return new ResponseEntity<Korisnik>(loginUser, HttpStatus.OK);
    }
	
	@GetMapping(value="/predjiDalje/{email}")
	public ResponseEntity<String> predjiDalje(@PathVariable String email)
	{
		System.out.println("Mejl u predjiDalje je: " + email);
		String novi = email + ".com";
		Korisnik k = korisnikRepository.findOneByEmail(novi);
		
		boolean povratna;
		
		if (k.isRecenzent() == true)
		{
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// SPRING SECURITY - VRATI TRENUTNOG KORISNIKA
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
	
	
	
	
	

	
	
}
