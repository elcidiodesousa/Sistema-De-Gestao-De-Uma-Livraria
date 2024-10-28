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
public class ItemPedido {
    private Livro livro; // Livro que foi pedido
    private int quantidade; // Quantidade do livro

    // Construtor
    public ItemPedido(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    // Getters
    public Livro getLivro() {
        return livro;
    }

    public int getQuantidade() {
        return quantidade;
    }
}

