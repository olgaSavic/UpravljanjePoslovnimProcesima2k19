package root.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NaucnaOblast {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String nazivNO ;
	
	public NaucnaOblast() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NaucnaOblast(Long id, String nazivNO) {
		super();
		this.id = id;
		this.nazivNO = nazivNO;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNazivNO() {
		return nazivNO;
	}
	public void setNazivNO(String nazivNO) {
		this.nazivNO = nazivNO;
	}
	
	

}
