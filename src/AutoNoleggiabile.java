public class AutoNoleggiabile extends Automobile{
		private boolean isDisponibile;
	private double costoOrario;
	private String affidatario;	

	public AutoNoleggiabile(String marca, String modello, String targa, boolean isDisponibile, double costoOrario,
			String affidatario) {
		super(marca, modello, targa);
		this.isDisponibile = isDisponibile;
		this.costoOrario = costoOrario;
		this.affidatario = affidatario;
	}

	public boolean isDisponibile() {
		return isDisponibile;
	}

	public void setDisponibile(boolean isDisponibile) {
		this.isDisponibile = isDisponibile;
	}

	public double getCostoOrario() {
		return costoOrario;
	}

	public void setCostoOrario(double costoOrario) {
		this.costoOrario = costoOrario;
	}

	public String getAffidatario() {
		return affidatario;
	}

	public void setAffidatario(String affidatario) {
		this.affidatario = affidatario;
	}

	
}
