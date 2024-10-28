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

public class Funcionario extends Pessoa {
    private String idFuncionario;
    private String cargo; // Exemplo: Administrador ou Atendente
    private double salario;
    private Date dataContratacao;

    public Funcionario(String nome, String email, String telefone, String endereco, String idFuncionario, String cargo, double salario, Date dataContratacao) {
        super(nome, email, telefone, endereco);
        this.idFuncionario = idFuncionario;
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("ID do Funcionário: " + idFuncionario);
        System.out.println("Cargo: " + cargo);
        System.out.println("Salário: " + salario);
        System.out.println("Data de Contratação: " + dataContratacao);
    }
}
