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

import autonoleggio.Enum.Ruolo;



public class GestioneAutonoleggio {
	
	ConsoleManage cm = new ConsoleManage();
	private	HashMap<String,AutoNoleggiabile> parcoAuto	= new HashMap<>();
	private HashMap<String,Utente> listaUtenti = new HashMap<>();
	private HashMap<String, Batmobile> listaBatmobili = new HashMap<>();


public GestioneAutonoleggio(HashMap<String,AutoNoleggiabile> parcoAuto,HashMap<String,Utente> listaUtenti,
		HashMap<String, Batmobile> listaBatmobili) {
	super();
	this.parcoAuto = parcoAuto;
	this.listaUtenti = listaUtenti;
	this.listaBatmobili = listaBatmobili;
}

public GestioneAutonoleggio() {
	
}


public void caricaFileAuto() {
	

	try {
		BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("SystemDriveAuto") 
				+ File.separator + "com" + File.separator + "test.txt")));
		String auto; //riga letta in file
		while((auto = br.readLine()) != null) { //loop per leggere tutto il txt
			String [] featuresAuto = auto.split(","); //array per storare i valori comma separated
			//System.out.println(auto);
			if(featuresAuto.length==6) {
				
			parcoAuto.put(featuresAuto[2].trim(), new AutoNoleggiabile(featuresAuto[0].trim(), featuresAuto[1].trim(), featuresAuto[2].trim(),
					Boolean.parseBoolean(featuresAuto[3].trim()), Double.parseDouble(featuresAuto[4].trim()),featuresAuto[5].trim())); 
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
	try {
		BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("SystemDriveUtenti") 
				+ File.separator + "com" + File.separator + "test.txt")));
		String utente; //riga letta in file
		while((utente = br.readLine()) != null) { //loop per leggere tutto il txt
			String [] featuresUtente = utente.split(","); //array per storare i valori comma separated
			//System.out.println(utente);
			if(featuresUtente.length==5) {
				
			listaUtenti.put(featuresUtente[2].trim(), new Utente(featuresUtente[0].trim(), featuresUtente[1].trim(),
					featuresUtente[2].trim(), featuresUtente[3].trim(), Ruolo.valueOf(featuresUtente[4].trim()))); 
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
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(System.getenv("SystemDriveAuto")
				+ File.separator + "com" + File.separator + "test.txt")));
		
			
	for (Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		

		try {
			bw.write(entry.getKey()+", "+entry.getValue().getTarga()+", "+ entry.getValue().getMarca() +", "
					+entry.getValue().getModello()+", "+entry.getValue().getTarga()+", "+entry.getValue().isDisponibile()
							+", "+ entry.getValue().getCostoOrario());
		
			bw.newLine();
		} 

		catch (Exception e) {
			bw.write(entry.getKey()+", "+entry.getValue().getTarga()+", "+ entry.getValue().getMarca() +", "
		+entry.getValue().getModello()+", "+entry.getValue().getTarga()+", "+entry.getValue().isDisponibile()
				+", "+ entry.getValue().getCostoOrario());

			bw.newLine();
		}
	
	}
	bw.close();
	//System.out.println("HO SALVATO");
	
	}catch(IOException e) {
		e.printStackTrace();
		 
	}
	
	
}

public void salvaFileUtenti() {
	
try {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(System.getenv("SystemDriveUtenti")
				+ File.separator + "com" + File.separator + "test.txt")));
		
			
	for (Entry<String, Utente> entry : listaUtenti.entrySet()) {
		

		try {
			bw.write(entry.getKey()+", "+entry.getValue().getEmail()+", "+ entry.getValue().getNome() +", "
					+entry.getValue().getCognome()+", "+entry.getValue().getEmail()+", "+entry.getValue().getRuolo());
		
			bw.newLine();
	}
		catch (Exception e) {
			bw.write(entry.getKey()+", "+entry.getValue().getEmail()+", "+ entry.getValue().getNome() +", "
					+entry.getValue().getCognome()+", "+entry.getValue().getEmail()+", "+entry.getValue().getRuolo());
			
			bw.newLine();
		}
	
	}
	bw.close();
	//System.out.println("HO SALVATO");
	
	}catch(IOException e) {
		e.printStackTrace();
	
}
}

public void salvaFileBatmobili() {
	
try {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(System.getenv("SystemDriveBatmobili")
				+ File.separator + "com" + File.separator + "test.txt")));
		
			
	for (Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
		

		try {
			bw.write(entry.getKey()+", "+entry.getValue().getTarga()+", "+ entry.getValue().getMarca() +", "
					+entry.getValue().getModello()+", "+entry.getValue().getTarga()+", "+entry.getValue().getNome()
					+", "+entry.getValue().getAccessori());
		
			bw.newLine();
		}

		catch (Exception e) {
			bw.write(entry.getKey()+", "+entry.getValue().getTarga()+", "+ entry.getValue().getMarca() +", "
					+entry.getValue().getModello()+", "+entry.getValue().getTarga()+", "+entry.getValue().getNome()
					+", "+entry.getValue().getAccessori());
			
			bw.newLine();
		}
	
	}
	bw.close();
	//System.out.println("HO SALVATO");
	
	}catch(IOException e) {
		e.printStackTrace();
	
}
	
}

public void caricaFileBatmobili() {
	

	try {
		BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("SystemDriveBatmobili") 
				+ File.separator + "com" + File.separator + "test.txt")));
		String mobile; //riga letta in file
		while((mobile = br.readLine()) != null) { //loop per leggere tutto il txt
			String [] featuresMobile = mobile.split(","); //array per storare i valori comma separated
			//System.out.println(mobile);
			if(featuresMobile.length==5) {
				
			listaBatmobili.put(featuresMobile[2].trim(), new Batmobile(featuresMobile[0].trim(), featuresMobile[1].trim(),
						featuresMobile[2].trim(), featuresMobile[3].trim(), featuresMobile[4].trim()));
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
        if (autoDisp.isDisponibile() && autoDisp.getCostoOrario() == costoOrario) {
            listaAutoCosto.add(autoDisp);
        }
    }

    if (!listaAutoCosto.isEmpty()) {
        System.out.println("Auto disponibili per il costo selezionato:");
        for (AutoNoleggiabile autodisponibile : listaAutoCosto) {
            System.out.println("Marca: " + autodisponibile.getMarca() + ". Modello: " +
                    autodisponibile.getModello() + ". Targa: " + autodisponibile.getTarga());
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
            System.out.println("Modello: " + autodisponibile.getModello() + ". Costo orario: " +
                    autodisponibile.getCostoOrario() + ". Targa: " + autodisponibile.getTarga());
        }
    } else {
        System.out.println("Nessun risultato trovato.");
    } 
    }

public void mostraAuto() {
	System.out.println("La lista completa delle automobili disponibili:\n");
	
	for(Map.Entry<String,AutoNoleggiabile> entry : parcoAuto.entrySet()) {
		
		AutoNoleggiabile an = entry.getValue();
		
		System.out.println("Marca: " + an.getMarca()+". Modello: " + an.getModello()
		+ ". Costo orario: " + an.getCostoOrario() +". Targa: " +an.getTarga());
	}
	
}

public void noleggia() {
	
	
	
}
public void rimuoviAuto(String targa) {
	
	parcoAuto.remove(targa);
	salvaFileAuto();
	
}

public void aggiungiAuto(String marca, String modello, String targa, Boolean isDisponibile, double costoOrario, String affidatario) {
	
	parcoAuto.put(targa,(new AutoNoleggiabile (marca, modello, targa, isDisponibile, costoOrario, affidatario)));
	System.out.println("Aggiunto nuova auto");
	salvaFileAuto();
}

public void cambiaStatoAuto() {
	
	 String[] targa = cm.giveTarga("Inserisci targa", "Inserimento invalido, riprova.", "Errore", 3);
	    boolean autoTrovata = false;
	    for (String t : targa) {
	    for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
	        if (entry.getKey().equalsIgnoreCase(t)) {
	            entry.getValue().setDisponibile(!entry.getValue().isDisponibile());
	            salvaFileAuto();
	            System.out.println("Lo stato dell'auto è stato cambiato con successo: " + entry.getValue().toString());
	            autoTrovata = true;
	            break; // Esci dal ciclo perché hai trovato l'auto corrispondente
	        }
	    }
	    if (!autoTrovata) {
	        System.out.println("Nessuna auto trovata con la targa inserita.");
	    }
	}	
}

public void stampaClienti() {
	

	for (Entry<String, Utente> entry : listaUtenti.entrySet()) { //for each per hashmap 
		
		
		System.out.println(entry.toString());
	}
}

public void stampaBatmobili() {
	
	if(listaBatmobili.size()>0) {
		
	for(Entry<String,Batmobile> entry:listaBatmobili.entrySet()) {
		
		System.out.println(entry.toString());
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
			
		
	String[] newCognome = cm.giveString("Inserisci il tuo cognome:", "Inserimento invalido, riprova.", "Errore.", 3);
		String cognome = null;
		if(newCognome[0].equals("1")) 
			cognome = newCognome[1];
		

	String[] newEmail = cm.giveMail("Inserisci un indirizzo email valido:", "Inserimento invalido, riprova.", "Errore.", 3);
		String email = null;
		if (newEmail[0].equals("1")) 
	      email = newEmail[1];
	    
	        
	String[] newPW = cm.giveString("Inserisci la tua password:", "Inserimento invalido, riprova.", "Errore.", 3);
		String password = null;
		if(newPW[0].equals("1")) 
		 password = newPW[1];
	
	
	for(Map.Entry<String, Utente>entry:listaUtenti.entrySet()) {
		if(entry.getValue().getEmail().equalsIgnoreCase(email)) {
			System.out.println("Nel sistema è già presente questo utente");
		} else {
			listaUtenti.put(email, new Utente(nome, cognome, email, password, null));
			System.out.println("Aggiunto nuovo utente al sistema");
		}
	}
	salvaFileUtenti();  // salva lista utenti nel file
}


}