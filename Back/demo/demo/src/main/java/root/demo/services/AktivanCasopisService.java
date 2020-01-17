package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;

@Service
public class AktivanCasopisService implements JavaDelegate{
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	@Autowired
	CasopisRepository casopisRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissonDTO> casopisAktivan = (List<FormSubmissonDTO>)execution.getVariable("casopisAktivan");
	    System.out.println(casopisAktivan);
	    
	    List<FormSubmissonDTO> kreiranjeCasopisa = (List<FormSubmissonDTO>)execution.getVariable("kreiranjeCasopisa");
	    System.out.println(kreiranjeCasopisa);
	    
	    String issn = "";
	    	    
	    for (FormSubmissonDTO formField : kreiranjeCasopisa) {
			if(formField.getFieldId().equals("issn")) {
				issn = formField.getFieldValue();
				System.out.println("ISSN je: " + issn);
			}
	    }
	    
	   Casopis casopis = casopisRepository.findOneByIssn(issn);
	    
	    String stringOdobrioGaJe = "" ;
	    
	    for (FormSubmissonDTO formField : casopisAktivan) {
	    	
			if(formField.getFieldId().equals("proveraUspesna")) { // ovo je polje sa forme
				
				if (formField.getFieldValue().equals("true"))
				{
					casopis.setAktivan(true);
					System.out.println("Aktivan!");
				}
				else
				{
					casopis.setAktivan(true);
					System.out.println("Nektivan!");

				}
							
			}
			
			
	    }
	     
	    casopisRepository.save(casopis);
	    execution.setVariable("aktivanCasopis", "aktivan");
		
	}

}
