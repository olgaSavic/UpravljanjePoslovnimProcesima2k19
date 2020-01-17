package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.Casopis;
import root.demo.model.Korisnik;

public interface CasopisRepository extends JpaRepository<Casopis, Long> {
	
	public Casopis findOneByIssn(String issn);

}
