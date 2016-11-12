package web.ucs.dao.exceptions;

public class ProdutoReferenciadoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Produto em uso. Não pode ser excluído!";

	public ProdutoReferenciadoException() {
		super(MESSAGE);
	}

	public ProdutoReferenciadoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public ProdutoReferenciadoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public ProdutoReferenciadoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
