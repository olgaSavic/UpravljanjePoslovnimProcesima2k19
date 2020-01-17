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
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.TipKorisnika;
import root.demo.repository.KorisnikRepository;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Service
public class SlanjeMejlaService implements JavaDelegate{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private KorisnikRepository korisnikRepository ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      System.out.println(registration);
	      
	      String processInstanceId = execution.getProcessInstanceId();
	      
	      Korisnik korisnik = new Korisnik() ;
	      
	      for (FormSubmissionDto formField : registration) {
				if(formField.getFieldId().equals("username")) {
					korisnik.setUsername(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("password")) {
					korisnik.setPassword(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("email")) {
					korisnik.setEmail(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("ime")) {
					korisnik.setIme(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("prezime")) {
					korisnik.setPrezime(formField.getFieldValue());
				}
				
				if(formField.getFieldId().equals("drzava")) {
					korisnik.setDrzava(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("grad")) {
					korisnik.setGrad(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("titula")) {
					korisnik.setTitula(formField.getFieldValue());
				}
				if(formField.getFieldId().equals("recenzent")) {
					
					if (formField.getFieldValue() == "true")
					{
						korisnik.setRecenzent(true);
					}
					else
					{
						korisnik.setRecenzent(false);
					}
					
				}
				korisnik.setOdobrenRecenzent(false);
				korisnik.setAktivan(false);
				korisnik.setTip("OBICAN");
				
				
		 } // kraj FOR-a
	      
	      sendNotificaitionAsync(korisnik, processInstanceId);
	      
	}
	
	
	@Async
	public void sendNotificaitionAsync(Korisnik k, String processInstanceId) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

		String htmlMsg = "<h3>Pozdrav "+k.getIme()+"</h3><br> <p>Da biste aktivirali profil posetite  <a href=\"http://localhost:4200/afterEmail/"+ k.getEmail() + "/" + processInstanceId + "\">link</a></p>";
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(k.getEmail());
		helper.setSubject("Verifikacija clanstva");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	
		System.out.println("Email poslat!");
	}
	
	public String aktivirajNalog(String email) 
	{
		String noviEmail = email + ".com";
		Korisnik k = korisnikRepository.findOneByEmail(noviEmail);
		
		k.setAktivan(true);	
    	korisnikRepository.save(k);
		
		return "Uspesno ste aktivirali nalog, mozete posetiti sajt.";
	}
	

}
