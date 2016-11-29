package web.ucs.model;

public class Faixa {
	
	private Integer id_faixa;
	private String  nome_faixa;
	private String  caminho;
	private Integer id_faixa_album;
	private Integer ordem;
	private String  artista;
	private String  album;
	
	public Faixa() {}

	public Faixa(Integer id_faixa, String nome_faixa, String caminho, Integer id_faixa_album, Integer ordem) {
		super();
		this.id_faixa = id_faixa;
		this.nome_faixa = nome_faixa;
		this.caminho = caminho;
		this.id_faixa_album = id_faixa_album;
		this.ordem = ordem;
	}

	public Integer getId() {
		return id_faixa;
	}

	public void setId(Integer id) {
		this.id_faixa = id;
	}

	public String getNomeFaixa() {
		return nome_faixa;
	}

	public void setNomeFaixa(String nome_faixa) {
		this.nome_faixa = nome_faixa;
	}
	
	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	public Integer getIdFaixaAlbum() {
		return id_faixa_album;
	}

	public void setIdFaixaAlbum(Integer id_faixa_album) {
		this.id_faixa_album = id_faixa_album;
	}
	
	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}
	
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
			  

}
