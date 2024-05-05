import enums.OpzioniBatman;
import enums.OpzioniCliente;
import enums.OpzioniManager;
import enums.Ruoli;

import java.io.*;
import java.sql.SQLOutput;
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
    private Map<Integer, NoleggioStorico> autoNoleggate = new HashMap<>();
    private Map<String, AutoNoleggiabile> autoDispFuturo = new HashMap<>();
    private Map<String, Utente> listaUtenti = new HashMap<>();
    private Map<String, Batmobile> listaBatmobili = new HashMap<>();

    private Utente utenteAttivo = null;


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
        String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
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
        } else if (mail.equals("Bruce@batcaverna.bat")) {
            ruolo = Ruoli.BATMAN;
        } else {
            ruolo = Ruoli.CLIENTE;
        }
        //controllo se esiste mail nella lista
        if (listaUtenti.containsKey(mail)) {
            System.out.println("Utente con email inserito è già presente in listaUtenti");
        } else {
            listaUtenti.put(mail, new Utente(nome, cognome, mail, psw, ruolo));
            System.out.println("Utente aggiunto nella lista utenti: ");
            salvaFileUtenti();
        }
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
            System.out.println("Il file auto è stato aggiornato");
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
            System.out.println("Il file utenti è stato aggiornato");
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
            System.out.println("Il file autonoleggiate è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void cercaAutoCostoDisp() {
        autoDispFuturo.clear();
        cercaAutoDispFuturo();
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
        int count = 0;
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
                    count++;

                }
            }
            if (count == 0) System.out.println("Non ci sono auto disponibili con parametri cercati");
        } else {
            System.out.println("Non possibile mostrare auto, il parcoAuto è vuoto");
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
        String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Map.Entry<String, AutoNoleggiabile> entry : autoDispFuturo.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(targa)) {
                autoNoleggiabile = entry.getValue();
                break;
            }
        }
        return autoNoleggiabile;
    }

    //popola hashMap disponibile per le date e stampa lista
    public LocalDateTime[] cercaAutoDispFuturo() {
        LocalDateTime[] inizioFineArr = controllaData();
        if (inizioFineArr != null) {
            LocalDateTime inizioDataOra = inizioFineArr[0];
            LocalDateTime fineDataOra = inizioFineArr[1];
            System.out.println("Elenco di auto disponibili per le date inserite");
            for (Map.Entry<String, AutoNoleggiabile> entryP : parcoAuto.entrySet()) {
                boolean autoDisponibile = true;
                for (Map.Entry<Integer, NoleggioStorico> entryN : autoNoleggate.entrySet()) {
                    if (entryP.getKey().equalsIgnoreCase(entryN.getValue().getTarga())) {
                        // se auto occupata
                        if (!(inizioDataOra.isAfter(entryN.getValue().getFineNoleggio()) ||
                                fineDataOra.isBefore(entryN.getValue().getInizioNoleggio()))) {
                            autoDisponibile = false;
                            break;
                        }
                    }
                }
                if (autoDisponibile) {
                    autoDispFuturo.put(entryP.getKey(), entryP.getValue());
                }
            }
            for (Map.Entry<String, AutoNoleggiabile> entryP : autoDispFuturo.entrySet()) {
                System.out.println(entryP.getKey() + " -> " + entryP.getValue());
            }
        }
        return inizioFineArr;
    }


    //metodo di suppotro per chiamare in nolegggia
    public LocalDateTime[] controllaData() {
        LocalTime minHour = null;
        LocalDate dateInizio = null;
        LocalTime timeInizio = null;
        LocalDate dateFine = null;
        LocalTime timeFine = null;

        LocalDateTime inizioDataOra = null;
        LocalDateTime fineDataOra = null;
        LocalDateTime[] inizioFineArr = new LocalDateTime[2];

        // Start data ora
        dateInizio = cm.dammiDatainFuturo("Inserisci la data inizio noleggio : dd-MM-yyyy", "Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo", 3);
        if (dateInizio.equals(LocalDate.now())) {
            minHour = LocalTime.now();
        } else {
            minHour = LocalTime.of(0, 0);
        }
        timeInizio = cm.dammiOraInFuturo("Inserisci un orario del inizio noleggio (HH:mm)", "Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con successo", 3, minHour);
        inizioDataOra = dateInizio.atTime(timeInizio);

        // Fine data ora
        dateFine = cm.dammiDatainFuturo("Inserisci la data fine noleggio : dd-MM-yyyy", "Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo", 3);
        if (dateFine.equals(LocalDate.now())) {
            minHour = LocalTime.now();
        } else {
            minHour = LocalTime.of(0, 0);
        }
        timeFine = cm.dammiOraInFuturo("Inserisci un orario del fine noleggio (HH:mm)", "Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con successo", 3, minHour);
        fineDataOra = dateFine.atTime(timeFine).truncatedTo(ChronoUnit.MINUTES);

        // Controllo dataOra inizio < fine
        if (inizioDataOra.isAfter(fineDataOra)) {
            System.out.println("La data e ora di inizio non possono essere maggiori della data e ora di fine");
            return null;
        }

        // Controllo se data e Ora non sono nel passato
        if (inizioDataOra.isBefore(LocalDateTime.now()) || fineDataOra.isBefore(LocalDateTime.now())) {
            System.out.println("La data e ora di inizio e fine devono essere nel futuro");
            return null;
        }

        inizioFineArr[0] = inizioDataOra;
        inizioFineArr[1] = fineDataOra;
        return inizioFineArr;
    }


    //metodo di supporto da chiamare in restuituisci Auto se restituisci prima o dopo
    public NoleggioStorico calcolaCosto(NoleggioStorico noleggioStorico) {
        LocalTime minHour = null;
        LocalDate dateFine = null;
        LocalTime timeFine = null;
        LocalDateTime fineDataOra = null;
        Duration durata = null;
        Double costo = null;
        NoleggioStorico noleggioStoricoNew = null;
        AutoNoleggiabile autoNoleggiabile = parcoAuto.get(noleggioStorico.getTarga());
        //fine data ora
        dateFine = cm.dammiDatainFuturo("Inserisci la data fine noleggio : dd-MM-yyyy", "Non è stata riconosciuta come data", "Non è stata inserita una data", "Data inserita con successo", 3);
        if (dateFine.equals(LocalDate.now())) {
            minHour = LocalTime.now();
        } else {
            minHour = LocalTime.of(0, 0);
        }
        timeFine = cm.dammiOraInFuturo("Inserisci un orario del fine noleggio (HH:mm)", "Non è stato riscontrato come orario", "Non è stato inserito un orario", "Ora inserita con sucesso", 3, minHour);
        fineDataOra = dateFine.atTime(timeFine).truncatedTo(ChronoUnit.MINUTES);
        // ricaalcolo durata e costo
        if (fineDataOra != null) {
            durata = Duration.between(noleggioStorico.getInizioNoleggio(), fineDataOra);
            costo = autoNoleggiabile.getCostoOrario() * durata.toHours();
            noleggioStorico.setFineNoleggio(fineDataOra.truncatedTo(ChronoUnit.MINUTES));
            noleggioStorico.setSommaPagata(costo);
        }
        return noleggioStorico;
    }

    //vengono chiamati mostraAutoDisp e calcolaCosto e cercaAutoPErTarga
    public void noleggia() {
        //inserimento data return boolean se giusta
        Double costo = null;
        Duration durata = null;
        autoDispFuturo.clear();
        LocalDateTime[] inizioFineArr = cercaAutoDispFuturo();
        LocalDateTime inizioDataOra = null;
        LocalDateTime fineDataOra = null;
        NoleggioStorico noleggioStorico = null;
        //     cercaAutoDispFuturo();
        if (inizioFineArr != null) {
            inizioDataOra = inizioFineArr[0];
            fineDataOra = inizioFineArr[1];
            AutoNoleggiabile autoNoleggiabile = cercaAutoPerTarga();
            durata = Duration.between(inizioDataOra, fineDataOra);
            if (autoNoleggiabile != null) {
                costo = autoNoleggiabile.getCostoOrario() * durata.toHours();
                if (costo != null) {
                    noleggioStorico = new NoleggioStorico(autoNoleggiabile.getTarga(), utenteAttivo.getEmail(), inizioDataOra, fineDataOra, costo);
                    autoNoleggate.put(NoleggioStorico.getNumFattura(), noleggioStorico);
                  //  autoNoleggiabile.setDisponibile(false);
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

    }

    //viene chiamato metodo di supporto cercaNoleggioPerFattura
    public void restituisciAuto() {
        NoleggioStorico noleggioStoricoOld = cercaNoleggioPerFattura();
        NoleggioStorico noleggioStoricoNew = null;
        //   AutoNoleggiabile autoNoleggiabile = parcoAuto.get(noleggioStoricoOld.getTarga());
        //   System.out.println("Auto: " + autoNoleggiabile);
        if (noleggioStoricoOld != null) {
            noleggioStoricoNew = calcolaCosto(noleggioStoricoOld);
            noleggioStoricoOld.setFineNoleggio(noleggioStoricoNew.getFineNoleggio());
            noleggioStoricoOld.setSommaPagata(noleggioStoricoNew.getSommaPagata());
            salvaFileAutoNoleggiate();
            salvaFileAuto();
            System.out.println("Auto restituita con successo");

        } else {
            System.out.println("Auto non trovata");
        }
    }


    public void rimuoviAuto() {
        String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
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


    //metodo di supporto per chiamare in restituisciAuto
    public NoleggioStorico cercaNoleggioPerFattura() {
        Integer numFatturaInput = null;
        NoleggioStorico noleggioStorico = null;
        //inserimento di numero
        int[] numFatturaArr = cm.giveInt("Inserisci il numero di fattura", "Non è stato riconosciuto come numero",
                "Non è stato inserito un numero", 3);
        if (numFatturaArr[0] == 1) {
            numFatturaInput = numFatturaArr[1];
            System.out.println("il numero inserito è: " + numFatturaInput);
        }
        if (numFatturaArr != null) {
            //cerco in lista autoNoleggiate
            for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
                if (entry.getKey().equals(numFatturaInput)) {
                    noleggioStorico = entry.getValue();
                    System.out.println("Noleggio trovato: " + entry.getValue());
                }
            }
        }
        return noleggioStorico;
    }


    public void vediNoleggiDelCliente() {
        System.out.println("Tuoi noleggi attivi: ");
        int count = 0;
        for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
            if (entry.getValue().getAffidatarioEmail().equalsIgnoreCase(utenteAttivo.getEmail())) {
                System.out.println("Numero fattura: " + entry.getKey() + ", Noleggio: " + entry.getValue());
                count++;
            }
        }
        if (count == 0)
            System.out.println("Noi hai noleggi attivi");
    }

    //vengono chiamati metodi di supporto vediNoleggiDelCliente e cercaNoleggioPerFattura
    public void annullaNoleggio() {
        vediNoleggiDelCliente();
        NoleggioStorico noleggioStorico = cercaNoleggioPerFattura();
        if (noleggioStorico != null &&
                (utenteAttivo.getEmail().equalsIgnoreCase(noleggioStorico.getAffidatarioEmail()))
                || utenteAttivo.getRuolo().equals(Ruoli.MANAGER)) {
            for (Map.Entry<Integer, NoleggioStorico> entry : autoNoleggate.entrySet()) {
                if (entry.getValue() == noleggioStorico) {
                   // parcoAuto.get(noleggioStorico.getTarga()).setDisponibile(true);
                    salvaFileAuto();
                    autoNoleggate.remove(entry.getKey());
                    salvaFileAutoNoleggiate();
                    System.out.println("Noleggio di auto " + noleggioStorico.getTarga() + " annullato");
                    break;
                }
            }
        } else {
            System.out.println("Noleggio non trovato");
        }
    }

    public void cambiaStatoAuto() {
        String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
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
        String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
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
        String targa = cm.dammiTarga("Inserisci targa: da 4 a 8 caratteri alfanumerici", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
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
                        if (opzioniCliente.equals(OpzioniCliente.VEDI_PROPRI_NOLEGGI)) vediNoleggiDelCliente();
                        if (opzioniCliente.equals(OpzioniCliente.ANNULLA_NOLEGGIO)) annullaNoleggio();
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
                        if (opzioniManager.equals(OpzioniManager.ANNULLA_NOLEGGIO)) annullaNoleggio();
                        if (opzioniManager.equals(OpzioniManager.VEDI_PROPRI_NOLEGGI)) vediNoleggiDelCliente();
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


