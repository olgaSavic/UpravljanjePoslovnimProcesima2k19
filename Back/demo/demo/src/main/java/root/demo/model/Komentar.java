package root.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Komentar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	private String vrednost ;

	public Komentar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Komentar(Long id, String vrednost) {
		super();
		this.id = id;
		this.vrednost = vrednost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVrednost() {
		return vrednost;
	}

	public void setVrednost(String vrednost) {
		this.vrednost = vrednost;
	}
	
	

}
