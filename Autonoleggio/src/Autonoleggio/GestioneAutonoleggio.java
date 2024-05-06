package autonoleggio;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class GestioneAutonoleggio {
	private HashMap<String, AutoNoleggiabile> parcoAuto = new HashMap<>();
	private HashMap<String, Utente> listaUtenti = new HashMap<>();
	private HashMap<String, Batmobile> listaBatmobili = new HashMap<>();
	private Map<Integer, NoleggioStorico> autoNoleggate = new HashMap<>();
	private ConsoleManage cm = new ConsoleManage();
	private String myMail = "";
	private String myRuolo = "";
	private LocalDateTime inizioDataOra = null;
	private LocalDateTime fineDataOra = null;

	Scanner scanner = new Scanner(System.in);

	public GestioneAutonoleggio() {

	}

	public void login() {
		while (true) {
			System.out.println("Sei un utente registrato? \n1. Si \n2. No \n3. Esci");
			int scelta = scanner.nextInt();
			scanner.nextLine(); // per il problema della riga mangiata

			switch (scelta) {
			case 1:
				effettuaLogin();
				for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
					if (entry.getKey().equals(myMail)) {
						myRuolo = entry.getValue().getRuolo();
					}
				}

				if (myRuolo.equals("utente")) {
					menuUtente();
				}
				if (myRuolo.equals("manager")) {
					menuManager();
				}
				if (myRuolo.equals("batman")) {
					menuBatman();
				}
				break;

			case 2:
				registraUtente();
				break;
			case 3:
				System.out.println("Arrivederci");
				return;
			default:
				System.out.println("Scelta non valida");
				break;
			}
		}
	}

	private void effettuaLogin() {
		// Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la tua email");
		String email = scanner.nextLine();
		
		if (email.equalsIgnoreCase("bruce@batcaverna.bat")) {
	        System.out.println("Inserisci la tua password");
	        String password = scanner.nextLine();
	        if (password.equals("batmansuperpassword")) {
	          menuBatman();
	        } else {
	          return;
	        }
		
		for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
			if (entry.getValue().getEmail().equals(email)) {
				System.out.println("Inserisci la tua password");
				String password = scanner.nextLine();
				if (entry.getValue().getPassword().equals(password)) {
					myMail = entry.getValue().getEmail();
					myRuolo = entry.getValue().getRuolo();
					System.out.println("Login effettuato con successo");
					return;
				} else {
					System.out.println("Password errata");
					return;
				}
			}
		}

		if (!listaUtenti.containsKey(email)) {
			System.out.println("Email non trovata. Effettua la registrazione");
			// registraUtente();
			return;
		}
		return;
	}

	

	private void registraUtente() {
		String nome = "";
		String cognome = "";
		String email = "";
		String password = "";
		String ruolo = "utente";

		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci il tuo nome"); // magari usare metodo di ConsoleManage
		nome = scanner.nextLine();
		if (!nome.equals("")) {

			System.out.println("Inserisci il tuo cognome"); // magari usare metodo di ConsoleManage
			cognome = scanner.nextLine();
			if (!cognome.equals("")) {

				System.out.println("Inserisci la tua email"); // magari usare metodo di ConsoleManage
				email = scanner.nextLine();
				if (!email.equals("")) {

					if (listaUtenti.containsKey(email)) {
						System.out.println("Email già registrata. Effettua il login");

						return;
					}

					System.out.println("Inserisci la tua password");
					password = scanner.nextLine();
					if (!password.equals("")) {

						Utente nuovoUtente = new Utente(nome, cognome, email, password, ruolo);
						listaUtenti.put(email, nuovoUtente);
						salvaFileUtenti();

						System.out.println("Registrazione effettuata con successo");
					}
				}
			}
		}

	}

	public void menuUtente() {
		while (true) {
			System.out.println("Cosa vuoi fare?");
			System.out.println("1. Noleggia Auto");
			System.out.println("2. Visualizza auto disponibili");
			System.out.println("3. Cerca auto per marca");
			System.out.println("4. Cerca auto per prezzo");
			System.out.println("5. Esci");

			int scelta = scanner.nextInt();
			scanner.nextLine(); // per il problema della riga mangiata

			switch (scelta) {
			case 1:
				noleggia();
				break;
			case 2:
				mostraAutoDisp();
				break;
			case 3:
				cercaAutoMarcaDisp();
				break;
			case 4:
				cercaAutoCostoDisp();
				break;
			case 5:
				System.out.println("Arrivederci!");
				return;
			default:
				System.out.println("Scelta non valida.");
				break;
			}
		}
	}

	public void menuManager() {
		while (true) {
			System.out.println("Cosa vuoi fare?");
			System.out.println("1. Aggiungi auto");
			System.out.println("2. Rimuovi auto");
			System.out.println("3. Modifica auto");
			System.out.println("4. Aggiungi Utente");
			System.out.println("5. Rimuovi Utente");
			System.out.println("6. Modifica Utente");
			System.out.println("7. Visualizza auto disponibili");
			System.out.println("8. Visualizza il parco auto completo");
			System.out.println("9. Esci");

			int scelta = scanner.nextInt();
			scanner.nextLine(); // per il problema della riga mangiata

			switch (scelta) {
			case 1:
				aggiungiAuto();
				break;
			case 2:
				mostraAutoDisp();
				break;
			case 3:
				cercaAutoMarcaDisp();
				break;
			case 4:
				cercaAutoCostoDisp();
			case 5:
				System.out.println("Arrivederci!");
				return;
			default:
				System.out.println("Scelta non valida.");
				break;
			}
		}
	}

	public void menuBatman() {

	}

	public Utente getUtente(String email) {
		return listaUtenti.get(email);
	}

	public void caricaFileAuto() {
		try {
			File fileAuto = new File("fileAuto.txt");
			FileReader fileReader = new FileReader(fileAuto);
			BufferedReader br = new BufferedReader(fileReader);
			String line;
			while ((line = br.readLine()) != null) {
				String[] datiAuto = line.split(",");
				if (datiAuto.length == 5) {
					String marca = datiAuto[0].trim();
					String modello = datiAuto[1].trim();
					String targa = datiAuto[2].trim();
					boolean isDisponibile = Boolean.parseBoolean(datiAuto[3].trim());
					double costoOrario = Double.parseDouble(datiAuto[4].trim());
					parcoAuto.put(targa, new AutoNoleggiabile(marca, modello, targa, isDisponibile, costoOrario));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvaFileAuto() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("fileAuto.txt"));
			for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaFileUtenti() {
		try {
			File elencoUtenti = new File("fileUtenti.txt");
			FileReader fileReader = new FileReader(elencoUtenti);
			BufferedReader br = new BufferedReader(fileReader);

			String line;
			while ((line = br.readLine()) != null) {
				String[] datiUtenti = line.split(",");
				if (datiUtenti.length == 5) {
					String nome = datiUtenti[0];
					String cognome = datiUtenti[1];
					String email = datiUtenti[2];
					String password = datiUtenti[3];
					String ruolo = datiUtenti[4];

					// Creo l'oggetto e lo aggiungo all'HashMap:

					listaUtenti.put(email, new Utente(nome, cognome, email, password, ruolo));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void salvaFileUtenti() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("fileUtenti.txt"));
			for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaFileBatmobili() {

		try {
			File fileBatmobili = new File("fileBatmobili.txt");
			FileReader fileReader = new FileReader(fileBatmobili);
			BufferedReader br = new BufferedReader(fileReader);
			String line;
			while ((line = br.readLine()) != null) {
				String[] datiBatmobile = line.split(",");
				if (datiBatmobile.length == 5) {
					String marca = datiBatmobile[0].trim();
					String modello = datiBatmobile[1].trim();
					String targa = datiBatmobile[2].trim();
					String nome = datiBatmobile[3].trim();
					String accessori = datiBatmobile[4].trim();

					// Creo l'oggetto Batmobile e lo aggiungo all'HashMap
					Batmobile nuovaBatmobile = new Batmobile(marca, modello, targa, nome, accessori);
					listaBatmobili.put(targa, nuovaBatmobile);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void salvaFileBatmobili(HashMap<String, Batmobile> listaBatmobili) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("fileBatmobili.txt"));
			for (Batmobile batmobile : listaBatmobili.values()) {
				String linea = batmobile.getMarca() + "," + batmobile.getModello() + "," + batmobile.getTarga() + ","
						+ batmobile.getNome() + "," + batmobile.getAccessori();
				bw.write(linea);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cercaAutoCostoDisp() {

		System.out.println("Inserisci il prezzo orario massimo");
		double prezzoMax = scanner.nextDouble();

		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().getCostoOrario() <= (prezzoMax)) {
				System.out.println(entry.getValue().toString());
			}
		}
	}

	public void mostraAutoDisp() {
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().isDisponibile()) {

				System.out.println(entry.getValue().toString());
			}
		}
	}

	public void cercaAutoMarcaDisp() {
		System.out.println("Inserisci la marca che vuoi cercare");
		String marca = scanner.nextLine();

		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().getMarca().equals(marca)) {
				System.out.println(entry.getValue().toString());
			}
		}
	}

	public void mostraAuto() { // Restituisce tutto il parco auto, escluse le Batmobili
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {

			System.out.println(entry.getValue().toString());
		}
	}

	public void chiediPeriodo() {
		LocalTime minHour = null;
		LocalDate dateInizio = null;
		LocalTime timeInizio = null;
		LocalDate dateFine = null;
		LocalTime timeFine = null;
		int n = 0;

		while (n <= 3) {
			n++;
			dateInizio = cm.dammiDatainFuturo("Inserisci la data inizio noleggio : dd-MM-yyyy",
					"Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo",
					3);
			if (dateInizio.equals(LocalDate.now())) {
				minHour = LocalTime.now();
			} else {
				minHour = LocalTime.of(0, 0);
			}

			timeInizio = cm.dammiOraInFuturo("Inserisci un orario del inizio noleggio (HH:mm)",
					"Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con sucesso",
					3, minHour);
			inizioDataOra = dateInizio.atTime(timeInizio);

			dateFine = cm.dammiDatainFuturo("Inserisci la data fine noleggio : dd-MM-yyyy",
					"Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo",
					3);
			timeFine = cm.dammiOraInFuturo("Inserisci un orario del fine noleggio (HH:mm)",
					"Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con sucesso",
					3, minHour);
			fineDataOra = dateFine.atTime(timeFine).truncatedTo(ChronoUnit.MINUTES);

			if (inizioDataOra.isAfter(fineDataOra)) {
				System.out.println("Data e ora di inizio non puo essere maggiore della data e ora di fine");
			} else {
				return;
			}

		}
	}

	public NoleggioStorico calcolaCosto(AutoNoleggiabile autoNoleggiabile) {
//		LocalTime minHour = null;
//		LocalDate dateInizio = null;
//		LocalTime timeInizio = null;
//		LocalDate dateFine = null;
//		LocalTime timeFine = null;
//
//		LocalDateTime inizioDataOra = null;
//		LocalDateTime fineDataOra = null;
		Duration durata = null;
		Double costo = null;
		NoleggioStorico noleggioStorico = null;

		// se disponibile
		if (autoNoleggiabile != null && autoNoleggiabile.isDisponibile() == true) {
			// Start data ora
//			dateInizio = cm.dammiDatainFuturo("Inserisci la data inizio noleggio : dd-MM-yyyy",
//					"Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo",
//					3);
//			if (dateInizio.equals(LocalDate.now())) {
//				minHour = LocalTime.now();
//			} else {
//				minHour = LocalTime.of(0, 0);
//			}
//
//			timeInizio = cm.dammiOraInFuturo("Inserisci un orario del inizio noleggio (HH:mm)",
//					"Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con sucesso",
//					3, minHour);
//			inizioDataOra = dateInizio.atTime(timeInizio);

			noleggioStorico = new NoleggioStorico(autoNoleggiabile.getTarga(), myMail, inizioDataOra, null, costo);
		} else if (autoNoleggiabile != null && autoNoleggiabile.isDisponibile() == false) {
			// se non disponibile- prendo auto per targa
			for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
				if (entry.getValue().getTarga().equalsIgnoreCase(autoNoleggiabile.getTarga())) {
					inizioDataOra = entry.getValue().getInizioNoleggio();
					noleggioStorico = new NoleggioStorico(entry.getValue().getTarga().trim(),
							entry.getValue().getAffidatarioEmail().trim(), inizioDataOra,
							entry.getValue().getFineNoleggio(), entry.getValue().getSommaPagata());
					break;
				}
			}
		} else {
			System.out.println("Auto non trovata o non disponibile, impossibile calcolare il costo.");
		}
		// fine data ora
//		dateFine = cm.dammiDatainFuturo("Inserisci la data fine noleggio : dd-MM-yyyy",
//				"Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo", 3);
//		timeFine = cm.dammiOraInFuturo("Inserisci un orario del fine noleggio (HH:mm)",
//				"Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con sucesso", 3,
//				minHour);
//		fineDataOra = dateFine.atTime(timeFine).truncatedTo(ChronoUnit.MINUTES);

		// Calcolo durata e costo
		if (inizioDataOra != null && fineDataOra != null) {
			durata = Duration.between(inizioDataOra, fineDataOra);
			costo = autoNoleggiabile.getCostoOrario() * durata.toHours();
			noleggioStorico.setFineNoleggio(fineDataOra.truncatedTo(ChronoUnit.MINUTES));
			noleggioStorico.setSommaPagata(costo);
		}

		return noleggioStorico;
	}

	public AutoNoleggiabile cercaAutoPerTarga() {
		AutoNoleggiabile autoNoleggiabile = null;
		String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova",
				"Inserimento Non andato con successo", "Inserimento andato con successo", 3);
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(targa)) {
				autoNoleggiabile = entry.getValue();
				break;
			}
		}
		return autoNoleggiabile;
	}

	// metodo di supporto -da chiamare in noleggia
	public void salvaFileAutoNoleggiate() {
		String linea;
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter("fileNoleggioStorico.txt"));
			for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
				linea = entry.getKey() + "," + entry.getValue() + "\n";
				br.write(linea);
			}
			br.close();
			System.out.println("Il file autonoleggiate è stato aggiornato");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public boolean controllaData(LocalDateTime inizioDataOra, LocalDateTime fineDataOra, String targa) {
		// controllo dataOra inizio<fine
		if (inizioDataOra.isAfter(fineDataOra)) {
			System.out.println("Data e ora di inizio non puo essere maggiore della data e ora di fine");
			return false;
		}
		// controllo se datae Ora non in passato
		if (inizioDataOra.isAfter(LocalDateTime.now())) {
			for (NoleggioStorico noleggio : autoNoleggate.values()) {
				// Controllo se auto e occupata in range date inserite
				if (noleggio.getTarga().equalsIgnoreCase(targa) && ((inizioDataOra.isAfter(noleggio.getInizioNoleggio())
						&& inizioDataOra.isBefore(noleggio.getFineNoleggio()))
						|| (fineDataOra.isAfter(noleggio.getInizioNoleggio())
								&& fineDataOra.isBefore(noleggio.getFineNoleggio())))) {
					return false;
				}
			}
		}
		return true;
	}

	public void cercaAutoDispFuturo() {
		ArrayList<String> targaOccupata = new ArrayList<>();
		String targaInEsame = "";

		for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
			if ((entry.getValue().getInizioNoleggio().isAfter(inizioDataOra)
					&& entry.getValue().getInizioNoleggio().isBefore(fineDataOra))
					|| (entry.getValue().getFineNoleggio().isAfter(inizioDataOra)
							&& entry.getValue().getFineNoleggio().isBefore(fineDataOra))) {
				targaOccupata.add(entry.getValue().getTarga());
			}
		}

		for (Map.Entry<String, AutoNoleggiabile> entry2 : parcoAuto.entrySet()) {
			targaInEsame = entry2.getValue().getTarga();
			if (!targaOccupata.contains(targaInEsame) && entry2.getValue().isDisponibile()) {
				System.out.println(entry2.getValue().toString());
			}
		}
	}

	public void noleggia() {
		chiediPeriodo();
		cercaAutoDispFuturo();
//		mostraAutoDisp();
		AutoNoleggiabile autoNoleggiabile = cercaAutoPerTarga();
		if (autoNoleggiabile != null) {
			NoleggioStorico noleggioStorico = calcolaCosto(autoNoleggiabile);
			Double costo = noleggioStorico.getSommaPagata();
			// Controllo pagamento finto
			if (costo != null) {
				// Controllo disponibilita in data inserita
				if (controllaData(noleggioStorico.getInizioNoleggio(), noleggioStorico.getFineNoleggio(),
						autoNoleggiabile.getTarga())) {
					autoNoleggate.put(NoleggioStorico.getNumFattura(), noleggioStorico);
					autoNoleggiabile.setDisponibile(false);
					parcoAuto.put(autoNoleggiabile.getTarga(), autoNoleggiabile);
					salvaFileAutoNoleggiate();
					salvaFileAuto();
					System.out.println("Hai noleggiato auto: " + autoNoleggiabile.getMarca() + " "
							+ autoNoleggiabile.getModello() + ", Costo: " + costo);
				} else {
					System.out.println("Auto non disponibile durante questo periodo");
				}
			} else {
				System.out.println("Noleggio NON è effettuato");
			}
		} else {
			System.out.println("Auto non trovata");
		}
	}

	// Chiedo all'utente di scegliere un'opzione
//      System.out.println("Scegli un'opzione:");
//      System.out.println("1. Visualizza tutte le auto disponibili");
//      System.out.println("2. Filtra le auto disponibili per marca");
//      System.out.println("3. Filtra le auto disponibili per prezzo");
//      List<AutoNoleggiabile> listaFiltrata = new ArrayList<AutoNoleggiabile>();
//      int opzione = scanner.nextInt();
//      scanner.nextLine(); // Per il problema della riga mangiata
//      switch (opzione) {
//          case 1:
//              mostraAutoDisp();
//              listaFiltrata = mostraAutoDisp();
//              break;
//          case 2:
//              System.out.println("Inserisci la marca:");
//              String marca = scanner.nextLine();
//              cercaAutoMarcaDisp(marca);
//              listaFiltrata = cercaAutoMarcaDisp(marca);
//              break;
//          case 3:
//              System.out.println("Inserisci il prezzo massimo:");
//              double prezzoMax = scanner.nextDouble();
//              cercaAutoCostoDisp(prezzoMax);
//              listaFiltrata = cercaAutoCostoDisp(prezzoMax);
//              break;
//          default:
//              System.out.println("Opzione non valida.");
//              scanner.close();
//              return;
//      }

//      // Chiedo all'utente di selezionare un'auto
//      System.out.println("Seleziona il numero corrispondente all'auto che desideri noleggiare:");
//      int scelta = scanner.nextInt();
//      scanner.nextLine(); // Per il problema della riga mangiata
//
//      // Verifica se la scelta è valida
//      if (scelta < 1 || scelta > listaFiltrata.size()) {
//          System.out.println("Scelta non valida.");
//          scanner.close();
//          return;
//      }
//    }

//	public void rimuoviAuto(HashMap<String, AutoNoleggiabile> parcoAuto) {
//		Scanner scanner = new Scanner(System.in);
//		if (parcoAuto.isEmpty()) {
//			System.out.println("Nessuna auto nel parco auto.");
//			scanner.close();
//			return;
//		}
//
//		System.out.println("Inserisci la targa dell'auto da rimuovere:");
//		String targaDaRimuovere = scanner.nextLine().trim();
//		if (!parcoAuto.containsKey(targaDaRimuovere)) {
//			System.out.println("Auto non trovata.");
//			scanner.close();
//			return;
//		} else {
//			parcoAuto.remove(targaDaRimuovere);
//			System.out.println("Auto rimossa con successo.");
//			salvaFileAuto(parcoAuto);
//		}
//		scanner.close();
//	}

	public void aggiungiAuto() {
		String marca = "";
		String modello = "";
		String targa = "";
		double costoOrario = 0;

		System.out.println("Inserisci la marca"); // dovrei usare metodo dammiStringa per i controlli e richiedere 3 volte
		marca = scanner.nextLine().trim().toUpperCase();
		if (!marca.equals("")) {
			System.out.println("Inserisci il modello");
			modello = scanner.nextLine().trim().toUpperCase();
			if (!modello.equals("")) {
				System.out.println("Inserisci la targa");
				targa = scanner.nextLine().trim().toUpperCase();
				if (!targa.equals("")) {

					System.out.println("Inserisci il costo orario");
					costoOrario = scanner.nextDouble();
					if (costoOrario != 0) {
						if (parcoAuto.containsKey(targa)) {
							System.out.println("L'auto è già presente nel parco auto");
							return;
						} else {

							parcoAuto.put(targa, new AutoNoleggiabile(marca, modello, targa, true, costoOrario));

							salvaFileAuto();

							System.out.println("L'auto è stata aggiunta al parco auto");
						}
					}
				}
			}
		}
	}

	public void cambiaStatoAuto(HashMap<String, AutoNoleggiabile> parcoAuto) {
		Scanner scanner = new Scanner(System.in);
		if (parcoAuto.isEmpty()) {
			System.out.println("Nessuna auto nel parco auto.");
			scanner.close();
			return;
		}
		System.out.println("Inserisci la targa dell'auto di cui vuoi cambiare lo stato:");
		String targaAuto = scanner.nextLine().trim();
		if (!parcoAuto.containsKey(targaAuto)) {
			System.out.println("Auto con la targa specificata non trovata nel parco auto.");
			scanner.close();
			return;
		}
		AutoNoleggiabile autoDaModificare = parcoAuto.get(targaAuto);
		System.out.println("Inserisci il nuovo stato dell'auto (true per disponibile, false per non disponibile");
		boolean nuovaDisponibilita = scanner.nextBoolean();
		autoDaModificare.setIsDisponibile(nuovaDisponibilita);
		// salvaFileAuto(parcoAuto);
		scanner.close();
	}

	public void stampaClienti(HashMap<String, Utente> listaUtenti) {
		for (Utente utente : listaUtenti.values()) {
			if (!utente.getNome().equalsIgnoreCase("Batman")) {
				System.out.println(utente.getNome() + " " + utente.getCognome() + " - Email: " + utente.getEmail()
						+ " - Ruolo: " + utente.getRuolo());
			}
		}

	}

	public void caricaFileNoleggio() {
		String linea;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTimeInizio = null;
		LocalDateTime dateTimeFine = null;
		try {
			BufferedReader breader = new BufferedReader(new FileReader("fileNoleggioStorico.txt"));
			while ((linea = breader.readLine()) != null) {
				String[] datiNoleggio = linea.split(",");
				if (datiNoleggio.length == 6) {
					if (datiNoleggio[3] != null)
						dateTimeInizio = LocalDateTime.parse(datiNoleggio[3], formatter);
					if (datiNoleggio[4] != null)
						dateTimeFine = LocalDateTime.parse(datiNoleggio[4], formatter);

					NoleggioStorico noleggioStorico = new NoleggioStorico(datiNoleggio[1].trim(),
							datiNoleggio[2].trim(), dateTimeInizio, dateTimeFine,
							Double.parseDouble(datiNoleggio[5].trim()));

					autoNoleggate.put(Integer.parseInt(datiNoleggio[0].trim()), noleggioStorico);
				} else {
					System.out.println("Inserimento in HashMap autoNoleggiate non è possibile");
				}
			}
			breader.close();
		} catch (IOException e) {
			System.out.println("Problema di leggere da file");
		}
	}

	public void stampaBatmobili() {

	}

	public void aggiungiBatmobile() {
		HashMap<String, Batmobile> listaBatmobili = new HashMap<String, Batmobile>();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la marca della Batmobile");
		System.out.println("Inserisci la marca della Batmobile:");
		String marca = scanner.nextLine();
		System.out.println("Inserisci il modello della Batmobile:");
		String modello = scanner.nextLine();
		System.out.println("Inserisci la targa della Batmobile:");
		String targa = scanner.nextLine();
		System.out.println("Inserisci il nome della Batmobile:");
		String nome = scanner.nextLine();
		System.out.println("Inserisci gli accessori della Batmobile:");
		String accessori = scanner.nextLine();

		Batmobile nuovaBatmobile = new Batmobile(marca, modello, targa, nome, accessori);
		listaBatmobili.put(targa, nuovaBatmobile);

		salvaFileBatmobili(listaBatmobili);

		System.out.println("Batmobile aggiunta con successo al parco Batmobili.");
		scanner.close();

	}

	public void rimuoviBatmobile(HashMap<String, Batmobile> listaBatmobili) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci la targa della Batmobile da rimuovere:");
		String targaBatmobile = scanner.nextLine().trim();
		if (listaBatmobili.containsKey(targaBatmobile)) {
			listaBatmobili.remove(targaBatmobile);
			System.out.println("Batmobile rimossa con successo.");
			salvaFileBatmobili(listaBatmobili);
		} else {
			System.out.println("Nessuna Batmobile trovata con la targa inserita.");
		}
		scanner.close();
	}

}
