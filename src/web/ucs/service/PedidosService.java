package web.ucs.service;

import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.FiltroProduto;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.intf.DadoBinarioDAO;
import web.ucs.dao.intf.ItemPedidoDAO;
import web.ucs.dao.intf.PedidoDAO;
import web.ucs.dao.intf.ProdutoDAO;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.DadoBinario;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;

public class PedidosService {

	private static PedidosService instance;
	private DAOFactory factory = new PostgresDAOFactory();

	public static PedidosService getInstance() {
		if (instance == null) {
			instance = new PedidosService();
		}
		return instance;
	}

	private List<Pedido> pedidos;
	private List<Produto> produtos;

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	private PedidosService() {
		this.inicializaProdutos();
		this.inicializaPedidos();
	}

	private void inicializaProdutos() {

		ProdutoDAO dao = factory.getProdutoDAO();
		try {
			this.produtos = dao.buscaTodos();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
	}

	private void inicializaPedidos() {

		PedidoDAO dao = factory.getPedidoDAO();
		try {
			this.pedidos = dao.buscaTodos();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		for (Pedido pedido : pedidos) {
			ItemPedidoDAO itemDao = factory.getItemPedidoDAO();
			List<ItemPedido> itens = new ArrayList<>();
			try {
				itens = itemDao.buscaTodos(pedido, true);
				pedido.setItens(itens);
			} catch (FalhaAcessoAosDadosException e) {
				e.printStackTrace();
			}
		}
	}

	public void removePedido(Pedido pedido) {
		this.excluiPedido(pedido);
		this.pedidos.remove(pedido);
	}

	public Pedido getPedido(int index) {
		return this.pedidos.get(index);
	}

	public void salvaPedido(Pedido pedido) {
		System.out.println("chamou salvaPedido");
		if (pedido.getId() == null) {
			inserePedido(pedido);
			this.pedidos.add(pedido);
		} else {
			alteraPedido(pedido);
		}
	}

	// Nesse exemplo a inserção dos pedidos ocorre em uma transação e a inserção
	// dos ítens em outra
	public void inserePedido(Pedido pedido) {

		System.out.println("chamou inserePedido");

		PedidoDAO dao = factory.getPedidoDAO();

		try {
			Integer idPedido = dao.insere(pedido);
			pedido.setId(idPedido);
			for (ItemPedido item : pedido.getItens()) {
				ItemPedidoDAO itemDao = factory.getItemPedidoDAO();
				itemDao.insere(pedido, item);
			}

		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
	}

	// Nesse exemplo, a transação é feita dentro do DAO
	public void excluiPedido(Pedido pedido) {

		System.out.println("chamou excluiPedido");

		PedidoDAO dao = factory.getPedidoDAO();

		try {
			dao.remove(pedido.getId(), true);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
	}

	// Nesse exemplo a transação é controlada no Service
	public void alteraPedido(Pedido pedido) {

		System.out.println("chamou alteraPedido");

		PedidoDAO dao = factory.getPedidoDAO();
		// abre a atranssacao no pedido
		dao.abreTransacao();
		ItemPedidoDAO itemDao = factory.getItemPedidoDAO();
		// passa a conexão para os itens, para ficarem na mesma transação
		itemDao.setConnection(dao.getConnection());

		try {
			// remove todos os itens
			itemDao.removeTodos(pedido);
			// altera o pedido
			dao.altera(pedido);
			for (ItemPedido item : pedido.getItens()) {
				itemDao.insere(pedido, item);
			}
			// reinsere todos os itens, já alterados
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		// fecha a transação
		dao.commitaTransacao();
		// fecha a conexão
		dao.closeConnection();

	}

	public List<Produto> buscaProdutos(FiltroProduto filtro) {

		List<Produto> produtos = new ArrayList<>();

		ProdutoDAO dao = factory.getProdutoDAO();
		try {
			if (filtro != null) {
				produtos = dao.buscaTodos(filtro);
			} else {
				produtos = dao.buscaTodos();
			}
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return produtos;
	}

	public Integer contaProdutos(FiltroProduto filtro) {

		Integer quantidade = null;

		ProdutoDAO dao = factory.getProdutoDAO();
		try {
			quantidade = dao.conta(filtro);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return quantidade;
	}

	public void salvaProduto(Produto produto) {
		System.out.println("chamou salvaProduto com produto = " + produto);
		if (produto.getId() == null) {
			insereProduto(produto);
		} else {
			alteraProduto(produto);
		}
	}

	public void insereProduto(Produto produto) {

		System.out.println("chamou insereProduto para produto = " + produto);

		ProdutoDAO dao = factory.getProdutoDAO();

		try {
			dao.insere(produto);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
	}

	public void alteraProduto(Produto produto) {

		System.out.println("chamou alteraProduto para produto = " + produto);

		ProdutoDAO dao = factory.getProdutoDAO();

		try {
			dao.altera(produto);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
	}

	public void removeProduto(Produto produto)
			throws FalhaAcessoAosDadosException {

		System.out.println("chamou removeProduto para produto = " + produto);

		ProdutoDAO dao = factory.getProdutoDAO();

		dao.remove(produto.getId());
	}

	public void insereDadoBinario(DadoBinario dado)
			throws FalhaAcessoAosDadosException {

		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		dao.insere(dado);

	}

	public void vinculaProdutoComFoto(Produto produto, DadoBinario dado)
			throws FalhaAcessoAosDadosException {

		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		dao.insere(dado, produto);

	}

	
	public List<DadoBinario> buscaDados() {
		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		List<DadoBinario> dados = new ArrayList<>();
		try {
			dados = dao.buscaTodos();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return dados;
	}

	public DadoBinario buscaDado(Integer id) {
		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		DadoBinario dado = null;
		try {
			dado = dao.buscaPorId(id);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return dado;
	}

	public void removeDado(Integer id) throws FalhaAcessoAosDadosException {
		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		dao.remove(id);
	}

	public void removeDado(Integer idDado, Integer idProduto) throws FalhaAcessoAosDadosException {
		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		dao.remove(idDado, idProduto);
	}

	
	public List<DadoBinario> buscaDados(Produto produto) {
		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		List<DadoBinario> dados = new ArrayList<>();
		try {
			dados = dao.buscaArquivos(produto);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return dados;
	}

}
