package autonoleggio;

import java.util.Scanner;
import java.util.ArrayList; 
import java.util.HashMap;

public class MainAutonoleggio {

	public static void main(String[] args) {
		
//		Scanner scanner = new Scanner(System.in);
//	    HashMap<String, Utente> listaUtenti = new HashMap<String, Utente>();
//	    HashMap<String, AutoNoleggiabile> parcoAuto = new HashMap<String, AutoNoleggiabile>();
//	    HashMap<String, Batmobile> listaBatmobili = new HashMap<String, Batmobile>();
	    GestioneAutonoleggio gestioneAutonoleggio = new GestioneAutonoleggio();

//	    boolean isEsci = false;
//	    boolean isBatman = false;
//	    boolean isManager = false;
	    String email = null;
	    String passwordInserita = null;

	    gestioneAutonoleggio.caricaFileUtenti();
	    gestioneAutonoleggio.caricaFileAuto();
	    gestioneAutonoleggio.login();
	    gestioneAutonoleggio.salvaFileUtenti();
	    gestioneAutonoleggio.aggiungiAuto();
	    gestioneAutonoleggio.mostraAuto();
	    gestioneAutonoleggio.salvaFileAuto();
	    

	    // login:
	    //gestioneAutonoleggio.login();

	    

	    

	    
	  } // fine main
	} // fine classe Main
