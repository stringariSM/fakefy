package web.ucs.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="geralBean")
@RequestScoped
public class GeralBean {

	public String getEnderecoAplicacao() {

		FacesContext fc = FacesContext.getCurrentInstance();

		String endereco = null;
		if ("80".equals(fc.getExternalContext().getRequestServerPort())) {
			endereco = fc.getExternalContext().getRequestScheme() + "://"
					+ fc.getExternalContext().getRequestServerName() 
					+ fc.getExternalContext().getRequestContextPath();
		} else {
			endereco = fc.getExternalContext().getRequestScheme() + "://"
					+ fc.getExternalContext().getRequestServerName() + ":"
					+ fc.getExternalContext().getRequestServerPort()
					+ fc.getExternalContext().getRequestContextPath();
		}
		return endereco;
	}
	
}
