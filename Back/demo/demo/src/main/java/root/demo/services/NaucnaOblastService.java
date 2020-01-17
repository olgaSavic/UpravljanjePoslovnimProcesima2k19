package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.NaucnaOblast;
import root.demo.repository.NaucnaOblastRepository;

@Service
public class NaucnaOblastService implements JavaDelegate {
	
	@Autowired
	NaucnaOblastRepository naucnaORepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
	    List<FormSubmissionDto> naucnaOblastForm = (List<FormSubmissionDto>)execution.getVariable("naucnaOblast");
		
		NaucnaOblast naucnaO = new NaucnaOblast() ;
		
		for (FormSubmissionDto formField : naucnaOblastForm) {
			
			if(formField.getFieldId().equals("nazivNO")) {
				naucnaO.setNazivNO(formField.getFieldValue());
			}
		}
		
		naucnaORepository.save(naucnaO);
		
		
		
	}
	
	

}
