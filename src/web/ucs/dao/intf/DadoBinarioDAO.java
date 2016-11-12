package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.DadoBinario;
import web.ucs.model.Produto;

public interface DadoBinarioDAO {

	List<DadoBinario> buscaArquivos(Produto produto) throws FalhaAcessoAosDadosException;
	
	List<DadoBinario> buscaTodos() throws FalhaAcessoAosDadosException;

	DadoBinario buscaPorId(Integer id) throws FalhaAcessoAosDadosException;

	int insere(DadoBinario dado) throws FalhaAcessoAosDadosException;

	int insere(DadoBinario dado, Produto produto) throws FalhaAcessoAosDadosException;
	
	int remove(Integer id) throws FalhaAcessoAosDadosException;

	int remove(Integer idDado, Integer idProduto) throws FalhaAcessoAosDadosException;
	
	void closeConnection();

	void setFactory(DAOFactory factory);

}