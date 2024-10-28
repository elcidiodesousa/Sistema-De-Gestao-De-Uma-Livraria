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
public class Fornecedor extends Pessoa {
    private String idFornecedor;
    private String cnpj;
    private String tipoProduto; // Exemplo: Livros, Revistas, Jornais, etc.

    public Fornecedor(String nome, String email, String telefone, String endereco, String idFornecedor, String tipoProduto) {
        super(nome, email, telefone, endereco);
        this.idFornecedor = idFornecedor;
        this.tipoProduto = tipoProduto;
    }

    public String getIdFornecedor() {
        return idFornecedor;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("ID do Fornecedor: " + idFornecedor);
        System.out.println("Tipo de Produto: " + tipoProduto);
    }
}
