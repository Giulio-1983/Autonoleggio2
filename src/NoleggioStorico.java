import java.time.LocalDateTime;

public class NoleggioStorico {
    private String affidatarioEmail;
    private LocalDateTime inizioNoleggio;
    private LocalDateTime fineNoleggio;
    private Double sommaPagata;

    public NoleggioStorico(String affidatarioEmail, LocalDateTime inizioNoleggio, LocalDateTime fineNoleggio, Double sommaPagata) {
        this.affidatarioEmail = affidatarioEmail;
        this.inizioNoleggio = inizioNoleggio;
        this.fineNoleggio = fineNoleggio;
        this.sommaPagata = sommaPagata;
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
        return affidatarioEmail +","+ inizioNoleggio +","+ fineNoleggio +","+ sommaPagata;
    }
}
