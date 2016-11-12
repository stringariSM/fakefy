package web.ucs.dao.exceptions;

public class PedidoNaoEncontradoException extends FalhaAcessoAosDadosException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Pedido não encontrado";

	public PedidoNaoEncontradoException() {
		super(MESSAGE);
	}

	public PedidoNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " : " + message, cause);
	}

	public PedidoNaoEncontradoException(String message) {
		super(MESSAGE + " : " + message);
	}

	public PedidoNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
	}


}
