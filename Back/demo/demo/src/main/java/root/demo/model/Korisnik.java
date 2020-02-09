package root.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Korisnik {
	
	// private Long id ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;

	private String username ;
	private String password ;
	private String ime ;
	private String prezime ;
	private String drzava ;
	private String grad ;
	private String titula ;
	private String email ;
	private boolean recenzent ;
	private boolean aktivan ;
	private String tip ;
	private boolean odobrenRecenzent ;
	
	// za casopis
	@ManyToMany
	private Set<Casopis> casopisiRec = new HashSet<Casopis>();
	
	@ManyToMany
	private Set<Casopis> casopisiUred = new HashSet<Casopis>();
	
	@ManyToMany
	private Set<NaucnaOblastCasopis> noRec = new HashSet<NaucnaOblastCasopis>();
	
	@ManyToMany
	private Set<NaucnaOblastCasopis> noUred = new HashSet<NaucnaOblastCasopis>();
	
	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Korisnik(String username, String password, String ime, String prezime, String drzava, String grad,
			String titula, String email, boolean recenzent) {
		super();
		this.username = username;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
		this.drzava = drzava;
		this.grad = grad;
		this.titula = titula;
		this.email = email;
		this.recenzent = recenzent;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isRecenzent() {
		return recenzent;
	}

	public void setRecenzent(boolean recenzent) {
		this.recenzent = recenzent;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public boolean isOdobrenRecenzent() {
		return odobrenRecenzent;
	}

	public void setOdobrenRecenzent(boolean odobrenRecenzent) {
		this.odobrenRecenzent = odobrenRecenzent;
	}

	public Set<Casopis> getCasopisiRec() {
		return casopisiRec;
	}

	public void setCasopisiRec(Set<Casopis> casopisiRec) {
		this.casopisiRec = casopisiRec;
	}

	public Set<Casopis> getCasopisiUred() {
		return casopisiUred;
	}

	public void setCasopisiUred(Set<Casopis> casopisiUred) {
		this.casopisiUred = casopisiUred;
	}

	public Set<NaucnaOblastCasopis> getNoRec() {
		return noRec;
	}

	public void setNoRec(Set<NaucnaOblastCasopis> noRec) {
		this.noRec = noRec;
	}

	public Set<NaucnaOblastCasopis> getNoUred() {
		return noUred;
	}

	public void setNoUred(Set<NaucnaOblastCasopis> noUred) {
		this.noUred = noUred;
	}
	
	

}
