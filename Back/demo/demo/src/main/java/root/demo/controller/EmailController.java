package root.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import root.demo.services.SlanjeMejlaService;

@RestController
@RequestMapping(value = "email")
public class EmailController {
	
	@Autowired
	SlanjeMejlaService mejlService ;
	
	@RequestMapping(value="/aktivirajNalog/{email}", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public String aktivirajNalog(@PathVariable String email){
				
		String s = mejlService.aktivirajNalog(email);
		
		return s;
		
	}

}
