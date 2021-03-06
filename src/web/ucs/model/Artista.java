package web.ucs.model;

public class Artista {
	
	private Integer id;
	private String  nome_artista;
	private Integer ano_nascimento_formacao;
	private Integer id_artista_pais;
	private Integer id_artista_foto;
	private String caminho_foto;
	
	public Artista() {}

	public Artista(Integer id_artista, String nome_artista, Integer ano_nascimento_formacao, Integer id_artista_pais,
			       Integer id_artista_foto, String caminho_foto ) {
		super();
		this.id = id_artista;
		this.nome_artista = nome_artista;
		this.ano_nascimento_formacao = ano_nascimento_formacao;
		this.id_artista_pais = id_artista_pais;
		this.id_artista_foto = id_artista_foto;
		this.caminho_foto = caminho_foto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeArtista() {
		return nome_artista;
	}

	public void setNomeArtista(String nome_artista) {
		this.nome_artista = nome_artista;
	}
	
	public Integer getAnoNascimentoFormacao() {
		return ano_nascimento_formacao;
	}

	public void setAnoNascimentoFormacao(Integer ano_nascimento_formacao) {
		this.ano_nascimento_formacao = ano_nascimento_formacao;
	}
	
	public Integer getIdArtistaPais() {
		return id_artista_pais;
	}

	public void setIdArtistaPais(Integer id_artista_pais) {
		this.id_artista_pais = id_artista_pais;
	}
	
	
	public Integer getIdArtistaFoto() {
		return id_artista_foto;
	}

	public void setIdArtistaFoto(Integer id_artista_foto) {
		this.id_artista_foto = id_artista_foto;
	}

	public String getCaminhoFoto() {
		return caminho_foto;
	}

	public void setCaminhoFoto(String caminho_foto) {
		this.caminho_foto = caminho_foto;
	}

}
