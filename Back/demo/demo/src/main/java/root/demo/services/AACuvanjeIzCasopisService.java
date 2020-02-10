package root.demo.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissionDto;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.TipKorisnika;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;

@Service
public class AACuvanjeIzCasopisService implements JavaDelegate{

	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;

	@Autowired
	CasopisRepository casopisRepository ;
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				 List<FormSubmissonDTO> izabranCasopis = (List<FormSubmissonDTO>)execution.getVariable("izabranCasopis");
				
				 Casopis casopis = new Casopis();
				 
				 // postavim procesnu varijablu na osnovu casopisa, da li je open-access ili nije, zbog if-a
				 
				 
				 // postavljam da je izabran casopis taj koji je korisnik kliknuo 
				 // u jednom momentu sme biti samo jedan izabran casopis
				 
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
									  System.out.println("Naziv izabranog casopisa je: " + casopis.getNaziv());
									  break ;
									  
								  }
							  }
						  }
					 }
					 
					}				 
				 casopis.setIzabranCasopis(true);
				 execution.setVariable("openAccessVar", casopis.isOpenAccess());
				 casopisRepository.save(casopis);
		
	}

}
