package web.ucs.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
	
	private Integer id;
	private String nomeCliente;
	private Date data;
	private List<ItemPedido> itens;
	
	public Pedido() {
		this.data = new Date();
		this.itens = new ArrayList<>();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public List<ItemPedido> getItens() {
		return itens;
	}
	
	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	public void addItem(ItemPedido item) {
		System.out.println("Adicionado item ao pedido");
		this.itens.add(item);
		System.out.println("Agora tem " + itens.size() + " na lista");
	}

	public void removeItem(ItemPedido item) {
		this.itens.remove(item);
	}
	
	public void removeItem(int index) {
		this.itens.remove(index);
	}

	public ItemPedido getItem(int index) {
		return this.itens.get(index);
	}
	
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", nomeCliente=" + nomeCliente + ", data="
				+ data + ", itens=" + itens + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
