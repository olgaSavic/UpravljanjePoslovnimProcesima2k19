package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.repository.KorisnikRepository;

@Service
public class AktivanService implements JavaDelegate{
	
	@Autowired
	KorisnikRepository korisnikRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	    System.out.println(registration);
	    
	    String usernameValue = "";
	    
	    System.out.println("Usao u AktivanService!");
	    
	    for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				usernameValue = formField.getFieldValue();
				System.out.println("UsernameValue je: " + usernameValue);
			}
	    }
	    
	    Korisnik k = korisnikRepository.findOneByUsername(usernameValue);
	    k.setAktivan(true);
	    
	    korisnikRepository.save(k);
	    execution.setVariable("aktivanKorisnik", "aktivan");
		
	}

}
