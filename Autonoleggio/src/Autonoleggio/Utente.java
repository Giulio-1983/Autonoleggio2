package Autonoleggio;

import java.util.ArrayList;
import java.util.Scanner;

public class Utente {
  private String nome;
  private String cognome;
  private String email;
  private String password;
  private String ruolo;


  // Costruttore:
    Utente(String nome, String cognome , String email, String password, String ruolo) {
      this.nome = nome;
      this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = "utente";
      //this.setNome(nome);
      //this.setCognome(cognome);
      //this.setEmail(email);
      //this.setPassword(password);
      //this.setRuolo("utente");
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
      return nome +"," + cognome +"," +  email +"," + password +"," +ruolo;
    }

}
