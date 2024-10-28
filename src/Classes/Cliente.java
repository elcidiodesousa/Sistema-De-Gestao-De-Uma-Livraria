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

public class Cliente extends Pessoa {
    private String idCliente;
    private List<String> historicoCompras;
    private int pontosFidelidade;

    public Cliente(String nome, String email, String telefone, String endereco, String idCliente) {
        super(nome, email, telefone, endereco);
        this.idCliente = idCliente;
        this.historicoCompras = new ArrayList<>();
        this.pontosFidelidade = 0;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void adicionarCompra(String compra) {
        historicoCompras.add(compra);
        // A cada compra, adicionar pontos de fidelidade
        pontosFidelidade += 10;
    }

    public List<String> getHistoricoCompras() {
        return historicoCompras;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("ID do Cliente: " + idCliente);
        System.out.println("Pontos de Fidelidade: " + pontosFidelidade);
    }
}
