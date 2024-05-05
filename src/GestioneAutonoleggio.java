import enums.OpzioniBatman;
import enums.OpzioniCliente;
import enums.OpzioniManager;
import enums.Ruoli;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.File;

public class GestioneAutonoleggio {

    ConsoleManage cm = new ConsoleManage();
    private Map<String, AutoNoleggiabile> parcoAuto = new HashMap<>();
    private Map<String, Utente> listaUtenti = new HashMap<>();
    private Map<String, Batmobile> listaBatmobili = new HashMap<>();
    private Map<Integer, NoleggioStorico> autoNoleggate = new HashMap<>();
    private Utente utenteAttivo=null;


    private final String fileUtenti = "src" + File.separator + "file" + File.separator + "utenti.txt";
    private final String fileAuto = "src" + File.separator + "file" + File.separator + "auto.txt";
    private final String fileBatmoboli = "src" + File.separator + "file" + File.separator + "batmobili.txt";
    private final String fileNoleggioStorico = "src" + File.separator + "file" + File.separator + "noleggioStorico.txt";

    public void login() {
        boolean isTrovato = false;
        // Utente utenteAttivo = null;
        String mail = null;
        String[] mailArr = cm.giveMail("Inserisci mail", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        if (mailArr[0].equals("1")) mail = mailArr[1];
        String psw = cm.dammiPassword("Inserisci password", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Utente ut : listaUtenti.values()) {
            if (ut.getEmail().equals(mail.trim()) && ut.getPassword().equals(psw.trim())) {
                utenteAttivo = ut;
                break;
            }
        }
        if (utenteAttivo == null) {
            System.out.println("Utente non trovato");
        } else {
            System.out.println("Ciao, " + utenteAttivo.getNome() + "!");
        }

    }

    public void caricaFileAuto() {
        String linea;
        try {
            BufferedReader breader = new BufferedReader(new FileReader(fileAuto));
            while ((linea = breader.readLine()) != null) {
                String[] datiAuto = linea.split(",");
                if (datiAuto.length == 6) {
                    AutoNoleggiabile autoNoleggiabile = new AutoNoleggiabile(datiAuto[1].trim(), datiAuto[2].trim(), datiAuto[3].trim(), Boolean.valueOf(datiAuto[4].trim()), Double.parseDouble(datiAuto[5].trim()));
                    parcoAuto.put(datiAuto[0], autoNoleggiabile);
                } else {
                    System.out.println("Inserimento in HashMap parcoAuto non è possibile");
                }
            }
            breader.close();
        } catch (IOException e) {
            System.out.println("Problema di leggere da file");
        }
    }

    public void caricaFileNoleggio() {
        String linea;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTimeInizio = null;
        LocalDateTime dateTimeFine = null;
        try {
            BufferedReader breader = new BufferedReader(new FileReader(fileNoleggioStorico));
            while ((linea = breader.readLine()) != null) {
                String[] datiNoleggio = linea.split(",");
                if (datiNoleggio.length == 6) {
                    if (datiNoleggio[3] != null)
                        dateTimeInizio = LocalDateTime.parse(datiNoleggio[3], formatter);
                    if (datiNoleggio[4] != null)
                        dateTimeFine = LocalDateTime.parse(datiNoleggio[4], formatter);

                    NoleggioStorico noleggioStorico = new NoleggioStorico(datiNoleggio[1].trim(), datiNoleggio[2].trim(), dateTimeInizio, dateTimeFine, Double.parseDouble(datiNoleggio[5].trim()));

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

    public void caricaFileUtenti() {
        String linea;
        try {
            BufferedReader breader = new BufferedReader(new FileReader(fileUtenti));
            while ((linea = breader.readLine()) != null) {
                String[] datiUtenti = linea.split(",");
                if (datiUtenti.length == 6) {
                    Utente utente = new Utente(datiUtenti[1].trim(), datiUtenti[2].trim(), datiUtenti[3].trim(), datiUtenti[4].trim(), Ruoli.valueOf(datiUtenti[5].trim()));
                    listaUtenti.put(datiUtenti[0].trim(), utente);
                } else {
                    System.out.println("Inserimento in HashMap listaUtenti non è possibile");
                }
            }
            breader.close();
        } catch (IOException e) {
            System.out.println("Problema di leggere da file");
        }
    }

    public void caricaFileBatmobili() {
        String linea;
        try {
            BufferedReader breader = new BufferedReader(new FileReader(fileBatmoboli));
            while ((linea = breader.readLine()) != null) {
                String[] datiAuto = linea.split(",");
                if (datiAuto.length == 6) {
                    Batmobile batmobile = new Batmobile(datiAuto[1].trim(), datiAuto[2].trim(), datiAuto[3].trim(), datiAuto[4].trim(), datiAuto[5].trim());
                    listaBatmobili.put(datiAuto[0], batmobile);
                } else {
                    System.out.println("Inserimento in HashMap listaBatmobili non è possibile");
                }
            }
            breader.close();
        } catch (IOException e) {
            System.out.println("Problema di leggere da file");
        }

    }

    //popola hashmap e chiama metodo salvaFile
    public void aggiungiAuto() {
        String[] marcaArr = cm.giveString("Inserisci marca", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String marca = null;
        if (marcaArr[0].equals("1")) marca = marcaArr[1];
        System.out.println("La marca inserita è: " + marca);
        String[] modelloArr = cm.giveString("Inserisci modello", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String modello = null;
        if (modelloArr[0].equals("1")) modello = modelloArr[1];
        System.out.println("Il modello inserito è: " + modello);
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        System.out.println("Inserisci costo orario");
        double costoOrario = cm.dammiDouble("Inserisci costo orario in formato: x.xx: ", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        //controllo se esiste targa nella lista
        for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
            if (entry.getValue().getTarga().equalsIgnoreCase(targa)) {
                System.out.println("Targa inserita è gia presente in parco auto");
            } else {
                parcoAuto.put(targa, new AutoNoleggiabile(marca, modello, targa, true, costoOrario));
                System.out.println("Auto aggiunto in parco auto: ");
            }
        }
        salvaFileAuto();
    }

    //popola hashmap e chiama metodo salvaFile
    public void aggiungiUtente() {
        String[] nomeArr = cm.giveString("Inserisci nome", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String nome = null;
        if (nomeArr[0].equals("1")) nome = nomeArr[1];
        String[] cognomeArr = cm.giveString("Inserisci cognome", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String cognome = null;
        if (cognomeArr[0].equals("1")) cognome = cognomeArr[1];
        String psw = cm.dammiPassword("Inserisci password", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        String mail = null;
        String[] mailArr = cm.giveMail("Inserisci mail", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        if (mailArr[0].equals("1")) mail = mailArr[1];
        Ruoli ruolo = null;
        //batman è gia inserito nella listaUtenti
        if (mail.contains("@autonoleggio.com")) {
            ruolo = Ruoli.MANAGER;
        } else {
            ruolo = Ruoli.CLIENTE;
        }
        //controllo se esiste mail nella lista
        for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
            if (entry.getValue().getEmail().equalsIgnoreCase(mail)) {
                System.out.println("Utente con email inserito è gia presente in listaUtenti");
            } else {
                listaUtenti.put(mail, new Utente(nome, cognome, mail, psw, ruolo));
                System.out.println("Utente aggiunto nella lista utenti: ");
            }
        }
        salvaFileUtenti();
    }

    //metodo di supporto -da chiamare in aggiungiAuto
    public void salvaFileAuto() {
        String linea;
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileAuto));
            for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
                AutoNoleggiabile auto = entry.getValue();
                linea = entry.getKey() + "," + auto.getMarca() + "," + auto.getModello() + "," + auto.getTarga() + "," + auto.isDisponibile() + "," + auto.getCostoOrario() + "\n";
                br.write(linea);
            }
            br.close();
            System.out.println("Il file è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    //metodo di supporto -da chiamare in aggiungiUtente
    public void salvaFileUtenti() {
        String linea;
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileUtenti));
            for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
                linea = entry.getKey() + "," + entry.getValue() + "\n";
                br.write(linea);
            }
            br.close();
            System.out.println("Il file è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //metodo di supporto -da chiamare in noleggia
    public void salvaFileAutoNoleggiate() {
        String linea;
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileNoleggioStorico));
            for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
                linea = entry.getKey() + "," + entry.getValue() + "\n";
                br.write(linea);
            }
            br.close();
            System.out.println("Il file è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void cercaAutoCostoDisp() {
        Double costo = cm.dammiDouble("Inserisci costo massimo", "Inserimenro errato, riprova", "Inserimento NON andato con successo", "Inserimento costo andato con successo", 3);
        int index = 0;
        if (costo != null && parcoAuto.size() > 0) {
            System.out.println("Lista di auto con costo orario <= " + costo + ": ");
            for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
                if (entry.getValue().getCostoOrario() <= costo && entry.getValue().isDisponibile()) {
                    System.out.println(++index + ". " + entry.getValue().toString().substring(1));
                } else {
                    System.out.println("Non ci sono auto disponobili per il costo cercato");
                }
            }
        } else {
            System.out.println("Costo cercato non definito, non possibile mostrare auto");
        }
    }

    public void mostraAutoDisp() {
        int index = 0;
        if (parcoAuto.size() > 0) {
            System.out.println("Auto disponibili");
            for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
                if (entry.getValue().isDisponibile())
                    System.out.println(++index + ". " + entry.getValue().toString().substring(1));
            }
        } else {
            System.out.println("Non ci sono auto disponibili");
        }

    }

    public void stampaAutoNoleggiate() {
        int index = 0;
        if (autoNoleggate.size() > 0) {
            System.out.println("Auto noleggiate");
            for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
                System.out.println(++index + ". " + entry.getValue().toString().substring(1));
            }
        } else {
            System.out.println("Non ci sono auto noleggiate");
        }
    }

    public void cercaAutoMarcaDisp() {
        String[] marcaArr = cm.giveString("Inserisci marca", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String marca = null;
        if (marcaArr[0].equals("1")) marca = marcaArr[1];
        String[] modelloArr = cm.giveString("Inserisci marca", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String modello = null;
        if (modelloArr[0].equals("1")) modello = modelloArr[1];
        int index = 0;
        if (parcoAuto.size() > 0) {
            for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
                if (entry.getValue().isDisponibile() &&
                        entry.getValue().getMarca().equalsIgnoreCase(marca) &&
                        entry.getValue().getModello().equalsIgnoreCase(modello)) {
                    System.out.println("Lista di auto con marca: " + marca + " e modello: " + modello);
                    System.out.println(++index + ". " + entry.getValue().toString().substring(1));

                } else {
                    System.out.println("Non ci sono auto disponibili con parametri cercati");
                }
            }
        } else {
            System.out.println("Non possibile mostrare auto");
        }

    }

    public void mostraAuto() {
        int index = 0;
        if (parcoAuto.size() > 0) {
            System.out.println("Lista completa di auto: ");
            for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
                System.out.println(++index + ". " + entry.getValue().toString().substring(1));
            }
        }
    }

    public AutoNoleggiabile cercaAutoPerTarga() {
        AutoNoleggiabile autoNoleggiabile = null;
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(targa)) {
                autoNoleggiabile = entry.getValue();
                break;
            }
        }
        return autoNoleggiabile;
    }


    //metodo di supporto da chiamare in noleggia e restuituisci Auto


    public NoleggioStorico calcolaCosto(AutoNoleggiabile autoNoleggiabile) {
        LocalDate dateInizio = null;
        LocalTime timeInizio = null;
        LocalDate dateFine = null;
        LocalTime timeFine = null;
        LocalDateTime inizioDataOra = null;
        LocalDateTime fineDataOra = null;
        Duration durata = null;
        Double costo = null;
        NoleggioStorico noleggioStorico = null;

        // se disponibile
        if (autoNoleggiabile != null && autoNoleggiabile.isDisponibile()) {
            // Start data ora
            dateInizio = cm.dammiData("Inserisci la data inizio noleggio : dd-MM-yyyy", "Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo", 3);
            LocalTime[] inizioTimeArr = cm.giveTime("Inserisci un orario del inizio noleggio (HH:mm)", "Non è stato riscontrato come orario", "Non è stato inserito un orario", 3);
            if (inizioTimeArr[0].equals(LocalTime.of(00, 01))) {
                timeInizio = inizioTimeArr[1];
                System.out.println("L'orario inserito è: " + timeInizio);
            }
            inizioDataOra = dateInizio.atTime(timeInizio);
            noleggioStorico = new NoleggioStorico(autoNoleggiabile.getTarga(), utenteAttivo.getEmail(), inizioDataOra, null, costo);
        } else if (autoNoleggiabile != null && !autoNoleggiabile.isDisponibile()) {
            // se non disponibile- prendo auto per targa
            for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
                if (entry.getValue().getTarga().equalsIgnoreCase(autoNoleggiabile.getTarga())) {
                    inizioDataOra = entry.getValue().getInizioNoleggio();
                    noleggioStorico = new NoleggioStorico(entry.getValue().getTarga().trim(), entry.getValue().getAffidatarioEmail().trim(), inizioDataOra,
                            entry.getValue().getFineNoleggio(), entry.getValue().getSommaPagata());
                    break;
                }
            }
        } else {
            System.out.println("Auto non trovata o non disponibile, impossibile calcolare il costo.");
        }

        //fine data ora
        dateFine = cm.dammiData("Inserisci la data fine noleggio : dd-MM-yyyy", "Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo", 3);
        LocalTime[] fineTimeArr = cm.giveTime("Inserisci un orario della fine di noleggio (HH:mm)", "Non è stato riscontrato come orario", "Non è stato inserito un orario", 3);
        if (fineTimeArr[0].equals(LocalTime.of(00, 01))) {
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeFine = fineTimeArr[1].truncatedTo(ChronoUnit.MINUTES);
            System.out.println("L'orario inserito è: " + timeFine);
        }
        fineDataOra = dateFine.atTime(timeFine).truncatedTo(ChronoUnit.MINUTES);


        // Calcolo durata e costo
        if (inizioDataOra != null && fineDataOra != null) {
            durata = Duration.between(inizioDataOra, fineDataOra);
            costo = autoNoleggiabile.getCostoOrario() * durata.toHours();
            noleggioStorico.setFineNoleggio(fineDataOra.truncatedTo(ChronoUnit.MINUTES));
            noleggioStorico.setSommaPagata(costo);
        }

        return noleggioStorico;
    }


    public void noleggia() {
        AutoNoleggiabile autoNoleggiabile = cercaAutoPerTarga();
        if (autoNoleggiabile != null) {
            NoleggioStorico noleggioStorico = calcolaCosto(autoNoleggiabile);
            Double costo = noleggioStorico.getSommaPagata();
            // Control if payment is made
            if (costo != null) {
                autoNoleggate.put(NoleggioStorico.getNumFattura(), noleggioStorico);
                autoNoleggiabile.setDisponibile(false);
                parcoAuto.put(autoNoleggiabile.getTarga(), autoNoleggiabile);
                salvaFileAutoNoleggiate();
                salvaFileAuto();
                System.out.println("Hai noleggiato auto: " + autoNoleggiabile.getMarca() + " " + autoNoleggiabile.getModello() + ", Costo: " + costo);
            } else {
                System.out.println("Noleggio NON è effettuato");
            }
        } else {
            System.out.println("Auto non trovata");
        }
    }


    public void rimuoviAuto() {
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(targa)) {
                parcoAuto.remove(entry.getKey());
                salvaFileAuto();
                System.out.println("Hai cancellato auto: " + entry.getValue().toString().substring(1));
            }
        }
        salvaFileAuto();
    }

    // public void aggiungiAuto() {} //tutto in salvaFileAuto

    public void restituisciAuto() {
        AutoNoleggiabile autoNoleggiabile = cercaAutoPerTarga();
        NoleggioStorico noleggioStorico=null;
        if (autoNoleggiabile != null && autoNoleggiabile.isDisponibile() == false) {
           noleggioStorico = calcolaCosto(autoNoleggiabile);
          //  NoleggioStorico noleggioStoricoOld=null;

            for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
                if (entry.getValue().getTarga().equals(autoNoleggiabile.getTarga())) {
                    NoleggioStorico noleggioStoricoOld = entry.getValue();
                    noleggioStoricoOld.setFineNoleggio(noleggioStorico.getFineNoleggio());
                    noleggioStoricoOld.setSommaPagata(noleggioStorico.getSommaPagata());
                    break;
                }
            }
                autoNoleggiabile.setDisponibile(true);
                salvaFileAutoNoleggiate();
                salvaFileAuto();
                System.out.println("Auto restituita con successo");

        } else {
            System.out.println("Auto non trovata.");
        }
    }


    public void cambiaStatoAuto() {
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(targa)) {
                entry.getValue().setDisponibile(!entry.getValue().isDisponibile());
                salvaFileAuto();
                System.out.println("Hai cancellato auto: " + entry.getValue().toString().substring(1));
            }
        }
    }

    public void stampaUtenti() {
        int index = 0;
        if (listaUtenti.size() > 0) {
            for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
                if (!entry.getValue().getRuolo().equals(Ruoli.BATMAN))
                    System.out.println(++index + ". " + entry.getValue());
            }
        } else {
            System.out.println("Non ci sono Utenti");
        }
    }

    public void stampaClienti() {
        int index = 0;
        if (listaUtenti.size() > 0) {
            for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
                if (!entry.getValue().getRuolo().equals(Ruoli.BATMAN) && !entry.getValue().getRuolo().equals(Ruoli.MANAGER))
                    System.out.println(++index + ". " + entry.getValue());
            }
        } else {
            System.out.println("Non ci sono Clienti");
        }
    }


    public void stampaBatmobili() {

        int index = 0;
        if (listaBatmobili.size() > 0) {
            System.out.println("Batmobili");
            for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
                System.out.println(++index + ". " + entry.getValue().toString());
            }

        } else {
            System.out.println("Non ci sono batmobili");
        }
    }

    public void aggiungiBatmobile() {
        String[] marcaArr = cm.giveString("Inserisci marca", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String marca = null;
        if (marcaArr[0].equals("1")) marca = marcaArr[1];
        System.out.println("La marca inserita è: " + marca);
        String[] modelloArr = cm.giveString("Inserisci modello", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String modello = null;
        if (modelloArr[0].equals("1")) modello = modelloArr[1];
        System.out.println("Il modello inserito è: " + modello);
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        String[] nomeArr = cm.giveString("Inserisci nome", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String nome = null;
        if (nomeArr[0].equals("1")) nome = nomeArr[1];
        System.out.println("Il nomr inserito è: " + nome);
        String[] accessoriArr = cm.giveString("Inserisci accessori", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String accessori = null;
        if (accessoriArr[0].equals("1")) accessori = accessoriArr[1];
        System.out.println("hai inserito accessori: " + accessori);
        //controllo se esiste targa nella lista
        for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
            if (entry.getValue().getTarga().equalsIgnoreCase(targa)) {
                System.out.println("Targa inserita è gia presente nella lsta batmobili");
            } else {
                listaBatmobili.put(targa, new Batmobile(marca, modello, targa, nome, accessori));
                System.out.println("Auto aggiunto in lista batmobili ");
            }
        }
        salvaFileBatmobili();

    }

    public void rimuoviBatmobile() {
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(targa)) {
                listaBatmobili.remove(entry.getKey());
                salvaFileBatmobili();
                System.out.println("Hai cancellato auto: " + entry.getValue().toString().substring(1));
            }
        }
        salvaFileBatmobili();
    }


    public void salvaFileBatmobili() {
        String linea;
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileBatmoboli));
            for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
                linea = entry.getKey() + "," + entry.getValue() + "\n";
                br.write(linea);
            }
            br.close();
            System.out.println("Il file è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void stampaMenu() {
        caricaFileUtenti();
        caricaFileAuto();
        caricaFileBatmobili();
        caricaFileNoleggio();
        OpzioniCliente opzioniCliente = null;
        OpzioniManager opzioniManager = null;
        OpzioniBatman opzioniBatman = null;
        Integer input = null;
        do {
            input = cm.registrazioneLogin();
            if (input == 1) {
                aggiungiUtente();
            }
        } while (input != 2 && input != 0);
        if (input == 2) {
            login();
            switch (utenteAttivo.getRuolo()) {
                case CLIENTE:
                    do {
                        opzioniCliente = cm.stampaOpzioniCliente();

                        if (opzioniCliente.equals(OpzioniCliente.CERCA_PER_COSTO)) cercaAutoCostoDisp();
                        if (opzioniCliente.equals(OpzioniCliente.CERCA_PER_MARCA_MODELLO)) cercaAutoMarcaDisp();
                        if (opzioniCliente.equals(OpzioniCliente.VEDI_LISTA)) mostraAutoDisp();
                        if (opzioniCliente.equals(OpzioniCliente.NOLEGGIA_AUTO)) noleggia();
                    } while (!opzioniCliente.equals(OpzioniCliente.ESCI));

                    break;
                case MANAGER:
                    do {
                        opzioniManager = cm.stampaOpzioniManager();

                        if (opzioniManager.equals(OpzioniManager.CERCA_PER_COSTO)) cercaAutoCostoDisp();
                        if (opzioniManager.equals(OpzioniManager.CERCA_PER_MARCA_MODELLO)) cercaAutoMarcaDisp();
                        if (opzioniManager.equals(OpzioniManager.VEDI_LISTA)) mostraAutoDisp();
                        if (opzioniManager.equals(OpzioniManager.NOLEGGIA_AUTO)) noleggia();
                        if (opzioniManager.equals(OpzioniManager.AGGIUNGI_AUTO)) aggiungiAuto();
                        if (opzioniManager.equals(OpzioniManager.RIMUOVI_AUTO)) rimuoviAuto();
                        if (opzioniManager.equals(OpzioniManager.CAMBIA_STATO_AUTO)) cambiaStatoAuto();
                        if (opzioniManager.equals(OpzioniManager.STAMPA_LISTA_AUTO)) mostraAuto();
                        if (opzioniManager.equals(OpzioniManager.STAMPA_LISTA_CLIENTI)) stampaClienti();
                        if (opzioniManager.equals(OpzioniManager.STAMPA_LISTA_UTENTI)) stampaUtenti();
                        if (opzioniManager.equals(OpzioniManager.VEDI_AUTO_NOLEGGIATE)) stampaAutoNoleggiate();
                        if (opzioniManager.equals(OpzioniManager.RESTITUISCI_AUTO)) restituisciAuto();
                    } while (!opzioniManager.equals(OpzioniManager.ESCI));

                    break;
                case BATMAN:
                    do {
                        opzioniBatman = cm.stampaOpzioniBatman();
                        if (opzioniBatman.equals(OpzioniBatman.AGGIUNGI_BATMOBILE)) aggiungiBatmobile();
                        if (opzioniBatman.equals(OpzioniBatman.RIMUOVI_BATMOBILE)) rimuoviBatmobile();
                        if (opzioniBatman.equals(OpzioniBatman.VEDI_LISTA_BATMOBILI)) stampaBatmobili();
                    } while (!opzioniBatman.equals(OpzioniBatman.ESCI));

                default:
                    System.out.println("Stai per uscire");
                    break;
            }
        } else {
            System.out.println("Arrivederci!");
        }
        cm.closeScanner();
    }

}//


