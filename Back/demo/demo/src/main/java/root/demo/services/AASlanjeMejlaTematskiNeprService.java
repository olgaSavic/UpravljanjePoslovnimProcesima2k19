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
public class AASlanjeMejlaTematskiNeprService implements JavaDelegate{
	
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
		  
	      Korisnik autor = getCurrentUser();
	      String processInstanceId = execution.getProcessInstanceId();
	     
	      //sendNotificaitionAsync(autor, processInstanceId);
	      
	      
	}
	
	
	@Async
	public void sendNotificaitionAsync(Korisnik k, String processInstanceId) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		//String htmlMsg = "<h3>Pozdrav "+k.getIme()+"</h3><br> <p>Da biste aktivirali profil posetite  <a href=\"http://localhost:4200/afterEmail/"+ k.getEmail() + "/" + processInstanceId + "\">link</a></p>";
		String htmlMsg = "<h3>Pozdrav "+k.getIme()+",</h3><br> <p>Obavestavamo Vas tekst rada nije tematski prikladan i proces se terminira!</p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(k.getEmail());
		helper.setSubject("Obavestenje o terminaciji procesa uskled tematske neprikladnosti");
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
