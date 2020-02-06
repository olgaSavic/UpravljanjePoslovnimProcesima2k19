package root.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import root.demo.model.Korisnik;
import root.demo.repository.KorisnikRepository;


@Service   // It has to be annotated with @Service.
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	KorisnikRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Korisnik user = repository.findOneByUsername(username);
		
		System.out.println("Korisnicko ime je: " + user.getUsername());
		System.out.println("Lozinka je: " + user.getPassword());
		
		if(user != null) {
			System.out.println("User nije null!");
				// Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
				// So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
		                	.commaSeparatedStringToAuthorityList("ROLE_" + user.getTip());
				
				// The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
				// And used by auth manager to verify and check user authentication.
				return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
		}
		System.out.println("User nije pronadjen!");
		// If user not found. Throw this exception.
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}
}
