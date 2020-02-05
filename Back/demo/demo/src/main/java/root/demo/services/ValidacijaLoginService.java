package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.repository.KorisnikRepository;

@Service
public class ValidacijaLoginService implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissionDto> login = (List<FormSubmissionDto>)execution.getVariable("login");
	    System.out.println(login);
	    
	    boolean rezultatValidacije = proslaValidacija(login);
	    execution.setVariable("rezValidacijeLogin", rezultatValidacije);
	    
	    if (rezultatValidacije == true)
	    {
	    	System.out.println("PROSLA VALIDACIJA LOGIN!");
	    }
	    
	    else
	    {
	    	System.out.println("NIJE PROSLA VALIDACIJA LOGIN!");
	    }
		
	}
	
	
	public boolean proslaValidacija(List<FormSubmissionDto> login)
	{
		
		boolean rezultatValidacije = true ;
		
		for (FormSubmissionDto formField : login) {
			if(formField.getFieldId().equals("usernameLogin")) 
			{
				rezultatValidacije = praznoPolje(formField.getFieldValue());

				System.out.print("Rezultat validacije Login je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("passwordLogin")) 
			{
				
				rezultatValidacije = praznoPolje(formField.getFieldValue());

				System.out.print("Rezultat validacije Login je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
									
			
	      }
		
		return rezultatValidacije ;
	
	}
	
	public boolean praznoPolje(String data)
	{
		if(data.isEmpty()) 
		{
			return false;
		}
		
		return true ;
		
	}

	

}
