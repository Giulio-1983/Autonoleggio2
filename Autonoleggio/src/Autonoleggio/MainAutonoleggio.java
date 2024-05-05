import java.util.Scanner;
import java.util.ArrayList; 
import java.util.HashMap;

public class Main {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    HashMap<String, Utente> listaUtenti = new HashMap<String, Utente>();
    HashMap<String, AutoNoleggiabile> parcoAuto = new HashMap<String, AutoNoleggiabile>();
    HashMap<String, Batmobile> listaBatmobili = new HashMap<String, Batmobile>();
    GestioneAutonoleggio gestioneAutonoleggio = new GestioneAutonoleggio(new HashMap<String, AutoNoleggiabile>(), new HashMap<String, Utente>(), new HashMap<String, Batmobile>());

    boolean isEsci = false;
    boolean isBatman = false;
    boolean isManager = false;
    String email = null;
    String passwordInserita = null;

    // login:
    gestioneAutonoleggio.login();

    // Menu: 

    Utente utente = gestioneAutonoleggio.getUtente(email);
    if (utente.getRuolo().equals("manager")) {
      while (true) {
        System.out.println("Cosa vuoi fare?");
        System.out.println("1. Inserisci Auto");
        System.out.println("2. Rimuovi Auto");
        System.out.println("3. Cambia Stato Auto");
        System.out.println("4. Stampa Clienti");
        System.out.println("5. Esci");

        int scelta = scanner.nextInt();
          scanner.nextLine(); // per il problema della riga mangiata

          switch (scelta) {
              case 1:
                  gestioneAutonoleggio.aggiungiAuto();
                  break;
              case 2:
                  gestioneAutonoleggio.rimuoviAuto(parcoAuto);
                  break;
              case 3:
                  gestioneAutonoleggio.cambiaStatoAuto(parcoAuto);
                  break;
              case 4:
                  gestioneAutonoleggio.stampaClienti(listaUtenti);
                  break;
              case 5:
                  System.out.println("Arrivederci!");
                  scanner.close();
                  return;
              default:
                  System.out.println("Scelta non valida.");
                  break;
              
          } // chiusura switche
      }  // chiusura while
        
    } else if (utente.getRuolo().equals("utente")) {
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
                  gestioneAutonoleggio.noleggia();
                  break;
              case 2:
                  gestioneAutonoleggio.mostraAutoDisp();
                  break;
              case 3:
                  System.out.println("Inserisci la marca dell'auto che stai cercando");
                  String marca = scanner.nextLine();
                  gestioneAutonoleggio.cercaAutoMarcaDisp(marca);
                  break; 
              case 4:
                  System.out.println("Inserisci il prezzo dell'auto che stai cercando");
                  double prezzo = scanner.nextDouble();
                  gestioneAutonoleggio.cercaAutoCostoDisp(prezzo);
            case 5:
                  System.out.println("Arrivederci!");
                  scanner.close();
                  return;
              default:
                  System.out.println("Scelta non valida.");
                  break;
          }    
      } // chiusura while
    //} else if (utente.getRuolo().equals("batman")) {
      
      //}
    } 
  }
}
