import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return targa + affidatarioEmail +  inizioNoleggio + fineNoleggio + sommaPagata;
    }
}
