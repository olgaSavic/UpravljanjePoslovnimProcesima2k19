package root.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class NaucnaOblastCasopis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String nazivNO ;
	
	//@ManyToMany(mappedBy = "CasopisNO")
	@ManyToMany
	private Set<Casopis> casopisi = new HashSet<Casopis>();
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "NOCas_Urednik", 
	        joinColumns = { @JoinColumn(name = "no_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "korisnik_id") }
	  )
	private Set<Korisnik> uredniciNO = new HashSet<Korisnik>();
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "NOCas_Recenzent", 
	        joinColumns = { @JoinColumn(name = "no_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "korisnik_id") }
	  )
	private Set<Korisnik> recenzentiNO = new HashSet<Korisnik>();
	
	
	public NaucnaOblastCasopis() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NaucnaOblastCasopis(Long id, String nazivNO) {
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
	public Set<Casopis> getCasopisi() {
		return casopisi;
	}
	public void setCasopisi(Set<Casopis> casopisi) {
		this.casopisi = casopisi;
	}
	public Set<Korisnik> getUredniciNO() {
		return uredniciNO;
	}
	public void setUredniciNO(Set<Korisnik> uredniciNO) {
		this.uredniciNO = uredniciNO;
	}
	public Set<Korisnik> getRecenzentiNO() {
		return recenzentiNO;
	}
	public void setRecenzentiNO(Set<Korisnik> recenzentiNO) {
		this.recenzentiNO = recenzentiNO;
	}
	
	
	
	
	
	

}
