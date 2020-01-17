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
public class UpdateCasopisaService implements JavaDelegate
{
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;

	@Autowired
	CasopisRepository casopisRepository ;
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		 List<FormSubmissonDTO> kreiranjeCasopisa = (List<FormSubmissonDTO>)execution.getVariable("kreiranjeCasopisa");
		 List<FormSubmissonDTO> updateInfo = (List<FormSubmissonDTO>)execution.getVariable("updateData");
		 String issn="";
		 
		 for(FormSubmissonDTO item: kreiranjeCasopisa){
			 if(item.getFieldId().equals("issn")){
				 issn = item.getFieldValue();
				 break;
			 }
		 }
		 
		 // pronalazi casopis na osnovu issn broja
		 Casopis casopis = new Casopis();
		 casopis = casopisRepository.findOneByIssn(issn);
		 
		  for(FormSubmissonDTO item: updateInfo)
		  {
			  String fieldId=item.getFieldId();
			  
			 if(fieldId.equals("uredniciL")){
				  
				  List<Korisnik> allUsers = korisnikRepository.findAll();
				  for(Korisnik u : allUsers){
					  for(String selectedEd:item.getCategories())
					  {
						  String idS=u.getId().toString();
						  if(idS.equals(selectedEd)){
							  System.out.println(u.getIme());
							  casopis.getUredniciCasopis().add(u);
							  
						  }
					  }
				  }
			 }
			 if(fieldId.equals("recenzentiL")){
				  List<Korisnik> allUsers = korisnikRepository.findAll();
				  for(Korisnik u : allUsers){
					  for(String selectedEd:item.getCategories()){
	
						  String idS=u.getId().toString();
						  if(idS.equals(selectedEd)){
							  casopis.getRecenzentiCasopis().add(u);
							  
						  }
					  }
				  }
			 }
			}
		 casopisRepository.save(casopis);
	}

}
