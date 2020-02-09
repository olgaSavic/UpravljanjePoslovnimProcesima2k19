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
public class AASlanjeMejlaDodeljeniService implements JavaDelegate 
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
		  
		String dodeljeniUrednikUsername = (String) execution.getVariable("dodeljeniUrednik");
	    System.out.println("Slanje mejla dodeljeni urednik je: " + dodeljeniUrednikUsername);
	    Korisnik dodeljeniUrednik = korisnikRepository.findOneByUsername(dodeljeniUrednikUsername);
	      
	    String processInstanceId = execution.getProcessInstanceId();
	     
	    //sendNotificaitionAsync(dodeljeniUrednik, processInstanceId);	      
	      
	}
	
	
	@Async
	public void sendNotificaitionAsync(Korisnik k, String processInstanceId) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		//String htmlMsg = "<h3>Pozdrav "+k.getIme()+"</h3><br> <p>Da biste aktivirali profil posetite  <a href=\"http://localhost:4200/afterEmail/"+ k.getEmail() + "/" + processInstanceId + "\">link</a></p>";
		String htmlMsg = "<h3>Pozdrav "+k.getIme()+",</h3><br> <p>Obavestavamo Vas da je pristigao novi rad!</p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(k.getEmail());
		helper.setSubject("Obavestenje o pristizanju novog rada");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email poslat!");
	}


}
