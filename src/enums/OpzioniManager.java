package enums;

public enum OpzioniManager {
    AGGIUNGI_AUTO("Aggiungi auto"),
    RIMUOVI_AUTO("Rimuovi auto"),
    CAMBIA_STATO_AUTO("Cambia stato auto"),
    STAMPA_LISTA_CLIENTI("Stampa lista clienti"),
    STAMPA_LISTA_UTENTI("Stampa lista utenti"),
    STAMPA_LISTA_AUTO("Stampa lista auto completa"),
    CERCA_PER_COSTO("Cerca auto disponibili per costo"),
    CERCA_PER_MARCA_MODELLO("Cerca auto disponibili per marca e modello"),
    VEDI_LISTA("Vedi lista tutti auto disponibili"),
    NOLEGGIA_AUTO("Noleggia auto"),
    RESTITUISCI_AUTO("Restituisci auto"),
    VEDI_AUTO_NOLEGGIATE("Vedi lista di auto noleggiate"),
    VEDI_PROPRI_NOLEGGI("Vedi tuoi noleggi attive"),
    ANNULLA_NOLEGGIO("Annulla noleggio"),
    ESCI("Esci");

    private final String description;

    OpzioniManager(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
