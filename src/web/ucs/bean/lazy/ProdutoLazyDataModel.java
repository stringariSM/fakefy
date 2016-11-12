package web.ucs.bean.lazy;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import web.ucs.dao.FiltroProduto;
import web.ucs.model.Produto;
import web.ucs.service.PedidosService;

public class ProdutoLazyDataModel extends LazyDataModel<Produto> {

	private static final long serialVersionUID = 1L;

	private List<Produto> produtos;
	private FiltroProduto filtro;

	public ProdutoLazyDataModel(List<Produto> produtos, FiltroProduto filtro) {
		this.produtos = produtos;
		this.filtro = filtro;
		
		System.out.println("ProdutoLazyDataModel(...) -> " + filtro);
	}

	@Override
	public Produto getRowData(String rowKey) {
		System.out.println("chamou getRowData() com rowKey = " + rowKey);
		
		Integer id = Integer.parseInt(rowKey);
		
		for (Produto produto : produtos) {
			if (produto.getId().equals(id))
				return produto;
		}
		return new Produto();
	}

	@Override
	public Integer getRowKey(Produto produto) {
		return produto.getId();
	}

	@Override
	public List<Produto> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		System.out.println("Executou load(...)");
		System.out.println("Com filtro " + filtro);

		// dados
		produtos = PedidosService.getInstance().buscaProdutos(filtro);

		// rowCount
		this.setRowCount(PedidosService.getInstance().contaProdutos(filtro));

		return produtos;
	}

//	public List<Produto> load() {
//		System.out.println("Executou load()");
//
//		// dados
//		List<Produto> produtos = PedidosService.getInstance().buscaProdutos(filtro);
//
//		// rowCount
//		this.setRowCount(PedidosService.getInstance().contaProdutos(filtro));
//
//		return produtos;
//	}


}
