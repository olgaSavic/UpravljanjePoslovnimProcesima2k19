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
public class RecenzentService implements JavaDelegate{
	
	@Autowired
	KorisnikRepository korisnikRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissionDto> recOdobren = (List<FormSubmissionDto>)execution.getVariable("recenzentOdobren");
	    System.out.println(recOdobren);
	    
	    List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	    System.out.println(registration);
	    
	    String usernameValue = "";
	    	    
	    for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				usernameValue = formField.getFieldValue();
				System.out.println("UsernameValue je: " + usernameValue);
			}
	    }
	    
	    Korisnik k = korisnikRepository.findOneByUsername(usernameValue);
	    System.out.println("Korisnik je: " + k.getIme());
	    
	    String stringOdobrioGaJe = "" ;
	    
	    for (FormSubmissionDto formField : recOdobren) {
	    	
			if(formField.getFieldId().equals("potvrdaRecenzenta")) { // ovo je polje sa forme
				System.out.println("Nasao sam polje, vrednost polja je: " + formField.getFieldValue());
				if (formField.getFieldValue().equals("true"))
				{
					System.out.println("Odobrio sam ga!");
					k.setOdobrenRecenzent(true);
					k.setTip("RECENZENT");
				    execution.setVariable("odobrenRecenzentKod", "odobrenRecenzent");

				}
				else
				{
					k.setOdobrenRecenzent(false);
					k.setTip("OBICAN");
				}
							
			}
	    }
	    
	    korisnikRepository.save(k);
		
	}


}
