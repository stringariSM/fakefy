package web.ucs.dao.exceptions;

public class FaixaNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Aluno não encontrado";

	public FaixaNaoEncontradoException() {
		super(MESSAGE);
	}

	public FaixaNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public FaixaNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public FaixaNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
