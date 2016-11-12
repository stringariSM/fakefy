package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.FiltroAluno;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Aluno;

public interface AlunoDAO {

	Aluno buscaAlunoPorId(Integer id) throws FalhaAcessoAosDadosException;

	List<Aluno> buscaTodos() throws FalhaAcessoAosDadosException;

	List<Aluno> buscaTodos(FiltroAluno filtro) throws FalhaAcessoAosDadosException;
	
	int insere(Aluno aluno) throws FalhaAcessoAosDadosException;

	int altera(Aluno aluno) throws FalhaAcessoAosDadosException;

	int remove(Integer id) throws FalhaAcessoAosDadosException;
	
	int conta(FiltroAluno filtro) throws FalhaAcessoAosDadosException;
	
	void closeConnection();
	
	void setFactory(DAOFactory factory);
	
}