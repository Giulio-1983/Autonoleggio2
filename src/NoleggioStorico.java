import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoleggioStorico {
    private static Integer numFattura=0;
    private String targa;
    private String affidatarioEmail;
    private LocalDateTime inizioNoleggio;
    private LocalDateTime fineNoleggio;
    private Double sommaPagata;


    public NoleggioStorico(String targa, String affidatarioEmail, LocalDateTime inizioNoleggio, LocalDateTime fineNoleggio, Double sommaPagata) {
        numFattura++;
        this.targa = targa;
        this.affidatarioEmail = affidatarioEmail;
        this.inizioNoleggio = inizioNoleggio;
        this.fineNoleggio = fineNoleggio;
        this.sommaPagata = sommaPagata;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public static Integer getNumFattura() {
        return numFattura;
    }

    public static void setNumFattura(Integer numFattura) {
        NoleggioStorico.numFattura = numFattura;
    }

    public String getAffidatarioEmail() {
        return affidatarioEmail;
    }

    public void setAffidatarioEmail(String affidatarioEmail) {
        this.affidatarioEmail = affidatarioEmail;
    }

    public LocalDateTime getInizioNoleggio() {
        return inizioNoleggio;
    }

    public void setInizioNoleggio(LocalDateTime inizioNoleggio) {
        this.inizioNoleggio = inizioNoleggio;
    }

    public LocalDateTime getFineNoleggio() {
        return fineNoleggio;
    }

    public void setFineNoleggio(LocalDateTime fineNoleggio) {
        this.fineNoleggio = fineNoleggio;
    }

    public Double getSommaPagata() {
        return sommaPagata;
    }

    public void setSommaPagata(Double sommaPagata) {
        this.sommaPagata = sommaPagata;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String inizioFormatted = inizioNoleggio.format(formatter);
        String fineFormatted = fineNoleggio.format(formatter);
        String sommaPagataFormatted = String.format("%.1f", sommaPagata); // Format sommaPagata as a string with a decimal point
        return targa + "," + affidatarioEmail + "," + inizioFormatted + "," + fineFormatted + "," + sommaPagataFormatted;
    }
}
