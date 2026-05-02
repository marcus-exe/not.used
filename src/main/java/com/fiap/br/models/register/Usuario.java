package com.fiap.br.models.register;

import java.util.*;

public class Usuario {
    public UUID idUsuario;
    public String nomeCompleto;
    private String email;
    private String senha;
    private String cpfCnpj;
    private String telefone;
    private Date dataCriacao;
    private Date dataNascimento;
    private String cep;
    private String pais;


    public Usuario() {
        idUsuario = UUID.randomUUID();
        dataCriacao = new Date();
        dataNascimento = new Date();
        cep =  "1111111";
        pais = "BR";
    }

    public Usuario(String id, String nomeCompletoInput, String emailInput, String senhaInput, String cpfCnpjInput, String telefoneInput) {
        idUsuario = UUID.fromString(id);
        nomeCompleto = nomeCompletoInput;
        email = emailInput;
        senha = senhaInput;
        cpfCnpj = cpfCnpjInput;
        telefone = telefoneInput;
    }

    //getter and setters

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpf(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public java.sql.Date getDataCriacao() {
        return new java.sql.Date(dataCriacao.getTime());
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public java.sql.Date getDataNascimento() {
        return new java.sql.Date(dataNascimento.getTime());
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getResumoUsuario(){
        return "\nId do usuário: " + this.getIdUsuario() +
                "  Nome Completo: " + this.getNomeCompleto() +
                "  Email: " + this.getEmail() +
                "  Senha: " + this.getSenha() +
                "  CPF ou CNPJ: " + this.getCpfCnpj() +
                "  Telefone: " + this.getTelefone() + ".";
    }
}
