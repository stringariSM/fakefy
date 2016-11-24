package web.ucs.service;

import java.util.ArrayList;
import java.util.List;

import web.ucs.dao.FiltroProduto;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AlbumDAO;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.intf.DadoBinarioDAO;
import web.ucs.dao.intf.FaixaDAO;
import web.ucs.dao.intf.ItemPedidoDAO;
import web.ucs.dao.intf.PedidoDAO;
import web.ucs.dao.intf.ProdutoDAO;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.Album;
import web.ucs.model.DadoBinario;
import web.ucs.model.Faixa;
import web.ucs.model.ItemPedido;
import web.ucs.model.Pedido;
import web.ucs.model.Produto;

public class FaixaService {

	private static FaixaService instance;
	private DAOFactory factory = new PostgresDAOFactory();

	public static FaixaService getInstance() {
		if (instance == null) {
			instance = new FaixaService();
		}
		return instance;
	}

	private List<Faixa> faixas;	

	public List<Faixa> getFaixas() {
		return faixas;
	}

	public void setFaixas(List<Faixa> faixas) {
		this.faixas = faixas;
	}
	
	public List<Faixa> getFaixas(int id) {
		FaixaDAO dao = factory.getFaixaDAO();
		try {
			this.faixas = dao.buscaFaixaPorId(id);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return faixas;
	}
	
	private FaixaService() {
		
	}
	
	private void inicializaAlbuns(int id) {

		FaixaDAO dao = factory.getFaixaDAO();
		try {
			this.faixas = dao.buscaFaixaPorId(id);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
	}

	
	public void insereDadoBinario(DadoBinario dado)
			throws FalhaAcessoAosDadosException {

		DadoBinarioDAO dao = factory.getDadoBinarioDAO();
		dao.insere(dado);

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
