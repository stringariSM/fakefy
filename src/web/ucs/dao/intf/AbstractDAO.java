package web.ucs.dao.intf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class AbstractDAO {
	
	protected DAOFactory factory;
	
	public DAOFactory getFactory() {
		return factory;
	}

	public void setFactory(DAOFactory factory) {
		this.factory = factory;
	}

	protected void closeConnection(Connection aConn) {
		if(aConn!=null) {
			try {
				aConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void fechaResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void fechaStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void fechaPreparedStatement(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
