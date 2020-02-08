package root.demo.services;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

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

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Service
public class AASlanjeMejlaAutorIspService implements JavaDelegate{
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private KorisnikRepository korisnikRepository ;
	
	@Autowired
	private CasopisRepository casopisRepository ;
	
	@Autowired 
	RuntimeService runtimeService ;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		  
		  String autorUsername = (String) execution.getVariable("autorVar");
	      System.out.println("Slanje mejla autor username je: " + autorUsername);
	      Korisnik autor = korisnikRepository.findOneByUsername(autorUsername);
	      
	      String processInstanceId = execution.getProcessInstanceId();
	      String fileName = (String) execution.getVariable("pdfFileName");
	      byte[] decodedBytes = (byte[]) execution.getVariable("pdfRad");
	     
	      //sendNotificaitionAsync(autor, processInstanceId, fileName, decodedBytes);      
	}
	
	
	@Async
	public void sendNotificaitionAsync(Korisnik k, String processInstanceId, String fileName, byte[] decodedBytes) throws MailException, InterruptedException, MessagingException {

		System.out.println("Slanje emaila...");
		String content = "Neophodno je da ispravite pdf koji ste prilozili prilikom unosa informacija u radu!";
		
	    ByteArrayOutputStream outputStream = null;

	    try {           
	        //construct the text body part
	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(content);

	        //now write the PDF content to the output stream
	        writePdf(fileName, decodedBytes);

	        //construct the pdf body part
	        DataSource dataSource = new ByteArrayDataSource(decodedBytes, "application/pdf");
	        MimeBodyPart pdfBodyPart = new MimeBodyPart();
	        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
	        
	        pdfBodyPart.setFileName(fileName);

	        //construct the mime multi part
	        MimeMultipart mimeMultipart = new MimeMultipart();
	        mimeMultipart.addBodyPart(textBodyPart);
	        mimeMultipart.addBodyPart(pdfBodyPart);

	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			String htmlMsg = "<h3>Pozdrav "+k.getIme()+",</h3><br> <p>Obavestavamo Vas da je neophodno da rad koji ste prilozili ispravite u najkracem mogucem roku!</p>";
			mimeMessage.setContent(mimeMultipart, "text/html");
			helper.setTo(k.getEmail());
			helper.setSubject("Obavestenje o vracanju poslatog rada na doradu");
			helper.setFrom(env.getProperty("spring.mail.username"));
			javaMailSender.send(mimeMessage);
		
			System.out.println("Email poslat!");
	                 
	    
	} catch(Exception ex) {
        ex.printStackTrace();
    } finally {
        //clean off
        if(null != outputStream) {
            try { outputStream.close(); outputStream = null; }
            catch(Exception ex) { }
        }
    }
				
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
	
	
	public void writePdf(String fileName, byte[] decodedBytes) throws Exception {
		File file = new File("pdf/" + fileName);
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(decodedBytes);
		fop.flush();
		fop.close();
	}
	
	


}
