package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissonDTO;
import root.demo.model.Koautor;
import root.demo.model.NaucnaOblast;
import root.demo.model.Rad;
import root.demo.repository.KoautorRepository;
import root.demo.repository.NaucnaOblastRepository;
import root.demo.repository.RadRepository;

@Service
public class AACuvanjeKoautoraService implements JavaDelegate{
	
	@Autowired
	KoautorRepository koautorRepository ;
	
	@Autowired
	RadRepository radRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		List<FormSubmissonDTO> infoRad = (List<FormSubmissonDTO>)execution.getVariable("infoRad");
		List<FormSubmissonDTO> koautorForm = (List<FormSubmissonDTO>)execution.getVariable("koautor");
		
		String naslov="";
		 
		 for(FormSubmissonDTO item: infoRad)
		 {
			 if(item.getFieldId().equals("naslov"))
			 {
				 naslov = item.getFieldValue();
				 break;
			 }
		 }
		 
		System.out.println("Naslov rada je: " + naslov);
		
		Rad rad = new Rad();
		rad = radRepository.findOneByNaslov(naslov);
		
		
		Koautor koautor = new Koautor();
		
		for (FormSubmissonDTO formField : koautorForm) {
			if(formField.getFieldId().equals("imeK")) {
				koautor.setIme(formField.getFieldValue());
				System.out.println("Ime koautora je: " + koautor.getIme());
			}
			if(formField.getFieldId().equals("email")) {
				koautor.setEmail(formField.getFieldValue());
				System.out.println("Email koautora je: " + koautor.getEmail());
			}
			if(formField.getFieldId().equals("grad")) {
				koautor.setGrad(formField.getFieldValue());
				System.out.println("Grad koautora je: " + koautor.getGrad());
			}
			if(formField.getFieldId().equals("drzava")) {
				koautor.setDrzava(formField.getFieldValue());
				System.out.println("Drzava koautora je: " + koautor.getDrzava());
			}
		}
		koautorRepository.save(koautor);
		rad.getKoautoriRad().add(koautor);
		
		radRepository.save(rad);
		
	
		
	}

}
