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
import java.util.List;

public class Estoque {
    private List<Livro> livros; // Lista de livros disponíveis no estoque

    // Construtor
    public Estoque() {
        this.livros = new ArrayList<>();
    }

    // Método para adicionar um livro ao estoque
    public void adicionarLivro(Livro livro, int quantidade) {
        livro.setQuantidadeEstoque(livro.getQuantidadeEstoque() + quantidade); // Atualiza a quantidade do livro
        if (!livros.contains(livro)) {
            livros.add(livro); // Adiciona o livro à lista se não estiver presente
        }
    }

    // Método para remover um livro do estoque
    public void removerLivro(Livro livro, int quantidade) {
        if (livros.contains(livro)) {
            if (livro.getQuantidadeEstoque() >= quantidade) {
                livro.setQuantidadeEstoque(livro.getQuantidadeEstoque() - quantidade); // Atualiza a quantidade do livro
                if (livro.getQuantidadeEstoque() == 0) {
                    livros.remove(livro); // Remove o livro da lista se a quantidade for zero
                }
            } else {
                System.out.println("Quantidade solicitada maior do que a disponível no estoque.");
            }
        } else {
            System.out.println("Livro não encontrado no estoque.");
        }
    }

    // Método para verificar a quantidade de um livro no estoque
    public int verificarEstoque(Livro livro) {
        if (livros.contains(livro)) {
            return livro.getQuantidadeEstoque(); // Retorna a quantidade do livro
        } else {
            return 0; // Livro não está no estoque
        }
    }

    // Método para exibir todos os livros no estoque
    public void exibirEstoque() {
        System.out.println("Livros no Estoque:");
        for (Livro livro : livros) {
            System.out.println("- " + livro.getTitulo() + ": " + livro.getQuantidadeEstoque() + " disponível(s)");
        }
    }
}
