package web.ucs.dao.exceptions;

public class AlbumNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Aluno não encontrado";

	public AlbumNaoEncontradoException() {
		super(MESSAGE);
	}

	public AlbumNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public AlbumNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public AlbumNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
