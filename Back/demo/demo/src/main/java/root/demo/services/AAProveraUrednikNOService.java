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
public class AAProveraUrednikNOService implements JavaDelegate{
	
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
		
		String glavniUrednikUsername = (String) execution.getVariable("glavniUrednikVar");
		System.out.println("Username glavnog urednika provera je: " + glavniUrednikUsername);
		Korisnik glavniUrednik = korisnikRepository.findOneByUsername(glavniUrednikUsername);
		
		Korisnik urednikNO = getUrednikNO(processInstanceId);
		
		if (urednikNO == null) // nije pronasao urednika naucne oblasti
		{
			System.out.println("Dodeljeni urednik je glavni urednik: " + glavniUrednikUsername);
			execution.setVariable("dodeljeniUrednik", glavniUrednik.getUsername());
			
			 
		}
		else
		{
			System.out.println("Dodeljeni urednik je urednikNaucneOblasti: " + urednikNO.getUsername());
			execution.setVariable("dodeljeniUrednik", urednikNO.getUsername());
			
		}
		
	}
	
	public Korisnik getUrednikNO(@PathVariable String processId)	
	{
		List<Casopis> casopisi = casopisRepository.findAll();
		List<Korisnik> sviKorisnici = korisnikRepository.findAll();
		List<NaucnaOblastCasopis> sveNaucneOblasti = noRepository.findAll();
		
		List<Korisnik> uredniciNO = new ArrayList<Korisnik>();
		List<NaucnaOblastCasopis> naucneOCasopis = new ArrayList<NaucnaOblastCasopis>();
		NaucnaOblastCasopis radNaucnaOblast = new NaucnaOblastCasopis();
		
		Korisnik urednikNO = new Korisnik();
		
		
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
		 
		 for (Korisnik k: casopis.getUredniciCasopis())
			{
				uredniciNO.add(k);
			}
		System.out.println("Urednika naucnih oblasti u casopisu ima: " + uredniciNO.size());
		// sada imam sacuvane sve urednike naucnih oblasti tog casopisa
		
		 if (uredniciNO.size() == 0) // ne postoji nijedan urednik naucne oblasti u casopisu
		 {
			 System.out.println("Ne postoji nijedan urednik naucne oblasti u casopisu!");
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
		 
		 
		 for (Korisnik k: uredniciNO)
		 {
			 for (NaucnaOblastCasopis no: naucneOCasopis)
			 {
				 if (no.getId().equals(radNaucnaOblast.getId()))
				 {
					 for (Korisnik k2: radNaucnaOblast.getUredniciNO())
					 {
						 if (k2.getId().equals(k.getId()))
						 {
							 urednikNO = k;
							 System.out.println("Nasao sam urednika naucne oblasti!");
							 System.out.println("Izabrani urednik naucne oblasti je: " + urednikNO.getUsername()); 
						 }
					 }
					 
				 }
			 }
		 }
		
		
		return urednikNO;

		 
		 
	}

}
