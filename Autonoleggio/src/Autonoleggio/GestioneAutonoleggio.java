package Autonoleggio;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;


public class GestioneAutonoleggio {
  private HashMap<String, AutoNoleggiabile> parcoAuto= new HashMap<String, AutoNoleggiabile>();
  private HashMap<String, Utente> listaUtenti= new HashMap<String, Utente>();
  private HashMap<String, Batmobile> listaBatmobili= new HashMap<String, Batmobile>();

    public GestioneAutonoleggio(HashMap<String, AutoNoleggiabile> parcoAuto, HashMap<String, Utente> listaUtenti,
      HashMap<String, Batmobile> listaBatmobili) {
      super();
      this.parcoAuto = parcoAuto;
      this.listaUtenti = listaUtenti;
      this.listaBatmobili = listaBatmobili;
    }

    

    public void login() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Sei un utente registrato? \n1. Si \n2. No");
      int scelta = scanner.nextInt();

      switch (scelta) {
        case 1:
          effettuaLogin();
          break;
        case 2:
          registraUtente();
          break;
        default:
          System.out.println("Scelta non valida");
          break;
      }
    }

    

    private void effettuaLogin() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Inserisci la tua email");
      String email = scanner.nextLine();

      if (!listaUtenti.containsKey(email)) {
        System.out.println("Email non trovata. Effettua la registrazione");
        scanner.close();
        return;
      }

      Utente utente = listaUtenti.get(email);
      
      System.out.println("Inserisci la tua password");
      String password = scanner.nextLine();
      int count = 0;

      while ((!utente.getPassword().equals(password)) && count<=3) {
        System.out.println("Password errata. Inserisci la tua password" + (3-count) + "tentativi rimasti");
        password = scanner.nextLine();
        count++;
        if (count == 3) {
          System.out.println("Hai esaurito il numero di tentativi. Per il reset della password contatta amministrazione@autonoleggio.it");
          return;
        }
      }
      System.out.println("Login effettuato con successo");  
      scanner.close();
    }

    private void registraUtente() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Inserisci il tuo nome");  // magari usare metodo di ConsoleManage
      String nome = scanner.nextLine();

      System.out.println("Inserisci il tuo cognome");  // magari usare metodo di ConsoleManage
      String cognome = scanner.nextLine();
      
      System.out.println("Inserisci la tua email");  // magari usare metodo di ConsoleManage
      String email = scanner.nextLine();

      if (listaUtenti.containsKey(email)) {
        System.out.println("Email già registrata. Effettua il login");
        scanner.close();
        return;
      }

      System.out.println("Inserisci la tua password");
      String password = scanner.nextLine();
      
      String ruolo = "utente";

      Utente nuovoUtente = new Utente(nome, cognome, email, password, ruolo);
      listaUtenti.put(email, nuovoUtente);
      salvaFileUtenti();

      System.out.println("Registrazione effettuata con successo");

      scanner.close();
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
         if (datiAuto.length == 6) {
             String marca = datiAuto[0].trim();
             String modello = datiAuto[1].trim();
             String targa = datiAuto[2].trim();
             boolean isDisponibile = Boolean.parseBoolean(datiAuto[3].trim());
             double costoOrario = Double.parseDouble(datiAuto[4].trim());
             String affidatario = datiAuto[5];           
           
             // Creo l'oggetto e lo aggiungo all'HashMap:
             AutoNoleggiabile nuovaAuto = new AutoNoleggiabile(marca, modello, targa, isDisponibile, costoOrario, affidatario);
         
             parcoAuto.put(targa, nuovaAuto);
          }
        }
       br.close();
      } catch (IOException e) {
       e.printStackTrace();
      }
    }

    public void salvaFileAuto(HashMap<String, AutoNoleggiabile> parcoAuto) {
      BufferedWriter bw = null;
      try {
        bw= new BufferedWriter(new FileWriter("fileAuto.txt"));
          for (AutoNoleggiabile auto : parcoAuto.values()) {
              String linea = auto.getMarca() + "," + auto.getModello() + "," + auto.getTarga() + "," +
                             auto.isDisponibile() + "," + auto.getCostoOrario() + "," + auto.getAffidatario();
              bw.write(linea);
              bw.newLine();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }    
    }

    public void caricaFileUtenti(){
      try {
       File elencoUtenti = new File("elencoUtenti.txt");
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
             Utente nuovoUtente = new Utente(nome, cognome, email, password, ruolo);

             listaUtenti.put(email, nuovoUtente);
          }
        }
       br.close();
      } catch (IOException e) {
       e.printStackTrace();
      }
    
    }
    
    
    public void salvaFileUtenti() {
      BufferedWriter bw = null;
      try {
        bw = new BufferedWriter(new FileWriter("listaUtenti.txt"));
          for (Utente utente : listaUtenti.values()) {
              String linea = utente.getNome() + "," + utente.getCognome() + "," + utente.getEmail() + "," +
                             utente.getPassword() + "," + utente.getRuolo();
              bw.write(linea);
              bw.newLine();
          }
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
              String linea = batmobile.getMarca() + "," + batmobile.getModello() + "," + batmobile.getTarga() + "," + batmobile.getNome() + "," + batmobile.getAccessori();
              bw.write(linea);
              bw.newLine();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }    
    }
    
    public List<AutoNoleggiabile> cercaAutoCostoDisp(double prezzoMax) {
      Scanner scanner = new Scanner(System.in);
      List<AutoNoleggiabile> autoDisponibili = new ArrayList<AutoNoleggiabile>();
      System.out.println("Inserisci il prezzo orario massimo");
      prezzoMax=scanner.nextDouble();
      for (AutoNoleggiabile auto : parcoAuto.values()) {
        if (auto.isDisponibile()==true && auto.getCostoOrario()<=prezzoMax) {
          autoDisponibili.add(auto);       
        }
      }
      for (AutoNoleggiabile auto : autoDisponibili) {
        System.out.println(auto);
      }
      scanner.close();
      return autoDisponibili;
    }
  
    public List<AutoNoleggiabile> mostraAutoDisp() {
      List<AutoNoleggiabile> autoDisponibili = new ArrayList<AutoNoleggiabile>();
      for (AutoNoleggiabile auto : parcoAuto.values()) {
        if (auto.isDisponibile()==true) {
          autoDisponibili.add(auto);
          System.out.println(autoDisponibili);
        } 
        if (autoDisponibili.isEmpty()) {
          System.out.println("Non ci sono auto disponibili");
        }
      }
      return autoDisponibili;
    }
  
    public List<AutoNoleggiabile> cercaAutoMarcaDisp(String marcaSelezionata) {
      List<AutoNoleggiabile> autoDisponibiliMarca = new ArrayList<AutoNoleggiabile>();
      for (AutoNoleggiabile auto : parcoAuto.values()) {
        if (auto.isDisponibile()==true && auto.getMarca().equalsIgnoreCase(marcaSelezionata)) {
          autoDisponibiliMarca.add(auto);
          System.out.println(autoDisponibiliMarca);
        }
        if (autoDisponibiliMarca.isEmpty()) {
          System.out.println("Non ci sono auto disponibili");
        }
      } 
      return autoDisponibiliMarca;
    }
  
    public List<Automobile> mostraAuto() { // Restituisce tutto il parco auto, escluse le Batmobili
      List<Automobile> tuttoIlParcoAuto = new ArrayList<Automobile>();
      tuttoIlParcoAuto.addAll(parcoAuto.values());
      return tuttoIlParcoAuto;  
    }
  
    public void noleggia() {
      Scanner scanner = new Scanner(System.in);

      if (parcoAuto.isEmpty()) {
          System.out.println("Nessuna auto disponibile.");
          return;
      }

      // Chiedo all'utente di scegliere un'opzione
      System.out.println("Scegli un'opzione:");
      System.out.println("1. Visualizza tutte le auto disponibili");
      System.out.println("2. Filtra le auto disponibili per marca");
      System.out.println("3. Filtra le auto disponibili per prezzo");
      List<AutoNoleggiabile> listaFiltrata = new ArrayList<AutoNoleggiabile>();
      int opzione = scanner.nextInt();
      scanner.nextLine(); // Per il problema della riga mangiata
      switch (opzione) {
          case 1:
              mostraAutoDisp();
              listaFiltrata = mostraAutoDisp();
              break;
          case 2:
              System.out.println("Inserisci la marca:");
              String marca = scanner.nextLine();
              cercaAutoMarcaDisp(marca);
              listaFiltrata = cercaAutoMarcaDisp(marca);
              break;
          case 3:
              System.out.println("Inserisci il prezzo massimo:");
              double prezzoMax = scanner.nextDouble();
              cercaAutoCostoDisp(prezzoMax);
              listaFiltrata = cercaAutoCostoDisp(prezzoMax);
              break;
          default:
              System.out.println("Opzione non valida.");
              scanner.close();
              return;
      }

      // Chiedo all'utente di selezionare un'auto
      System.out.println("Seleziona il numero corrispondente all'auto che desideri noleggiare:");
      int scelta = scanner.nextInt();
      scanner.nextLine(); // Per il problema della riga mangiata

      // Verifica se la scelta è valida
      if (scelta < 1 || scelta > listaFiltrata.size()) {
          System.out.println("Scelta non valida.");
          scanner.close();
          return;
      }
    }
  
    public void rimuoviAuto(HashMap<String, AutoNoleggiabile> parcoAuto) {
      Scanner scanner = new Scanner(System.in);
      if (parcoAuto.isEmpty()) {
          System.out.println("Nessuna auto nel parco auto.");
          scanner.close();
          return;
      }
      
      System.out.println("Inserisci la targa dell'auto da rimuovere:");
      String targaDaRimuovere = scanner.nextLine().trim();
      if (!parcoAuto.containsKey(targaDaRimuovere)) {
          System.out.println("Auto non trovata.");
          scanner.close();
          return;
      } else {
          parcoAuto.remove(targaDaRimuovere);
          System.out.println("Auto rimossa con successo.");
          salvaFileAuto(parcoAuto);
      }
      scanner.close();
    }
  
    public void aggiungiAuto() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Inserisci la marca");
      String marca = scanner.nextLine();
      System.out.println("Inserisci il modello");
      String modello = scanner.nextLine();
      System.out.println("Inserisci la targa");
      String targa = scanner.nextLine();
      System.out.println("Inserisci la disponibilità Y/N");
      boolean isDisponibile = scanner.nextBoolean();
      System.out.println("Inserisci il costo orario");
      double costoOrario = scanner.nextDouble();
      System.out.println("Inserisci l'email dell'eventuale affidatario");
      String affidatario = scanner.nextLine();
      if (affidatario.isEmpty()) {
        affidatario = null;
      }
      AutoNoleggiabile nuovaAutomobile = new AutoNoleggiabile(marca, modello, targa, isDisponibile, costoOrario, affidatario);
      salvaFileAuto(parcoAuto);
      if (parcoAuto.containsKey(targa)) {
        System.out.println("L'auto è già presente nel parco auto");
      } else {
        parcoAuto.put(targa, nuovaAutomobile);
        System.out.println("L'auto è stata aggiunta al parco auto");
      }    
      scanner.close();
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
      salvaFileAuto(parcoAuto);
      scanner.close();
    }
  
    public void stampaClienti(HashMap<String, Utente> listaUtenti) {
      for (Utente utente : listaUtenti.values()) {
          if (!utente.getNome().equalsIgnoreCase("Batman")) {
              System.out.println(utente.getNome() + " " + utente.getCognome() + " - Email: " + utente.getEmail() + " - Ruolo: " + utente.getRuolo());
          }
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
