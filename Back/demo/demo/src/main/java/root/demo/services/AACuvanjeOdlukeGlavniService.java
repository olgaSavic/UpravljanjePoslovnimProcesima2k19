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
import root.demo.model.Komentar;
import root.demo.model.Korisnik;
import root.demo.model.NaucnaOblast;
import root.demo.model.NaucnaOblastCasopis;
import root.demo.model.Rad;
import root.demo.model.TipKorisnika;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KomentarRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastCasopisRepository;
import root.demo.repository.RadRepository;

// Kada Camundin Engine dodje do servisnog taska
@Service
public class AACuvanjeOdlukeGlavniService implements JavaDelegate{
	

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
	
	@Autowired
	KomentarRepository komentarRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String processInstanceId = execution.getProcessInstanceId();
		
		/**
		 * 		List<ExtendedFormSubmissionDto> chiefEditorReviewData = (List<ExtendedFormSubmissionDto>) runtimeService.getVariable(processInstanceId, "chiefEditorReview");
		for(ExtendedFormSubmissionDto item: chiefEditorReviewData) {
			 String fieldId=item.getFieldId();
			 if(fieldId.equals("odluka_GU")){
				  List<Decision> allDecisions = decisionRepository.findAll();
				  for(Decision decision : allDecisions){
					  for(String selected: item.getCategories()){
						  String idDecision = decision.getId().toString();
						  if(idDecision.equals(selected)){
							  System.out.println("Odluka je: " + decision.getName());  
							  runtimeService.setVariable(processInstanceId, "odluka", decision.getName());
						  }
					  }
				  }
			 }
		}
		 */
		
		
	}


}
