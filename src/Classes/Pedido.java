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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Pedido {
    private String idPedido; // Identificador único do pedido
    private Cliente cliente; // Cliente que fez o pedido
    private List<ItemPedido> itens; // Lista de itens do pedido
    private double valorTotal; // Valor total do pedido
    private String status; // Status do pedido (ex: Pendente, Concluído, Cancelado)
    private Date dataPedido; // Data em que o pedido foi realizado

    // Construtor
    public Pedido(Cliente cliente) {
        this.idPedido = UUID.randomUUID().toString(); // Gera um identificador único
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
        this.status = "Pendente"; // Status inicial
        this.dataPedido = new Date(); // Data atual
    }

    // Método para adicionar um item ao pedido
    public void adicionarItem(Livro livro, int quantidade) {
        ItemPedido item = new ItemPedido(livro, quantidade);
        itens.add(item);
        valorTotal += livro.getPreco() * quantidade; // Atualiza o valor total
        livro.reduzirEstoque(quantidade); // Reduz o estoque do livro
    }

    // Getters e Setters
    public String getIdPedido() {
        return idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    // Método para exibir informações do pedido
    public void exibirInfoPedido() {
        System.out.println("ID do Pedido: " + idPedido);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Data do Pedido: " + dataPedido);
        System.out.println("Status: " + status);
        System.out.println("Itens do Pedido:");
        for (ItemPedido item : itens) {
            System.out.println(" - " + item.getQuantidade() + "x " + item.getLivro().getTitulo() + " (MZN" + item.getLivro().getPreco() + ")");
        }
        System.out.println("Valor Total: MZN" + valorTotal);
    }
}
