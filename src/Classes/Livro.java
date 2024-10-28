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
import java.util.UUID;

public class Livro {
    private String idLivro; // Identificador único
    private String titulo;
    private String autor;
    private String genero;
    private String isbn;
    private String editora;
    private int anoPublicacao;
    private double preco;
    private int quantidadeEstoque;
    private Fornecedor fornecedor; // Associação com um fornecedor

    // Construtor
    public Livro(String titulo, String autor, String genero, String isbn, String editora, int anoPublicacao, double preco, int quantidadeEstoque, Fornecedor fornecedor) {
        this.idLivro = UUID.randomUUID().toString(); // Gera um identificador único
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.isbn = isbn;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters
    public String getIdLivro() {
        return idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEditora() {
        return editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    // Métodos para manipulação de estoque
    public void reduzirEstoque(int quantidade) {
        if (quantidadeEstoque >= quantidade) {
            quantidadeEstoque -= quantidade;
        } else {
            System.out.println("Estoque insuficiente.");
        }
    }

    public void aumentarEstoque(int quantidade) {
        quantidadeEstoque += quantidade;
    }

    // Método para exibir informações do livro
    public void exibirInfoLivro() {
        System.out.println("Livro: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Gênero: " + genero);
        System.out.println("ISBN: " + isbn);
        System.out.println("Editora: " + editora);
        System.out.println("Ano de Publicação: " + anoPublicacao);
        System.out.println("Preço: R$" + preco);
        System.out.println("Estoque: " + quantidadeEstoque);
        System.out.println("Fornecedor: " + fornecedor.getNome());
    }

    void setQuantidadeEstoque(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
