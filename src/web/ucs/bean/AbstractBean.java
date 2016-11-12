package web.ucs.bean;

import java.io.Serializable;

import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.postgres.PostgresDAOFactory;

public abstract class AbstractBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected DAOFactory factory = new PostgresDAOFactory();
	
    protected DAOFactory getDaoFactory() {
    	return this.factory;
    }
	
}
