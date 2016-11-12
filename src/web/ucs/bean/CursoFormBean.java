package web.ucs.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.CursoDAO;
import web.ucs.model.Curso;

@ManagedBean(name = "cursoFormBean")
@ViewScoped
public class CursoFormBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Curso curso;

	public CursoFormBean() {
	}
	
	@PostConstruct
	public void init() {
		this.curso = new Curso();
	}

	// Getters e Setters para as propriedades

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	// Métodos chamados na página listaCursos
	
	public void buscaCurso(ActionEvent actionEvent) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			String sId = (String) fc.getExternalContext()
					.getRequestParameterMap().get("id");
			if (sId != null) {
				int id = -1;
				try {
					id = Integer.parseInt(sId);
				} catch (NumberFormatException nfe) {

				}
				this.curso = this.buscaPorId(id);
			} else {
				this.curso = new Curso();
			}
		} catch (Exception e) {
			this.curso = new Curso();
		}
	}
	
	public void limpaCurso(ActionEvent actionEvent) {
		this.init();
	}

	// Métodos chamados na página formCursos

	public String inserir() {

		CursoDAO dao = factory.getCursoDAO();
		try {
			dao.insere(curso);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return "listaCursos";
	}
	
	public String alterar() {
		
		System.out.println("Mandou alterar " + curso);

		CursoDAO dao = factory.getCursoDAO();
		try {
			dao.altera(curso);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return "listaCursos";
	}
	
	public String excluir() {

		System.out.println("Mandou excluir " + curso);
		
		CursoDAO dao = factory.getCursoDAO();
		try {
			dao.remove(curso.getId());
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return "listaCursos";
	}

	public String limpar() {

		init();
		return "mantemCurso";
	}

	public String novo() {

		return "mantemCurso";
	}

	public boolean isInclusao() {
		return (this.curso == null ? true : (this.curso.getId() == null));
	}
	
	// Metodos internos
	
	private Curso buscaPorId(int id) {
		Curso curso = null;

		CursoDAO dao = factory.getCursoDAO();
		try {
			curso = dao.buscaPorId(id);
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return curso;
	}

}
