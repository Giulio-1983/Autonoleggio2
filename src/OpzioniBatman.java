public enum OpzioniBatman {
    AGGIUNGI_BATMOBILE("Aggiungi Batmobile"),
    RIMUOVI_BATMOBILE("Rimuovi Batmobile"),
    VEDI_LISTA_BATMOBILI("Vedi lista batmobili"),
    ESCI("Esci");

    private final String description;

    OpzioniBatman(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
