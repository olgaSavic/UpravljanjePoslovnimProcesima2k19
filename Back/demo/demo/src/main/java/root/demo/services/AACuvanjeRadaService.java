package root.demo.services;


import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissionWithFileDto;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.NaucnaOblast;
import root.demo.model.NaucnaOblastCasopis;
import root.demo.model.Rad;
import root.demo.model.TipKorisnika;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastCasopisRepository;
import root.demo.repository.RadRepository;

// Kada Camundin Engine dodje do servisnog taska
@Service
public class AACuvanjeRadaService implements JavaDelegate
{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	@Autowired
	CasopisRepository casopisRepository ;
	
	@Autowired
	RadRepository radRepository ;
	
	@Autowired
	NaucnaOblastCasopisRepository noRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissonDTO> infoRad = (List<FormSubmissonDTO>)execution.getVariable("infoRad");
		List<FormSubmissonDTO> izabranCasopis = (List<FormSubmissonDTO>)execution.getVariable("izabranCasopis");
			
		 Casopis casopis = new Casopis();
		 
		 for(FormSubmissonDTO item: izabranCasopis)
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
							  System.out.println("Naziv izabranog casopisa RAD je: " + casopis.getNaziv());
							  break ;
							  
						  }
					  }
				  }
			 }
			 
			}				 
	      
	      Rad rad = new Rad();
	      rad.setCasopis(casopis); // postavim polje rada casopis na izabran casopis
	      
	      for (FormSubmissonDTO formField : infoRad) 
	      {
			
			if(formField.getFieldId().equals("naslov")) {
				rad.setNaslov(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("kljucniPojmovi")) {
				rad.setKljucniPojmovi(formField.getFieldValue());
			}
			
			if(formField.getFieldId().equals("apstrakt")) {
				
				rad.setApstrakt(formField.getFieldValue());
				
			}
			
			if(formField.getFieldId().equals("pdf")) {
				
				rad.setPdf(formField.getFieldValue());
				
			}
			
			String fieldId = formField.getFieldId();
			if(fieldId.equals("naucnaOblastL")){
				  
				  List<NaucnaOblastCasopis> allOblasti = noRepository.findAll();
				  for(NaucnaOblastCasopis no : allOblasti){
					  for(String selectedEd:formField.getCategories())
					  {
						  String idS = no.getId().toString();
						  if(idS.equals(selectedEd)){
							  rad.setNaucnaOblast(no);
							  
						  }
					  }
				  }
			 }
			
			
			
	      }
	    
	      
	      radRepository.save(rad); 
	      

		
		
	}

}
