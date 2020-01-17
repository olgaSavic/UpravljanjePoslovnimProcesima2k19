package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.TipKorisnika;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;

// Kada Camundin Engine dodje do servisnog taska
@Service
public class CuvanjeCasopisaService implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	@Autowired
	CasopisRepository casopisRepository ;
	
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 String var = "Pera";
	      var = var.toUpperCase();
	      execution.setVariable("input", var);
	      List<FormSubmissonDTO> kreiranjeCasopisa = (List<FormSubmissonDTO>)execution.getVariable("kreiranjeCasopisa");
	      System.out.println(kreiranjeCasopisa);
	      
	      Casopis casopis = new Casopis();
	      
	      for (FormSubmissonDTO formField : kreiranjeCasopisa) {
			
			if(formField.getFieldId().equals("naziv")) {
				casopis.setNaziv(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("issn")) {
				casopis.setIssn(formField.getFieldValue());
			}
			
			if(formField.getFieldId().equals("openAccess")) {
				
				System.out.println("Vrednost openAccess je: " + formField.getFieldValue().toString());
				if (formField.getFieldValue().toString().equals("true"))
				{
					casopis.setOpenAccess(true);
				}
				else
				{
					casopis.setOpenAccess(false);
				}
				
			}
			
			casopis.setAktivan(false);
			
			
	      }
	      
	      casopisRepository.save(casopis); // Korisnik u bazi
	      execution.setVariable("aktivanCasopis", "neaktivan");

	      
	      
	}


}
