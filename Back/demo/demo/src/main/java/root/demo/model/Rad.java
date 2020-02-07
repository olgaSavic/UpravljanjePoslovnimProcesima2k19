package root.demo.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Rad 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
	
	@Column(name = "naslov", nullable = false)
	private String naslov;
	
	@Column(name = "kljucniPojmovi", nullable = false)
	private String kljucniPojmovi ;
	
	@Column(name = "apstrakt", nullable = false)
	private String apstrakt ;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private NaucnaOblastCasopis naucnaOblast ;
	
	private String pdf ;
	
	// lista recenzenata
		@ManyToMany(cascade = {CascadeType.ALL})
		@JoinTable(
		        name = "Rad_Koautor", 
		        joinColumns = { @JoinColumn(name = "rad_id") }, 
		        inverseJoinColumns = { @JoinColumn(name = "koautor_id") }
		  )
		private Set<Koautor> koautoriRad = new HashSet<Koautor>();

		// casopis u kom se rad nalazi
		@ManyToOne(fetch = FetchType.EAGER)
		private Casopis casopis;		

	public Rad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rad(Long id, String naslov, String kljucniPojmovi, String apstrakt, NaucnaOblastCasopis naucnaOblast, String pdf) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.kljucniPojmovi = kljucniPojmovi;
		this.apstrakt = apstrakt;
		this.naucnaOblast = naucnaOblast;
		this.pdf = pdf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getKljucniPojmovi() {
		return kljucniPojmovi;
	}

	public void setKljucniPojmovi(String kljucniPojmovi) {
		this.kljucniPojmovi = kljucniPojmovi;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}

	

	public NaucnaOblastCasopis getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblastCasopis naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public Set<Koautor> getKoautoriRad() {
		return koautoriRad;
	}

	public void setKoautoriRad(Set<Koautor> koautoriRad) {
		this.koautoriRad = koautoriRad;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}
	
	
	
	
	
	
	
	
	
}
