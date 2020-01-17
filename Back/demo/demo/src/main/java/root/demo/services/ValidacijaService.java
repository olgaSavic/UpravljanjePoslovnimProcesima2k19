package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.repository.KorisnikRepository;

@Service
public class ValidacijaService implements JavaDelegate
{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	    System.out.println(registration);
	    
	    boolean rezultatValidacije = proslaValidacija(registration);
	    execution.setVariable("rezValidacije", rezultatValidacije);
	    
	    if (rezultatValidacije == true)
	    {
	    	System.out.println("PROSLA VALIDACIJA!");
	    }
	    
	    else
	    {
	    	System.out.println("NIJE PROSLA VALIDACIJA!");
	    }
	    
	          
	    
	    
		
	}
	
	public boolean proslaValidacija(List<FormSubmissionDto> registration)
	{
		
		// MORAM UBACITI PROVERU AKO GA JE JEDNOM PREBACIO NA FALSE DA IDE NA BREAK, JER JE DOVOLJNO DA JEDNO POLJE BUDE NEISPRAVNO
		boolean rezultatValidacije = true ;
		
		for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) 
			{
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				
				rezultatValidacije = praznoSlovoBroj(formField.getFieldValue());

				System.out.print("Rezultat validacije USERNAME je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("password")) 
			{
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				rezultatValidacije = praznoSlovoBroj(formField.getFieldValue());

				System.out.print("Rezultat validacije PASSWORD je: " + rezultatValidacije);
			
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("email")) 
			{
				
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				rezultatValidacije = checkMail(formField.getFieldValue());
				System.out.print("Rezultat validacije EMAIL je: " + rezultatValidacije);

				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("ime")) 
			{
				
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				rezultatValidacije = praznoPoljeIliSlova(formField.getFieldValue());
				
				System.out.print("Rezultat validacije IME je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("prezime")) 
			{
				
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				rezultatValidacije = praznoPoljeIliSlova(formField.getFieldValue());
				
				System.out.print("Rezultat validacije je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			// dodatno za @Entity korisnika u bazi
			if(formField.getFieldId().equals("drzava")) 
			{
				
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				rezultatValidacije = praznoPoljeIliSlova(formField.getFieldValue());
					
				
				System.out.print("Rezultat validacije je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("grad")) 
			{
				
				if (formField.getFieldValue().isEmpty() == true)
				{
					rezultatValidacije = false ;
					break ;
				}
				rezultatValidacije = praznoPoljeIliSlova(formField.getFieldValue());
				
				System.out.print("Rezultat validacije je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
			
			if(formField.getFieldId().equals("titula")) 
			{
				rezultatValidacije = proveraSlovo(formField.getFieldValue());
			
				
				System.out.print("Rezultat validacije je: " + rezultatValidacije);
				
				if (rezultatValidacije == false)
				{
					break ;
				}
			}
						
			
	      }
		
		return rezultatValidacije ;
	
	}
	
	
    public boolean checkMail(String mail) 
    {
	    if(mail.isEmpty()) {
			return false;
		}
		if(mail.contains(";")) {
			return false;
		}
		
		if(mail.contains(",")) {
			return false;
		}
		for(Character c:mail.toCharArray()) {
			if(Character.isWhitespace(c)) {
				return false;
			
			}
				
		}
		return true;
	}

	public boolean praznoSlovoBroj(String data) {
		if(data.isEmpty()) 
		{
			return false;
		}
		for(Character c :data.toCharArray()) 
		{
			if(Character.isWhitespace(c)== false && Character.isLetterOrDigit(c) == false) {
				return false;
			}
		}
		
		return true;
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
	
	public boolean proveraSlovo(String data)
	{
		
		for(Character c :data.toCharArray()) 
		{
			if(Character.isWhitespace(c)== false && Character.isLetter(c)== false) {
				return false;
			}
		}
		
		return true ;
	}
	
	

}
