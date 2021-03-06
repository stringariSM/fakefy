package web.ucs.model;

public class Faixa {
	
	private Integer id_faixa;
	private String  nomeFaixa;
	private String  caminho;
	private Integer idFaixaAlbum;
	private Integer ordem;
	private String  artista;
	private String  album;
	private String caminho_foto_album;
	
	private Artista artistas;
	
	private Album albuns;
	
	public Faixa() {}

	public Faixa(Integer id_faixa, String nome_faixa, String caminho, Integer id_faixa_album, Integer ordem, String caminho_foto_album) {
		super();
		this.id_faixa = id_faixa;
		this.nomeFaixa = nome_faixa;
		this.caminho = caminho;
		this.idFaixaAlbum = id_faixa_album;
		this.ordem = ordem;
		this.caminho_foto_album = caminho_foto_album;
	}

	public Integer getId() {
		return id_faixa;
	}

	public void setId(Integer id) {
		this.id_faixa = id;
	}

	public String getNomeFaixa() {
		return nomeFaixa;
	}

	public void setNomeFaixa(String nome_faixa) {
		this.nomeFaixa = nome_faixa;
	}
	
	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	public Integer getIdFaixaAlbum() {
		return idFaixaAlbum;
	}

	public void setIdFaixaAlbum(Integer id_faixa_album) {
		this.idFaixaAlbum = id_faixa_album;
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
	
	public Album getAlbuns() {
		return albuns;
	}

	public void setAlbuns(Album albuns) {
		this.albuns = albuns;
		
		this.idFaixaAlbum = albuns.getId();
		
	}
	
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getCaminhoFotoAlbum() {
		return caminho_foto_album;
	}

	public void setCaminhoFotoAlbum(String caminho_foto_album) {
		this.caminho_foto_album = caminho_foto_album;
	}
			  

}
