package root.demo.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Korisnik;
import root.demo.model.TipKorisnika;
import root.demo.repository.KorisnikRepository;

// Kada Camundin Engine dodje do servisnog taska
@Service
public class TestService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 String var = "Pera";
	      var = var.toUpperCase();
	      execution.setVariable("input", var);
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      System.out.println(registration);
	      
	      User user = identityService.newUser("");
	      Korisnik korisnik = new Korisnik();
	      
	      for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				user.setId(formField.getFieldValue());
				korisnik.setUsername(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("password")) {
				
				String dobijenaSifra = formField.getFieldValue();
				String hesovanaLozinka = encoder.encode(dobijenaSifra);
				user.setPassword(hesovanaLozinka);
				korisnik.setPassword(hesovanaLozinka);
			}
			if(formField.getFieldId().equals("email")) {
				user.setEmail(formField.getFieldValue());
				korisnik.setEmail(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("ime")) {
				user.setFirstName(formField.getFieldValue());
				korisnik.setIme(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("prezime")) {
				user.setLastName(formField.getFieldValue());
				korisnik.setPrezime(formField.getFieldValue());
			}
			
			// dodatno za @Entity korisnika u bazi
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
				
				System.out.println("Vrednost recenzent je: " + formField.getFieldValue().toString());
				if (formField.getFieldValue().toString().equals("true"))
				{
					korisnik.setRecenzent(true);
				}
				else
				{
					korisnik.setRecenzent(false);
				}
				
			}
			
			korisnik.setAktivan(false);
			korisnik.setTip("AUTOR");
			
			
	      }
	      
	      korisnikRepository.save(korisnik); // Korisnik u bazi
	      execution.setVariable("aktivanKorisnik", "neaktivan");
		  execution.setVariable("odobrenRecenzentKod", "nijeOdobrenRecenzent");

	      identityService.saveUser(user); // Camundin user
	      
	      
	}
	
	public String encrypt(String sifra) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		  
		 //pretvori sifru  u bajte
         byte[] messageDigest = md.digest(sifra.getBytes()); 
         
         StringBuilder sb = new StringBuilder();
         for (byte b : messageDigest) {
             sb.append(String.format("%02x", b));
         }
        String povratna=sb.toString();
        System.out.println("Rezultat enkripcije je "+povratna);
    	
        return povratna;
	}
}
