package web.ucs.dao.exceptions;

public class CursoNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Curso não encontrado";

	public CursoNaoEncontradoException() {
		super(MESSAGE);
	}

	public CursoNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public CursoNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public CursoNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
