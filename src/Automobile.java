public class Automobile {
	private String marca;
	private String modello;
	private String targa;
	
	public Automobile(String marca, String modello, String targa) {
		
		this.marca = marca;
		this.modello = modello;
		this.targa = targa;
	}
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}

	@Override
	public String toString() {
		return marca +"," +  modello +"," + targa;
	}
}
