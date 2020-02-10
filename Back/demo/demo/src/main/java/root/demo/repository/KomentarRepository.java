package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.Komentar;

public interface KomentarRepository extends JpaRepository<Komentar, Long>{
	
	Komentar findOneById(Long id);
	Komentar findOneByVrednost(String vrednost);

}
