package web.ucs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.CursoDAO;
import web.ucs.model.Curso;

@ManagedBean(name="cursoListaBean")
@ViewScoped
public class CursoListaBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Curso> cursos;
	
	@PostConstruct
	public void init() {
		cursos = buscaTodosCursos();
	}
		
	// Métodos internos
	
	private List<Curso> buscaTodosCursos() {
		CursoDAO dao = factory.getCursoDAO();
		List<Curso> cursos = new ArrayList<>(); 
		try {
			cursos = dao.buscaTodos();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return cursos;
	}
	
	// Métodos chamados na página
	
	public List<Curso> getTodosCursos() {
		return cursos;
	}
		
	public String removeCurso(Curso curso) {
		
		CursoDAO dao = factory.getCursoDAO();
		try {
			dao.remove(curso.getId());
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		cursos = buscaTodosCursos();

		return "listaCursos";
	}
	
}
