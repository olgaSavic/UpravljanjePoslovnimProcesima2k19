package root.demo.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
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
		
		//boolean rezultatValidacije = proslaValidacija(registration);
	    execution.setVariable("rezUlogovan", false);
		
	}
	
	public boolean trenutniKorisnik(@Context HttpServletRequest request)
	{
		Korisnik k = (Korisnik) request.getSession().getAttribute("logged");
		
		if(k != null) { // neko je ulogovan
			return true ;
		} else {
			return false ; // niko nije ulogovan
		}
	}

}
