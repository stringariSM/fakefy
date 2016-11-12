package web.ucs.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import web.ucs.dao.exceptions.FalhaAcessoAosDadosException;
import web.ucs.dao.intf.AlunoDAO;
import web.ucs.dao.intf.DAOFactory;
import web.ucs.dao.postgres.PostgresDAOFactory;
import web.ucs.model.Aluno;

@ManagedBean(name="alunoBean")
@SessionScoped
public class AlunoBean {
	
	private Aluno aluno;
	private DAOFactory factory = new PostgresDAOFactory();
		
	public AlunoBean() {
		this.aluno = new Aluno();
	}
	
	// Getters e Setters para as propriedades
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	// Métodos
	
	public List<Aluno> getTodosAlunos() {
		//return FakeBD.getConnection().buscaTodos();
		AlunoDAO dao = factory.getAlunoDAO();
		List<Aluno> alunos = new ArrayList<>(); 
		try {
			alunos = dao.buscaTodos();
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return alunos;
	}
	
	public String inserir() {
		
		//FakeBD.getConnection().insereAluno(aluno);
		AlunoDAO dao = factory.getAlunoDAO();
		try {
			dao.insere(aluno);
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		return "listaAlunos";
	}
	
	public String removeAluno(Aluno umAluno) {
		
		//FakeBD.getConnection().removeAluno(umAluno);
		AlunoDAO dao = factory.getAlunoDAO();
		try {
			dao.remove(umAluno.getId());
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return "listaAlunos";
	}

	public String alterar() {
		
		System.out.println("Mandou alterar");
		
		//FakeBD.getConnection().alteraAluno(aluno);
		AlunoDAO dao = factory.getAlunoDAO();
		try {
			dao.altera(aluno);
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return "listaAlunos";
	}
	
	public String excluir() {
		
		System.out.println("Mandou excluir");
		
		//FakeBD.getConnection().removeAluno(aluno);
		AlunoDAO dao = factory.getAlunoDAO();
		try {
			dao.remove(aluno.getId());
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}

		return "listaAlunos";
	}
	
	public String limpar() {
		
		aluno = new Aluno();
		
		return "mantemAluno";
	}
	
	public String novo() {
		
		
		return "mantemAluno";
	}

	public boolean isInclusao() {
			return (this.aluno==null?true:(this.aluno.getMatricula()==null));
	}
	
	
	public void limpaAluno(ActionEvent actionEvent) {
		aluno = new Aluno();
	}
	
	private Aluno buscaPorId(int id) {
		Aluno aluno = null;
		
		AlunoDAO dao = factory.getAlunoDAO();
		try {
			aluno = dao.buscaAlunoPorId(id);
			dao.closeConnection();
		} catch (FalhaAcessoAosDadosException e) {
			e.printStackTrace();
		}
		
		return aluno;
	}
	
	public void buscaAluno(ActionEvent actionEvent) {
		/*try {
			FacesContext fc = FacesContext.getCurrentInstance();
			String sId = (String) fc.getExternalContext().getRequestParameterMap().get("id");
			if(sId!=null) {
				int id = -1;
				try{
					id = Integer.parseInt(sId);
				} catch(NumberFormatException nfe) {
					
				}
				//this.aluno = FakeBD.getConnection().buscaPorId(id);
				this.aluno = this.buscaPorId(id);
			} else {
				this.aluno = new Aluno();
			}
		} catch (Exception e) {
			this.aluno = new Aluno();
		}*/
	}

}
