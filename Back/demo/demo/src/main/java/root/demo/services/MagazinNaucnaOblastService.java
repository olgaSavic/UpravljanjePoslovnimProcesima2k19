package root.demo.services;

import java.util.HashSet;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.NaucnaOblastCasopis;
import root.demo.repository.CasopisRepository;
import root.demo.repository.NaucnaOblastCasopisRepository;

@Service
public class MagazinNaucnaOblastService implements JavaDelegate {
	
	@Autowired
	NaucnaOblastCasopisRepository naucnaORepository ;
	
	@Autowired
	CasopisRepository casopisRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
	    List<FormSubmissonDTO> naucnaOblastForm = (List<FormSubmissonDTO>)execution.getVariable("naucnaOblastCasopis");
		
		NaucnaOblastCasopis naucnaO = new NaucnaOblastCasopis() ;
		String nazivNO = "";
		
		for (FormSubmissonDTO formField : naucnaOblastForm) {
			
			if(formField.getFieldId().equals("nazivNO")) {
				naucnaO.setNazivNO(formField.getFieldValue());
				nazivNO = formField.getFieldValue();
			}
		}
		
		// cuva samo u tabeli za naucne oblasti casopisa
		naucnaORepository.save(naucnaO);
		
		List<FormSubmissonDTO> kreiranjeCasopisa = (List<FormSubmissonDTO>)execution.getVariable("kreiranjeCasopisa");
		
		String issn = "";
		
		for (FormSubmissonDTO formField : kreiranjeCasopisa) {
			
			if(formField.getFieldId().equals("issn")) {
				issn = formField.getFieldValue();
			}
		}
		
		Casopis casopis = casopisRepository.findOneByIssn(issn);
		System.out.println("Naziv casopisa je: " + casopis.getNaziv());
		System.out.println("Naziv naucne oblasti koju dodajem je: " + naucnaO.getNazivNO());
				
		// izvuce sve oblasti iz bae
		List<NaucnaOblastCasopis> allAreas = naucnaORepository.findAll();
		
		  for(NaucnaOblastCasopis no : allAreas)
		  {
			  // hocu da dodam bas ovu novu	  
			  if(nazivNO.equals(no.getNazivNO()))
			  {
				  System.out.println("Pronadjena oblast");
				  System.out.println(no.getNazivNO());
				  casopis.getNaucneOblasti().add(no);
			  }
			  
		  }
		  
		casopisRepository.save(casopis);
			
	}
	

}
