package web.ucs.dao.postgres;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AlbumDAO;
import web.ucs.dao.intf.AlunoDAO;
import web.ucs.dao.intf.ArtistaDAO;
import web.ucs.dao.intf.CursoDAO;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.intf.DadoBinarioDAO;
import web.ucs.dao.intf.ItemPedidoDAO;
import web.ucs.dao.intf.PedidoDAO;
import web.ucs.dao.intf.ProdutoDAO;

public class PostgresDAOFactory extends DAOFactory{

	public PostgresDAOFactory() {
	}

	@Override
	public AlunoDAO getAlunoDAO() {
		
		AlunoDAO dao = null;
		
		try {
			dao = new PostgresAlunoDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
	@Override
	public ArtistaDAO getArtistaDAO() {
		
		ArtistaDAO dao = null;
		
		try {
			dao = new PostgresArtistaDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
	@Override
	public CursoDAO getCursoDAO() {
		
		CursoDAO dao = null;
		
		try {
			dao = new PostgresCursoDAO(this.getConnection());
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
	@Override
	public ProdutoDAO getProdutoDAO() {
		
		ProdutoDAO dao = null;
		
		try {
			dao = new PostgresProdutoDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
	@Override
	public PedidoDAO getPedidoDAO() {
		
		PedidoDAO dao = null;
		
		try {
			dao = new PostgresPedidoDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
	@Override
	public ItemPedidoDAO getItemPedidoDAO() {
		
		ItemPedidoDAO dao = null;
		
		try {
			dao = new PostgresItemPedidoDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
	@Override
	public AlbumDAO getAlbumDAO() {
		
		AlbumDAO dao = null;
		
		try {
			dao = new PostgresAlbumDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}

	@Override
	public DadoBinarioDAO getDadoBinarioDAO() {
		
		DadoBinarioDAO dao = null;
		
		try {
			dao = new PostgresDadoBinarioDAO(this.getConnection());
			dao.setFactory(this);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return dao;
	}
	
}
