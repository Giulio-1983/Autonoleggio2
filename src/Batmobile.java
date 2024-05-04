public class Batmobile extends Automobile{
	
	private String nome;
	private String accessori;

	
	public Batmobile(String marca, String modello, String targa, String nome, String accessori) {
		super(marca, modello, targa);
		this.nome = nome;
		this.accessori = accessori;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAccessori() {
		return accessori;
	}
	public void setAccessori(String accessori) {
		this.accessori = accessori;
	}

	@Override
	public String toString() {
		return super.toString()+","+nome +","+ accessori;
	}
}
