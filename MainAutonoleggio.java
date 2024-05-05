package autonoleggio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class MainAutonoleggio {

	public static void main(String[] args) {

		
		ConsoleManage cm = new ConsoleManage();
	    GestioneAutonoleggio ga = new GestioneAutonoleggio();
	    
	 
	    ga.caricaFileUtenti(); 
	    ga.caricaFileAuto();
	    ga.caricaFileBatmobili();


	    String[] cMainMenu = new String[2];
	    cMainMenu = cm.giveMenuOption("Ciao e benvenuto/a! \nA)Login \nB)Registrati ", "\nC)Esci", "Non è stata inserita una risposta valida", 3, "ABC");

	    if (cMainMenu[0].equals("1")) {
	        String menu = cMainMenu[1];
	        
	        if (menu.equalsIgnoreCase("B")) {
	            
	            ga.registraUtente();
	            
	            System.out.println("Registrazione effettuata con successo.");
	            ga.login();
	           
	        }
	    
	    else if (menu.equalsIgnoreCase("A")) {
	    	HashMap<String,String> credenziali = ga.login();
	    	String ruolo = "";
	    	String utenteLoggato="";
	    	
	    	for (Map.Entry<String, String> entry : credenziali.entrySet()) {
	    
	    		utenteLoggato = entry.getKey();
	    		ruolo=entry.getValue();
	    		
	    	}
	    	//System.out.println(utenteLoggato + ruolo);
	    	if (utenteLoggato.length()>0 && ruolo.equals("Manager")) {
	    		
	    		
	    		String[] MenuManager = new String[2];
	    		MenuManager=cm.giveMenuOption("Cosa descideri fare? \nA)Noleggiare un'auto \nB)Cerca auto per costo ", "\nC)Esci", "Non è stata inserita una risposta valida", 3, "ABC");
	    		
	    		if (MenuManager[0].equals("1")) {
	    		        String menuLog = MenuManager[1];
	    		if(menuLog.equalsIgnoreCase("A")) {
	    		ga.noleggia(utenteLoggato);
	    	}
	    		if(menuLog.equalsIgnoreCase("B")) {
	    			ga.cercaAutoCostoDisp();
	    		}
	    	}
	    	}
	    	if (utenteLoggato.length()>0 && ruolo.equals("Clienti")) {
	    		
	    	}
	    	else if (utenteLoggato.length()>0 && ruolo.equals("Bataman")){
	    		
	    	}
	    	}
	   
	    else if(menu.equalsIgnoreCase("C")) {
	    	System.out.println("Arrivederci.");
	    }
	    }  
	   
	    }
	    
	   
	    
	    
	    
	    
	    
  
	    
	    
	    
	
	   
	    

//	    while(true) {
//			
//			System.out.println("Ciao e benvenuto/a! Sei già nostro/a utente? \n1)Sì \n2)No, mi voglio registrare \n3)Esci");
//			Scanner scanner = new Scanner (System.in);
//			int scelta = scanner.nextInt();
//			
//			
//			switch(scelta) {
//				case 1:
//					System.out.println("Inserisci le credenziali.\nEmail:");
//					String email= scanner.nextLine();
//					scanner.nextLine();
//					
//					System.out.println("Inserisci la password:");
//					String password = scanner.nextLine();	
//					
//					break;
//					
//				case 2:
//					System.out.println("Per registrarti inserisci i tuoi dati.\nNome:");
//					scanner.nextLine();
//					String nome = scanner.nextLine();
//					System.out.println("Cognome:");
//					String cognome = scanner.nextLine();
//					System.out.println("Email:");
//					String email = scanner.nextLine();
//					System.out.println("Password:");
//					String password = scanner.nextLine();
//					break;
//					
//				case 3:
//					System.out.println("Arrivederci.");
//					scanner.close();
//				
//			
//				return;
//				
//				default: System.out.println("Inserisci un'opzione da 1 a 5, altrimenti non posso aiutarti");
//				
//			}
//			
//			}

		
	

		

} //chiude tutto


