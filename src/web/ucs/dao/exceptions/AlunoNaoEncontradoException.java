package web.ucs.dao.exceptions;

public class AlunoNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Aluno não encontrado";

	public AlunoNaoEncontradoException() {
		super(MESSAGE);
	}

	public AlunoNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public AlunoNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public AlunoNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
