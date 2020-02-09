package root.demo.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.NaucnaOblastCasopis;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.NaucnaOblastCasopisRepository;

@Service
public class AAProveraRecenzentiService implements JavaDelegate
{
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	@Autowired
	CasopisRepository casopisRepository ;
	
	@Autowired
	NaucnaOblastCasopisRepository noRepository ;
	
	@Autowired
	RuntimeService runtimeService ;


	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{		
		String processInstanceId = execution.getProcessInstanceId();
		
		List<Korisnik> recenzentiNO = getRecenzentiNO(processInstanceId);
		
		if (recenzentiNO.size() >= 2) { // ima dovoljno recenzenata naucnih oblasti
			System.out.println("[DOVOLJAN >= 2] Broj recenzenataNO je: " + recenzentiNO.size());
			execution.setVariable("brojRecenzenataVar", recenzentiNO.size());
		}
		else // recenziranje se dodeljuje glavnom uredniku
		{	
			System.out.println("[NEDOVOLJAN < 2] Broj recenzenataNO je: " + recenzentiNO.size());
			execution.setVariable("brojRecenzenataVar", recenzentiNO.size());
		}
		
		
	}
	
	public List<Korisnik> getRecenzentiNO(@PathVariable String processId)	
	{
		List<Casopis> casopisi = casopisRepository.findAll();
		List<Korisnik> sviKorisnici = korisnikRepository.findAll();
		List<NaucnaOblastCasopis> sveNaucneOblasti = noRepository.findAll();
		
		List<NaucnaOblastCasopis> naucneOCasopis = new ArrayList<NaucnaOblastCasopis>();
		NaucnaOblastCasopis radNaucnaOblast = new NaucnaOblastCasopis();
		
		List<Korisnik> recenzentiNO = new ArrayList<Korisnik>();
		List<Korisnik> recenzentiCasopis = new ArrayList<Korisnik>();
		
		
		List<FormSubmissonDTO> izabranCasopisForm = (List<FormSubmissonDTO>)runtimeService.getVariable(processId, "izabranCasopis");
		Casopis casopis = new Casopis();
		 
		 for(FormSubmissonDTO item: izabranCasopisForm)
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
		// sada imam sacuvan casopis koji je korisnik izabrao
		 
		List<FormSubmissonDTO> infoRad = (List<FormSubmissonDTO>)runtimeService.getVariable(processId, "infoRad");
	      for (FormSubmissonDTO formField : infoRad) 
	      {
			
			String fieldId = formField.getFieldId();
			if(fieldId.equals("naucnaOblastL")){
				  
				  List<NaucnaOblastCasopis> allOblasti = noRepository.findAll();
				  for(NaucnaOblastCasopis no : allOblasti){
					  for(String selectedEd:formField.getCategories())
					  {
						  String idS = no.getId().toString();
						  if(idS.equals(selectedEd)){
							  radNaucnaOblast = no ;
							  break ;
							  
						  }
					  }
				  }
			 }
			
			
			
	      }
	      // nasla sam naucnu oblast kojoj rad pripada
		 
		 for (Korisnik k: casopis.getRecenzentiCasopis())
			{
			 recenzentiCasopis.add(k);
			}
		System.out.println("Recenzenta u casopisu ima: " + recenzentiCasopis.size());
		// sada imam sacuvane sve recenzente tog casopisa
		
		 if (recenzentiCasopis.size() == 0) // ne postoji nijedan recenzent u casopisu
		 {
			 System.out.println("Ne postoji nijedan recenzent u casopisu!");
			 //return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
			 return null ;
		 }
		 
		 for (NaucnaOblastCasopis no: casopis.getNaucneOblasti())
		 {
			 naucneOCasopis.add(no);
		 } 
		 // sada imam sacuvane naucne oblasti casopisa
		 
		 if (naucneOCasopis.size() == 0) // ne postoji nijedna naucna oblast u casopisu
		 {
			 System.out.println("Ne postoji nijedna naucna oblast u casopisu!");
			 //return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
			 return null ;
		 }
		 
		 
		 for (Korisnik k: recenzentiCasopis)
		 {
			 for (NaucnaOblastCasopis no: naucneOCasopis)
			 {
				 if (no.getId().equals(radNaucnaOblast.getId()))
				 {
					 for (Korisnik k2: radNaucnaOblast.getRecenzentiNO())
					 {
						 if (k2.getId().equals(k.getId()))
						 {
							 recenzentiNO.add(k);
							 System.out.println("Nasao sam recenzenta naucne oblasti!");
							 System.out.println("Izabrani urednik naucne oblasti je: " + k.getUsername()); 
						 }
					 }
					 
				 }
			 }
		 }
		
		System.out.println("Recenzenata koji rade u casopisu, a koji su bas za datu naucnu oblast ima: " + recenzentiNO.size());
		return recenzentiNO;

		 
		 
	}
	

	
}
