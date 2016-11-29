package web.ucs.dao.exceptions;

public class PlaylistNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Aluno não encontrado";

	public PlaylistNaoEncontradoException() {
		super(MESSAGE);
	}

	public PlaylistNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public PlaylistNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public PlaylistNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
