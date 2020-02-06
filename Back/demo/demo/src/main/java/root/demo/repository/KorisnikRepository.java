package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> 
{
	public Korisnik save(Korisnik k);
	public Korisnik findOneByEmail(String email);
	public Korisnik findOneByUsername(String username);
	public Korisnik findOneById(Long id);
	
}
