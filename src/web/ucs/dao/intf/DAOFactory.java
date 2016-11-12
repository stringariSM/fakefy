package web.ucs.dao.intf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;

public abstract class DAOFactory {

	protected Connection getConnection() throws FalhaAcessoAosDadosException {
		
		Connection aConn = null;
		
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:jboss/datasources/AulaJSF");
			if (ds != null) {
				aConn = ds.getConnection();
			}
		} catch (NamingException e) {
			throw new FalhaAcessoAosDadosException("Datasource não encontrado", e);
		} catch (SQLException e) {
			throw new FalhaAcessoAosDadosException(e);
		}
		
		return aConn;
	}
	
	protected Connection getConnectionJDBC() throws FalhaAcessoAosDadosException {
		
		return this.getConnection("org.postgresql.Driver",
				"jdbc:postgresql://localhost:5432/paw1", "postgres", "postgres");
	}
	
	private Connection getConnection(String driver, String url, String user,
			String pwd) throws FalhaAcessoAosDadosException {

		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException cnfe) {
			throw new FalhaAcessoAosDadosException(cnfe);
		} catch (SQLException se) {
			throw new FalhaAcessoAosDadosException(se);
		}

		return conn;
	}
	
	public abstract AlunoDAO getAlunoDAO();
	
	public abstract ArtistaDAO getArtistaDAO();
	
	public abstract CursoDAO getCursoDAO();
	
	public abstract ProdutoDAO getProdutoDAO();
	
	public abstract PedidoDAO getPedidoDAO();
	
	public abstract ItemPedidoDAO getItemPedidoDAO();
	
	public abstract DadoBinarioDAO getDadoBinarioDAO();
	
}
