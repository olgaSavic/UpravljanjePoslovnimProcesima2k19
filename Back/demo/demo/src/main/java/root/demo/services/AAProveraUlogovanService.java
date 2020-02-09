package root.demo.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import root.demo.model.Korisnik;
import root.demo.repository.KorisnikRepository;

@Service
public class AAProveraUlogovanService implements JavaDelegate {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	KorisnikRepository korisnikRepository ;


	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		// TODO Auto-generated method stub
		
		Korisnik k = new Korisnik();
		k = getCurrentUser();
		
		if (k == null) {
			System.out.println("Niko nije ulogovan!");
			execution.setVariable("rezUlogovan", false);
		}
		else
		{
			System.out.println("Ulogovan je [provera ulogovan -> postavljanje varijable]: " + k.getUsername());
			execution.setVariable("rezUlogovan", true);
			execution.setVariable("autor", k.getUsername());
		}
		
	}
	
	// SPRING SECURITY - VRATI TRENUTNOG KORISNIKA
	public Korisnik getCurrentUser() {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				System.out.println("Principal.toString() je: " + principal.toString());
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
