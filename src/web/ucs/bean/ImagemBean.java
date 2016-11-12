package web.ucs.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import web.ucs.model.DadoBinario;
import web.ucs.service.PedidosService;

@ManagedBean(name="imagemBean")
@ApplicationScoped
public class ImagemBean {

    public byte[] getImagem(Integer id) {
    	
    	DadoBinario dado = PedidosService.getInstance().buscaDado(id);
    	if(dado!=null) {
    		return dado.getConteudo();
    	}
        return null;
    }

}