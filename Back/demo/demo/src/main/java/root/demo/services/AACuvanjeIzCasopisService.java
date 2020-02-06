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
import root.demo.model.IzabranCasopis;
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
				 String issn="";
				 
				 for(FormSubmissonDTO item: izabranCasopis){
					 if(item.getFieldId().equals("issn")){
						 issn = item.getFieldValue();
						 break;
					 }
				 }
				 
				 // pronalazi casopis na osnovu issn broja
				 Casopis casopis = new Casopis();
				 casopis = casopisRepository.findOneByIssn(issn);
				 
				 System.out.println("Stanje OPEN-ACCESS je: " + casopis.isOpenAccess());
				 
				 // postavim procesnu varijablu na osnovu casopisa, da li je open-access ili nije, zbog if-a
				 execution.setVariable("openAccessVar", casopis.isOpenAccess());
				 
				 // postavljam da je izabran casopis taj koji je korisnik kliknuo 
				 // u jednom momentu sme biti samo jedan izabran casopis
				 casopis.setIzabranCasopis(true);
				 casopisRepository.save(casopis);
		
	}

}
