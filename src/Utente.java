public class Utente {
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private Ruoli ruolo;
	
	
	// Costruttore:


	public Utente(String nome, String cognome, String email, String password, Ruoli ruolo) {
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

	public Ruoli getRuolo() {
		return ruolo;
	}

	@Override
	public String toString() {
		return nome +"," + cognome +"," +  email +"," + password +"," +ruolo;
	}
}