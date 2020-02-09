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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Casopis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "issn", nullable = false)
	private String issn;
	
	// ovo je za nacin placanja
	@Column(name = "open_access", nullable = false)
	private boolean openAccess;

	// status casopisa - AKTIVAN/NEAKTIVAN
	@Column(name = "aktivan")
	private boolean aktivan;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "Casopis_NO", 
	joinColumns = { @JoinColumn(name = "casopis_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "naucnaOblastCasopis_id") }
	  )
	private Set<NaucnaOblastCasopis> naucneOblasti = new HashSet<NaucnaOblastCasopis>();
	
	// lista recenzenata za dati casopis
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Casopis_Recenzent", 
	        joinColumns = { @JoinColumn(name = "casopis_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "korisnik_id") }
	  )
	private Set<Korisnik> recenzentiCasopis = new HashSet<Korisnik>();
	
	// lista urednika naucnih oblasti
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Casopis_Urednik", 
	        joinColumns = { @JoinColumn(name = "casopis_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "korisnik_id") }
	  )
	private Set<Korisnik> uredniciCasopis = new HashSet<Korisnik>();
	
	// glavni urednik casopisa
	@ManyToOne(fetch = FetchType.EAGER)
	private Korisnik glavniUrednik;
	
	@Column(name = "izabran")
	private boolean izabranCasopis ;
	
	

	
	
	public Casopis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Casopis(Long id, String naziv, String issn, boolean openAccess, boolean aktivan,
			Set<NaucnaOblastCasopis> naucneOblasti, Set<Korisnik> recenzentiCasopis, Set<Korisnik> uredniciCasopis,
			Korisnik glavniUrednik) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.issn = issn;
		this.openAccess = openAccess;
		this.aktivan = aktivan;
		this.naucneOblasti = naucneOblasti;
		this.recenzentiCasopis = recenzentiCasopis;
		this.uredniciCasopis = uredniciCasopis;
	}

	public boolean isIzabranCasopis() {
		return izabranCasopis;
	}

	public void setIzabranCasopis(boolean izabranCasopis) {
		this.izabranCasopis = izabranCasopis;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public Set<NaucnaOblastCasopis> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblastCasopis> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Set<Korisnik> getRecenzentiCasopis() {
		return recenzentiCasopis;
	}

	public void setRecenzentiCasopis(Set<Korisnik> recenzentiCasopis) {
		this.recenzentiCasopis = recenzentiCasopis;
	}

	public Set<Korisnik> getUredniciCasopis() {
		return uredniciCasopis;
	}

	public void setUredniciCasopis(Set<Korisnik> uredniciCasopis) {
		this.uredniciCasopis = uredniciCasopis;
	}

	public Korisnik getGlavniUrednik() {
		return glavniUrednik;
	}

	public void setGlavniUrednik(Korisnik glavniUrednik) {
		this.glavniUrednik = glavniUrednik;
	}
	
	



}
