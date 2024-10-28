/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Elcidio De Sousa
 */
import java.util.Date;
import java.util.List;

public class Venda {
    private String idVenda;
    private Cliente cliente;
    private List<Livro> livrosVendidos;
    private double valorTotal;
    private Date dataVenda;
    private String formaPagamento;

    public Venda(String idVenda, Cliente cliente, List<Livro> livrosVendidos, double valorTotal, Date dataVenda, String formaPagamento) {
        this.idVenda = idVenda;
        this.cliente = cliente;
        this.livrosVendidos = livrosVendidos;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
        this.formaPagamento = formaPagamento;
    }

    public String getIdVenda() {
        return idVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Livro> getLivrosVendidos() {
        return livrosVendidos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void exibirRecibo() {
        System.out.println("Recibo de Venda: " + idVenda);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Livros Vendidos: ");
        for (Livro livro : livrosVendidos) {
            System.out.println("- " + livro.getTitulo() + " (Mts" + livro.getPreco() + ")");
        }
        System.out.println("Valor Total: Mts" + valorTotal);
        System.out.println("Data: " + dataVenda);
        System.out.println("Forma de Pagamento: " + formaPagamento);
    }
}

