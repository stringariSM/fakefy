package web.ucs.dao.exceptions;

public class ArtistaNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Aluno não encontrado";

	public ArtistaNaoEncontradoException() {
		super(MESSAGE);
	}

	public ArtistaNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public ArtistaNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public ArtistaNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
