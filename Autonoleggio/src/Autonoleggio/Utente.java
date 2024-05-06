package autonoleggio;

import java.util.ArrayList;
import java.util.Scanner;

public class Utente {
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String ruolo;

	// Costruttore:
	Utente(String nome, String cognome, String email, String password, String ruolo) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	@Override
	public String toString() {
		return getNome() + "," + getCognome() + "," + getEmail() + "," + getPassword() + "," + getRuolo();
	}
	
	/*
	 * public void registrati() { dammiEmail("Inserisci la tua email",
	 * "indirizzo email non riconosciuto. Inserisci la tua email",
	 * "Hai esaurito il numero di tentativi. Riprova più tardi", 3); Scanner scanner
	 * = new Scanner(System.in); email=scanner.nextLine();
	 * System.out.println("Inserisci una password");
	 * password=scanner.nextLine().trim();
	 * System.out.println("Inserisci il tuo nome"); // volendo, utilizzare
	 * dammiStringa(); nome=scanner.nextLine();
	 * System.out.println("Inserisci il tuo cognome"); cognome=scanner.nextLine();
	 * ruolo="utente"; salvaFileUtenti(); }
	 */


}
