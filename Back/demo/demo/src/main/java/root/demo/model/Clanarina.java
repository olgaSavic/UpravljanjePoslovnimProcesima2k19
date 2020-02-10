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
public class Clanarina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
	
	@ManyToOne
	private Korisnik korisnik ;
	
	@ManyToOne
	private Casopis casopis ;
	
	private boolean aktivna ;

	public Clanarina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Clanarina(Long id, Korisnik korisnik, Casopis casopis, boolean aktivna) {
		super();
		this.id = id;
		this.korisnik = korisnik;
		this.casopis = casopis;
		this.aktivna = aktivna;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public boolean isAktivna() {
		return aktivna;
	}

	public void setAktivna(boolean aktivna) {
		this.aktivna = aktivna;
	}
	
	

}
