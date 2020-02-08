package root.demo.services;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import root.demo.model.Casopis;
import root.demo.model.FormSubmissonDTO;
import root.demo.model.Korisnik;
import root.demo.model.TipKorisnika;
import root.demo.repository.CasopisRepository;
import root.demo.repository.KorisnikRepository;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Service
public class AASlanjeMejlaAGUService implements JavaDelegate
{
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private KorisnikRepository korisnikRepository ;
	
	@Autowired
	private CasopisRepository casopisRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissonDTO> casopisForm = (List<FormSubmissonDTO>)execution.getVariable("izabranCasopis");
	     
	      String processInstanceId = execution.getProcessInstanceId();
	      
	      Korisnik autor = getCurrentUser();
	      Korisnik glavniUrednik = new Korisnik();
	      
	      Casopis casopis = new Casopis();
			 
			 for(FormSubmissonDTO item: casopisForm)
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
	      
			 glavniUrednik = casopis.getGlavniUrednik();
			 System.out.println("Glavni urednik casopisa je [EMAIL]: " + glavniUrednik.getUsername());
			 System.out.println("Autor je [EMAIL]: " + autor.getUsername());
			 
		  
		  /*
	      sendNotificaitionAsync(autor, processInstanceId);
	      sendNotificaitionAsync(glavniUrednik, processInstanceId);
	      */
	      
	}
	
	
	@Async
	public void sendNotificaitionAsync(Korisnik k, String processInstanceId) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		//String htmlMsg = "<h3>Pozdrav "+k.getIme()+"</h3><br> <p>Da biste aktivirali profil posetite  <a href=\"http://localhost:4200/afterEmail/"+ k.getEmail() + "/" + processInstanceId + "\">link</a></p>";
		String htmlMsg = "<h3>Pozdrav "+k.getIme()+",</h3><br> <p>Obavestavamo Vas da je izvrsena prijava novog rada u casopis!</p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(k.getEmail());
		helper.setSubject("Obavestenje o prijavi novog rada u casopis");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email poslat!");
	}
	
	public Korisnik getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println("Principal.toString() EMAIL je: " + principal.toString());
		Korisnik k = korisnikRepository.findOneByUsername(principal.toString());
		if (k != null) 
		{
			String kIme = k.getUsername();
			System.out.println("Username getCurrentUser je: " + kIme);
		}
		else 
		{
			System.out.println("Korisnik je null!");
		}
		
		
		return k;
	}

}
