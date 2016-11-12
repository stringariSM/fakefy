package web.ucs.dao.exceptions;

public class DadoReferenciadoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Arquivo em uso. Não pode ser excluído!";

	public DadoReferenciadoException() {
		super(MESSAGE);
	}

	public DadoReferenciadoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public DadoReferenciadoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public DadoReferenciadoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
