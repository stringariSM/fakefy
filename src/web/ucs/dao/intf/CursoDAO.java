package web.ucs.dao.intf;

import java.util.List;

import web.ucs.dao.FiltroCurso;
import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.model.Curso;

public interface CursoDAO {

	Curso buscaPorId(Integer id) throws FalhaAcessoAosDadosException;

	List<Curso> buscaTodos() throws FalhaAcessoAosDadosException;

	List<Curso> buscaTodos(FiltroCurso filtro) throws FalhaAcessoAosDadosException;
	
	int insere(Curso curso) throws FalhaAcessoAosDadosException;

	int altera(Curso curso) throws FalhaAcessoAosDadosException;

	int remove(Integer id) throws FalhaAcessoAosDadosException;
	
	int conta(FiltroCurso filtro) throws FalhaAcessoAosDadosException;
	
	void closeConnection();
	
}