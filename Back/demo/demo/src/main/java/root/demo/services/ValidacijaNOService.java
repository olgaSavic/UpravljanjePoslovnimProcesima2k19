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
public class ValidacijaNOService implements JavaDelegate
{
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissionDto> naucnaOblastForm = (List<FormSubmissionDto>)execution.getVariable("naucnaOblast");
	    System.out.println(naucnaOblastForm);
	    
	    boolean rezultatValidacije = proslaValidacija(naucnaOblastForm);
	    execution.setVariable("rezValidacijeNO", rezultatValidacije);
	    
	    if (rezultatValidacije == true)
	    {
	    	System.out.println("PROSLA VALIDACIJA NO!, a rezultat validacije je: " + rezultatValidacije);
	    }
	    
	    else
	    {
	    	System.out.println("NIJE PROSLA VALIDACIJA NO!, a rezultat validacije je: " + rezultatValidacije);
	    }    
		
	}
	
	public boolean proslaValidacija(List<FormSubmissionDto> naucnaOblastForm)
	{
		boolean rezultatValidacije = true ;
		
		for (FormSubmissionDto formField : naucnaOblastForm) {
			if(formField.getFieldId().equals("nazivNO")) 
			{
				rezultatValidacije = praznoPoljeIliSlova(formField.getFieldValue());

				System.out.print("Rezultat validacije je: " + rezultatValidacije);
				
			}
								
			
	      }
		
		return rezultatValidacije ;
	
	}


	public boolean praznoPoljeIliSlova(String data)
	{
		if(data.isEmpty()) 
		{
			return false;
		}
		
		for(Character c :data.toCharArray()) 
		{
			if(Character.isWhitespace(c)== false && Character.isLetter(c) == false) {
				return false;
			}
		}
		
		return true ;
	}
	
	
	

}
