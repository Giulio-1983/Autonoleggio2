import java.awt.print.Book;
import java.io.*;
import java.util.*;
import java.io.File;

public class GestioneAutonoleggio {
    ConsoleManage cm = new ConsoleManage();
    private Map<String, AutoNoleggiabile> parcoAuto = new HashMap<>();
    private Map<String, Utente> listaUtenti = new HashMap<>();
    private Map<String, Batmobile> listaBatmobili = new HashMap<>();


    // private final String fileUtenti = "src\\file\\utenti.txt";

    private final String fileUtenti = "src" + File.separator + "file" + File.separator + "utenti.txt";
    private final String fileAuto = "src" + File.separator + "file" + File.separator + "auto.txt";
    private final String fileBatmoboli = "src" + File.separator + "file" + File.separator + "batmobili.txt";

    public Utente login() {
        boolean isTrovato = false;
        Utente utenteAttivo = null;
        String mail = null;
        String[] mailArr = cm.giveMail("Inserisci mail", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        if (mailArr[0].equals("1")) mail = mailArr[1];
        String psw = cm.dammiPassword("Inserisci password", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        for (Utente utente : listaUtenti.values()) {
            if (utente.getEmail().equals(mail) && utente.getPassword().equals(psw)) {
                System.out.println("Ciao, " + utente.getNome() + "!");
                utenteAttivo = utente;
                isTrovato = true;
                break;
            }
        }
        return utenteAttivo;
    }

    public void caricaFileAuto() {
        String linea;
        try {
            BufferedReader breader = new BufferedReader(new FileReader(fileAuto));
            while ((linea = breader.readLine()) != null) {
                String[] datiAuto = linea.split(",");
                if (datiAuto.length == 7) {
                    boolean isDisponibile;
                    AutoNoleggiabile autoNoleggiabile = new AutoNoleggiabile(datiAuto[1].trim(), datiAuto[2].trim(), datiAuto[3].trim(), Boolean.valueOf(datiAuto[5].trim()), Double.parseDouble(datiAuto[5].trim()), datiAuto[6].trim());
                    parcoAuto.put(datiAuto[0], autoNoleggiabile);
                } else {
                    System.out.println("Inserimento in HashMap non è possibile");
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
                    System.out.println("Inserimento in HashMap non è possibile");
                }
            }
            breader.close();
        } catch (IOException e) {
            System.out.println("Problema di leggere da file");
        }
    }

    public void inputMappaAuto() {
        String[] marcaArr = cm.giveString("Inserisci marca", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String marca = null;
        if (marcaArr[0].equals("1")) marca = marcaArr[1];
        String[] modelloArr = cm.giveString("Inserisci marca", "Formato non valido, riprova", "Inserimento Non andato con successo", 3);
        String modello = null;
        if (modelloArr[0].equals("1")) modello = modelloArr[1];
        String targa = cm.dammiTarga("Inserisci targa", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        System.out.println("Inserisci costo orario");
        double costoOrario = cm.dammiDouble("Inserisci costo orario in formato: x.xx: ", "Formato non valido, riprova", "Inserimento Non andato con successo", "Inserimento andato con successo", 3);
        parcoAuto.put(targa, new AutoNoleggiabile(marca, modello, targa, true, costoOrario, null));

    }

    public void inputMappaUtenti() {
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
        if (mail.contains("@autonoleggio.com")) {
            ruolo = Ruoli.MANAGER;
        } else {
            ruolo = Ruoli.CLIENTE;
        }
        listaUtenti.put(mail, new Utente(nome, cognome, mail, psw, ruolo));

    }

    public void salvaFileAuto() {
        String linea;
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileAuto));
            for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
                linea = entry.getKey() + "," + entry.getValue() + "\n";
                br.write(linea);
            }
            br.close();
            System.out.println("Il file è stato aggiornato");
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void salvaFileUtenti() {
        inputMappaUtenti();
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

    public void caricaFileBatmobili() {

    }

    public void salvaFileBatmobili() {

    }

    public void cercaAutoCostoDisp() {


    }

    public void mostraAutoDisp() {
        int index=0;
        for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
            System.out.println(++index +". "+ entry.getValue().toString().substring(1) );
        }
    }

    public void cercaAutoMarcaDisp() {

    }

    public void mostraAuto() {


    }

    public void noleggia() {

    }

    public void rimuoviAuto() {

    }

    // public void aggiungiAuto() { //tutto in salvaFileAuto

    // }

    public void cambiaStatoAuto() {

    }

    public void stampaClienti() {

    }

    public void stampaBatmobili() {

    }

    public void aggiungiBatmobile() {

    }

    public void rimuoviBatmobile() {

    }


    public void stampaMenu() {
        caricaFileUtenti();
        caricaFileAuto();
        caricaFileBatmobili();
        Utente utente = null;
        OpzioniCliente opzioniCliente = null;
        OpzioniManager opzioniManager = null;
        Integer input = null;
        do {
            input = cm.registrazioneLogin();
            if (input == 1) {
                salvaFileUtenti();
            }
        } while (input != 2&& input != 0);
        if (input == 2) {
            utente = login();
            if (utente.getEmail().equals("iambatman@batman.com")) {
                Integer batmanInput = cm.stampaOpzioniBatman();
                switch (batmanInput) {
                    case 1:
                        aggiungiBatmobile();
                        break;
                    case 2:
                        rimuoviBatmobile();
                        break;
                    case 3:
                        stampaBatmobili();
                        break;
                    default:
                        System.out.println("Stai per uscire");
                        break;
                }
            }
            switch (utente.getRuolo()) {
                case CLIENTE:
                    opzioniCliente = cm.stampaOpzioniCliente();
                    if (opzioniCliente.equals(OpzioniCliente.CERCA_PER_COSTO)) cercaAutoCostoDisp();
                    if (opzioniCliente.equals(OpzioniCliente.CERCA_PER_MARCA_MODELLO)) cercaAutoMarcaDisp();
                    if (opzioniCliente.equals(OpzioniCliente.VEDI_LISTA)) mostraAutoDisp();
                    if (opzioniCliente.equals(OpzioniCliente.NOLEGGIA_AUTO)) noleggia();
                    break;
                case MANAGER:
                    opzioniManager = cm.stampaOpzioniManager();
                    if (opzioniManager.equals(OpzioniManager.CERCA_PER_COSTO)) cercaAutoCostoDisp();
                    if (opzioniManager.equals(OpzioniManager.CERCA_PER_MARCA_MODELLO)) cercaAutoMarcaDisp();
                    if (opzioniManager.equals(OpzioniManager.VEDI_LISTA)) mostraAutoDisp();
                    if (opzioniManager.equals(OpzioniManager.NOLEGGIA_AUTO)) noleggia();
                    if (opzioniManager.equals(OpzioniManager.AGGIUNGI_AUTO)) salvaFileAuto();
                    if (opzioniManager.equals(OpzioniManager.RIMUOVI_AUTO)) rimuoviAuto();
                    if (opzioniManager.equals(OpzioniManager.CAMBIA_STATO_AUTO)) cambiaStatoAuto();
                    if (opzioniManager.equals(OpzioniManager.STAMPA_LISTA_AUTO)) mostraAuto();
                    if (opzioniManager.equals(OpzioniManager.STAMPA_LISTA_CLIENTI)) stampaClienti();
                    break;
                default:
                    System.out.println("Stai per uscire");
                    break;
            }
        } else {
            System.out.println("Arrivederci!");

        }


    }
}


