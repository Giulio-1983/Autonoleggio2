package autonoleggio;

public class AutoNoleggiabile extends Automobile {
	private boolean isDisponibile;
	private double costoOrario;

	public AutoNoleggiabile(String marca, String modello, String targa, boolean isDisponibile, double costoOrario) {
		super(marca, modello, targa);
		this.isDisponibile = isDisponibile;
		this.costoOrario = costoOrario;
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

	public boolean getIsDisponibile(boolean isDisponibile) {
		return isDisponibile;
	}

	public void setIsDisponibile(boolean isDisponibile) {
		this.isDisponibile = isDisponibile;
	}
	
	@Override
	public String toString() {
		return getMarca() + "," + getModello() + "," + getTarga() + "," + isDisponibile() + "," + getCostoOrario();
	}

}
