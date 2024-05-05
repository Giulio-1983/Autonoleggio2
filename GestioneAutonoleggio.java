package autonoleggio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class GestioneAutonoleggio {
	
	private	HashMap<String,AutoNoleggiabile> parcoAuto	= new HashMap<>();
	private HashMap<String,Utente> listaUtenti = new HashMap<>();
	private HashMap<String, Batmobile> listaBatmobili = new HashMap<>();
	String fileUtenti = "src/listaUtenti.txt";
	String fileAuto = "src/parcoAuto.txt";
	String fileBatmobili = "src/listaBatmobili.txt";
	
public GestioneAutonoleggio(HashMap<String,AutoNoleggiabile> parcoAuto,HashMap<String,Utente> listaUtenti,
		HashMap<String, Batmobile> listaBatmobili) {
	super();
	this.parcoAuto = parcoAuto;
	this.listaUtenti = listaUtenti;
	this.listaBatmobili = listaBatmobili;
}

public GestioneAutonoleggio() {
	
}

ConsoleManage cm = new ConsoleManage();

public void caricaFileAuto() {
	
	try {
		BufferedReader br = new BufferedReader(new FileReader(fileAuto));
		String auto; //riga letta in file
		//System.out.println("sto leggendo file"+fileAuto);
		while((auto = br.readLine()) != null) { //loop per leggere tutto il txt
			String [] featuresAuto = auto.split(","); //array per storare i valori comma separated
			
			//System.out.println(featuresAuto.length);
			
			if(featuresAuto.length==6) {
				
				String marca = featuresAuto[0].trim();
				String modello = featuresAuto[1].trim();
				String targa = featuresAuto[2].trim();
				boolean isDisponibile = Boolean.parseBoolean(featuresAuto[3].trim());
				Double costoOrario= Double.parseDouble(featuresAuto[4].trim());	
				String affidatario = featuresAuto[5].trim();
				
			parcoAuto.put(targa, new AutoNoleggiabile(marca,modello,targa,isDisponibile,costoOrario,affidatario));
			
		}	else {
			
			System.out.println("Impossibile inserire l'elemento in parcoAuto");
		}
			
		}
		 br.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			
		}
}

public void caricaFileUtenti() {
	System.out.println(listaUtenti);
	try {
		BufferedReader br = new BufferedReader(new FileReader(fileUtenti));
		String utente; //riga letta in file
		while((utente = br.readLine()) != null) { //loop per leggere tutto il txt
			
			String [] featuresUtente = utente.split(","); //array per storare i valori comma separated
			//System.out.println(featuresUtente.length);
			if(featuresUtente.length==5) {
				
			String nome = featuresUtente[0].trim();
			String cognome = featuresUtente[1].trim();
			String email = featuresUtente[2].trim();
			String password = featuresUtente[3].trim();
			String ruolo = featuresUtente[4].trim();
			
			listaUtenti.put(email, new Utente(nome,cognome,email,password,ruolo)); 
			//for (String key: listaUtenti.keySet()) {
	            //System.out.println("Chiave : " + key);
	           //System.out.println("Dettagli: " +  listaUtenti.get(key));
			//}
		}	else {
			System.out.println("Impossibile inserire l'elemento in listaUtenti");
		}
			
		}
		 br.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	
}


public void salvaFileAuto() {

	try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileAuto));
		
	for (Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		


			bw.write(entry.getValue().getMarca() +", "
					+entry.getValue().getModello()+", "+entry.getValue().getTarga()+", "+entry.getValue().isDisponibile()+", "+entry.getValue().getCostoOrario()+", "+entry.getValue().getAffidatario());
					bw.newLine();
	}
	
	bw.close();
	
} catch (IOException e) {
	e.printStackTrace();
}
}


public void salvaFileUtenti() {
	try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileUtenti));
		
		
		for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
			
		
			bw.write(entry.getKey()+", "+entry.getValue().getNome()+", "+ entry.getValue().getCognome() +", "
					+entry.getValue().getEmail()+", "+entry.getValue().getPassword()+", "+entry.getValue().getRuolo());
			bw.newLine();
		}
		
		bw.close();
		
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	

public void salvaFileBatmobili() {
	
try {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(System.getenv("SystemDriveBatmobili")
				+ File.separator + "com" + File.separator + "test.txt")));
		
			
	for (Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
		

		
			bw.write(entry.getKey()+", "+entry.getValue().getTarga()+", "+ entry.getValue().getMarca() +", "
					+entry.getValue().getModello()+", "+entry.getValue().getTarga()+", "+entry.getValue().getNome()
					+", "+entry.getValue().getAccessori());
			bw.newLine();
		}
		
		bw.close();
		
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	
	

public void caricaFileBatmobili() {
	

	try {
		BufferedReader br = new BufferedReader(new FileReader(fileBatmobili));
		String mobile; //riga letta in file
		while((mobile = br.readLine()) != null) { //loop per leggere tutto il txt
			String [] featuresMobile = mobile.split(","); //array per storare i valori comma separated
			//System.out.println(mobile);
			if(featuresMobile.length==5) {
				
				String marca = featuresMobile[0].trim();
				String modello = featuresMobile[1].trim();
				String targa = featuresMobile[2].trim();
				String nome = featuresMobile[3].trim();
				String accessori = featuresMobile[4].trim();	
				
				
			listaBatmobili.put(targa, new Batmobile(marca,modello,targa,nome,accessori));
		}	else {
			System.out.println("Impossibile inserire l'elemento in listaBatmobili");
		}	
		}
		 br.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	
}

public void cercaAutoCostoDisp() {
    double[] costoH = cm.giveDouble("Inserisci costo: ", "Inserimento invalido, riprova.", "Errore", 3);
    double costoOrario = 0.00;
    
    if (costoH[0] == 1)
        costoOrario = costoH[1];

    List<AutoNoleggiabile> listaAutoCosto = new ArrayList<>();
    for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
        AutoNoleggiabile autoDisp = entry.getValue();
        if (autoDisp.isDisponibile() && autoDisp.getCostoOrario() <= costoOrario) {
            listaAutoCosto.add(autoDisp);
        }
    }

    if (!listaAutoCosto.isEmpty()) {
        System.out.println("Auto disponibili per il costo selezionato:");
        for (AutoNoleggiabile autodisponibile : listaAutoCosto) {
            System.out.println("Marca: " + autodisponibile.getMarca() + ". Modello: " +
                    autodisponibile.getModello() + ". Targa: " + autodisponibile.getTarga() +". Prezzo orario: "+autodisponibile.getCostoOrario());
        }
    } else {
        System.out.println("Nessuna auto trovata per il costo selezionato.");
    }
}

public void mostraAutoDisp() {
	
	System.out.println("La lista completa delle automobili disponibili:");
	List <AutoNoleggiabile> listaAutoDisponibili=new ArrayList<>();
	
	for(Map.Entry<String,AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		

	    AutoNoleggiabile autoDisp=entry.getValue();
		
		if (autoDisp.isDisponibile()) {
			listaAutoDisponibili.add(autoDisp);
		}
		
	}
	if(listaAutoDisponibili.isEmpty()) {
		System.out.println("Spiacenti. Nessuna automobile disponibile.");
	}else {
	for(AutoNoleggiabile autodisponibile:listaAutoDisponibili) {
		System.out.println("Marca: " + autodisponibile.getMarca()+". Modello: " + autodisponibile.getModello()
		+ ". Costo orario: " + autodisponibile.getCostoOrario() +". Targa: " + autodisponibile.getTarga());
	}
}
}

public void cercaAutoMarcaDisp() {
    String[] newMarca = cm.giveString("Inserisci la marca per cui vuoi filtrare la tua ricerca: ", "Inserimento invalido, riprova.", "Errore.", 3);
    String marca = null;
    if (newMarca[0].equals("1"))
        marca = newMarca[1];

    List<AutoNoleggiabile> listaAutoMarca = new ArrayList<>();

    for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
        AutoNoleggiabile autoDisp = entry.getValue();

        if (autoDisp.isDisponibile() && autoDisp.getMarca().equalsIgnoreCase(marca)) {
            listaAutoMarca.add(autoDisp);
        }
    }

    if (!listaAutoMarca.isEmpty()) {
        System.out.println("Auto di marca " + marca + " disponibili:");
        for (AutoNoleggiabile autodisponibile : listaAutoMarca) {
            System.out.println("Modello: " + autodisponibile.getModello() + ". Targa: " + autodisponibile.getTarga()+". Costo orario: " +
                    autodisponibile.getCostoOrario());
        }
    } else {
        System.out.println("Nessun risultato trovato.");
    } 
    }

public void mostraAuto() {
	
	if (parcoAuto.size()>0) {
		
	System.out.println("La lista completa delle automobili disponibili:\n");
	
	for(Map.Entry<String,AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		
		AutoNoleggiabile an = entry.getValue();
		
		System.out.println("Marca: " + an.getMarca()+". Modello: " + an.getModello()
		+ ". Costo orario: " + an.getCostoOrario() +". Targa: " +an.getTarga());
	}
	}else{
		System.out.println("La lista è vuota.");
	}
	
}

public void noleggia(String UtenteLog) {
	
	String[] targa = cm.giveTarga("Inserisci targa", "Inserimento invalido, riprova.", "Errore", 3);
    String t = targa[1];
    boolean autoTrovata=false;
    int record=0; // var che tiene traccia delle entry lette, contatore
    
    while(!autoTrovata) {
    
    for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
        AutoNoleggiabile autoDisp = entry.getValue();
       // System.out.println(autoDisp.isDisponibile()+""+autoDisp.getTarga());
        record=record+1; 
        
        if (autoDisp.isDisponibile() && autoDisp.getTarga().equals(t)) {
        	//System.out.println("entro nel 1 if");
        	 entry.getValue().setDisponibile(!entry.getValue().isDisponibile()); 
        	 entry.getValue().setAffidatario(UtenteLog);
        	 salvaFileAuto();
        	 System.out.println("Auto "+autoDisp.getMarca()+" "+autoDisp.getModello()+" "+autoDisp.getTarga()+" correttamente noleggiata.");
	         autoTrovata=true;
	         break; // Esci dal ciclo perché hai trovato l'auto corrispondente
        }else {
        	if(!autoDisp.isDisponibile() && autoDisp.getTarga().equals(t)){
        		//System.out.println("entro nel 2 if auto trovata= "+autoTrovata);
        		autoTrovata=true;
        		System.out.println("L'automobile selezionata non è attualmente disponibile.");
        		break;
        	}
        	else {
        		
        	if(record==(parcoAuto.size())) {
        		//System.out.println("entro nel 3 if auto trovata= "+autoTrovata);
        		System.out.println("Nessuna auto trovata con la targa inserita.");
        		autoTrovata=true;
        	}
        	}
        }
        	
        	}
  
        
    }
}

	
public void rimuoviAuto() {
	
	String[] targa = cm.giveString("Inserisci targa: ", "Inserimento invalido, riprova.", "Errore", 3);
	String newTarga = targa[1];
	
	boolean autoTrovata=false;
	
	for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		if (newTarga.equals(entry.getKey())) {
			parcoAuto.remove(newTarga);
			autoTrovata=true;
			salvaFileAuto();
			System.out.println("Auto con targa "+newTarga+" correttamente rimossa dall'elenco.");
			return;
		}}
	if(!autoTrovata) {
		
	System.out.println("Nessuna auto trovata con targa "+newTarga+".");
	}
};
	


public void aggiungiAuto() {
	
	String[] marca = cm.giveString("Inserisci marca: ", "Inserimento invalido, riprova.", "Errore", 3);
	String newMarca = marca[1];
	String[] modello = cm.giveString("Inserisci modello: ", "Inserimento invalido, riprova.", "Errore", 3);
	String newModello = modello[1];
	String[] targa = cm.giveString("Inserisci targa: ", "Inserimento invalido, riprova.", "Errore", 3);
	String newTarga = targa[1];
	double[] costoOrario= cm.giveDouble("Inserisci costo orario: ", "Inserimento invalido, riprova.", "Errore", 3);
	double newcostoOrario = costoOrario[1];
	
	for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		if (newTarga.equals(entry.getKey())) {
			System.out.println("Targa già presente in elenco.");
			return;
		}}
	parcoAuto.put(newTarga,(new AutoNoleggiabile (newMarca, newModello, newTarga, true, newcostoOrario, null)));
	System.out.println("Aggiunto nuova auto");
	salvaFileAuto();
}


public void cambiaStatoAuto() {
	
	String[] targa = cm.giveTarga("Inserisci targa", "Inserimento invalido, riprova.", "Errore", 3);
    String t = targa[1];
    boolean autoTrovata=false;
    int record=0;
    while(!autoTrovata) {
    //List<AutoNoleggiabile> listaAutoNoleggiabile = new ArrayList<>();
    for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
        AutoNoleggiabile autoDisp = entry.getValue();
        record=record+1;
       // System.out.println(autoDisp.isDisponibile()+""+autoDisp.getTarga());
        
        if (autoDisp.isDisponibile() && autoDisp.getTarga().equals(t)) {
        	 entry.getValue().setDisponibile(!entry.getValue().isDisponibile()); 
        	 salvaFileAuto();
	           System.out.println("Lo stato dell'auto è stato cambiato con successo: " + entry.getValue().getMarca()+ " "+entry.getValue().getModello());
	           autoTrovata=true;
	           break; // Esci dal ciclo perché hai trovato l'auto corrispondente
        }else {
        	if(record==(parcoAuto.size())) {
        		System.out.println("Nessuna auto trovata con la targa inserita.");	
        		autoTrovata=true;
        	}
        }
        	
        	}
  
        
    }
}

	
//	 String[] targa = cm.giveTarga("Inserisci targa", "Inserimento invalido, riprova.", "Errore", 3);
//	    boolean autoTrovata = false;
//	    for (String t : targa) {
//	    for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
//	        if (entry.getKey().equalsIgnoreCase(t)) {
//	            entry.getValue().setDisponibile(!entry.getValue().isDisponibile());
//	            salvaFileAuto();
//	            System.out.println("Lo stato dell'auto è stato cambiato con successo: " + entry.getValue().toString());
//	            autoTrovata = true;
//	            break; // Esci dal ciclo perché hai trovato l'auto corrispondente
//	        }
//	    }
//	    if (!autoTrovata) {
//	        System.out.println("Nessuna auto trovata con la targa inserita.");
//	    }
//	}	
//}

public void stampaClienti() {

	if (listaUtenti.size() > 0) {

		System.out.println("Nome, Cognome, Mail, Ruolo");

		for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) { // for each per hashmap

			System.out.println(entry.getValue().getNome() + " " + entry.getValue().getCognome() + ", "
					+ entry.getValue().getEmail() + ", " + entry.getValue().getRuolo());
		}
	} else {
		System.out.println("La lista è vuota");
	}
}

public void stampaBatmobili() {
	
	if(listaBatmobili.size()>0) {
		
	for(Entry<String,Batmobile> entry:listaBatmobili.entrySet()) {
		
		System.out.println(entry.toString()); //correggi
	}
	} else {
		System.out.println("La lista è vuota");
	}
	
}

public void aggiungiBatmobile(String marca, String modello, String targa, String nome, String accessori ) {
	
	listaBatmobili.put(targa,(new Batmobile (marca, modello, targa, nome, accessori)));
	System.out.println("Aggiunto Batmobile");
	salvaFileBatmobili();
	
}

public void rimuoviBatmobile(String targa) {

	listaBatmobili.remove(targa);
	salvaFileBatmobili();
}

public void registraUtente() {  

	String[] newNome= cm.giveString("Inserisci il tuo nome:", "Inserimento invalido, riprova.", "Errore.", 3);
		String nome = null;
		if(newNome[0].equals("1")) 
			nome = newNome[1];
	//System.out.println(nome);	
		
	String[] newCognome = cm.giveString("Inserisci il tuo cognome:", "Inserimento invalido, riprova.", "Errore.", 3);
		String cognome = null;
		if(newCognome[0].equals("1")) 
			cognome = newCognome[1];
		//System.out.println(cognome);	

	String[] newEmail = cm.giveMail("Inserisci un indirizzo email valido:", "Inserimento invalido, riprova.", "Errore.", 3);
		String email = null;
		if (newEmail[0].equals("1")) 
	      email = newEmail[1];
		//System.out.println(email);	   
	        
	String[] newPW = cm.giveString("Inserisci la tua password:", "Inserimento invalido, riprova.", "Errore.", 3);
		String password = null;
		if(newPW[0].equals("1")) 
		 password = newPW[1];
		//System.out.println(password);	
	
	for(Map.Entry<String, Utente>entry:listaUtenti.entrySet()) {
		if(entry.getValue().getEmail().equalsIgnoreCase(email)) {
			System.out.println("Nel sistema è già presente questo utente");
			break;
		} else {
			//System.out.println(email);
			listaUtenti.put(email, new Utente(nome, cognome, email, password, "cliente"));
			//System.out.println(listaUtenti.toString());
		
			System.out.println("Aggiunto nuovo utente al sistema");
			break;
		}
	}
	salvaFileUtenti();  // salva lista utenti nel file
}

public HashMap login() {
    Scanner scanner = new Scanner(System.in);

    boolean accessoRiuscito = false;
    int tentativiRimanenti = 3; // Numero massimo di tentativi consentiti
    HashMap<String,String> logIn=new HashMap<>();
    //    caricaFileUtenti();
    
    while (tentativiRimanenti > 0) {
        System.out.print("Inserisci l'email: ");
        String email = scanner.nextLine();

        System.out.print("Inserisci la password: ");
        String password = scanner.nextLine();
        
        
        for(Map.Entry<String, Utente> utente : listaUtenti.entrySet()) {
            Utente utenteLog = utente.getValue();
            if(utenteLog.getEmail().equals(email) && utenteLog.getPassword().equals(password)) {
                System.out.println("Accesso riuscito! Benvenuto/a, " + utenteLog.getNome()+".");
                accessoRiuscito = true;
                logIn.put(utenteLog.getEmail(),utenteLog.getRuolo());
                break; // esce perché l'utente è stato trovato
            }
        }

        if (accessoRiuscito) {
      
            break; // esce perché accesso è riuscito
        } else {
            System.out.println("Accesso negato. Tentativi rimanenti: " + --tentativiRimanenti);
            if (tentativiRimanenti > 0) {
                System.out.println("Riprova.");
            }
       
        }
    }

    if (!accessoRiuscito) {
        System.out.println("Numero massimo di tentativi raggiunto. Accesso negato.");
    }

    
    //System.out.println("ritorno login");
	return logIn;
}



}