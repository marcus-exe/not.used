package com.fiap.br.models.transaction;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class Transacao {
    public UUID idTransacao;
    private String nrConta;
    private String tipoTransacao;
    private BigDecimal montante;
    private Date data;
    private String descricao;
    private String contaOrigem;
    private String contaDestino;
    private String hashTransacao;
    private double taxaTransacao;
    public UUID idCliente;
    public UUID idCorretora;

    public Transacao(String id) {
        idTransacao = UUID.randomUUID();
    }

    public Transacao(String id, BigDecimal montante, String descricao, String enderecoOrigem, String enderecoDestino, String hash, double taxa) {

        idTransacao = UUID.randomUUID();
        this.montante = montante;
        this.descricao = descricao;
        contaOrigem = enderecoOrigem;
        contaDestino = enderecoDestino;
        hashTransacao = hash;
        taxaTransacao = taxa;
    }

    public Transacao(String id, /* java.util.Date dataTransacao*/ BigDecimal montate, String descricao, String enderecoOrigem, String enderecoDestino, String hashTransacao, double taxaTransacao, String nrConta, String tipoTransacao, String idCliente, String idCorretora) {
        idTransacao = UUID.fromString(id);
        //data = (Date) dataTransacao;
        montante = montate;
        this.descricao = descricao;
        contaOrigem = enderecoOrigem;
        contaDestino = enderecoDestino;
        this.hashTransacao = hashTransacao;
        this.taxaTransacao = taxaTransacao;
        this.nrConta = nrConta;
        this.tipoTransacao = tipoTransacao;
        this.idCliente = UUID.fromString(idCliente);
        this.idCorretora = UUID.fromString(idCorretora);
    }

    public UUID getIdTransacao() {
        return idTransacao;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    //public java.sql.Date getData() {
    //    return new java.sql.Date(data.getTime());
    //}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public String getHashTransacao() {
        return hashTransacao;
    }

    public void setHashTransacao(String hashTransacao) {
        this.hashTransacao = hashTransacao;
    }
    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public double getTaxaTransacao() {
        return taxaTransacao;
    }

    public void setTaxaTransacao(double taxaTransacao) {
        this.taxaTransacao = taxaTransacao;
    }
    public String getNrConta() {
        return nrConta;
    }

    public void setNrConta(String nrConta) {
        this.nrConta = nrConta;
    }
    public Transacao() {
        idTransacao = UUID.randomUUID();
        idCorretora = UUID.randomUUID();
        idCliente = UUID.randomUUID();
    }

    //getter and setters

    public UUID getIdCorretora() {
        return idCorretora;
    }
    //getter and setters

    public UUID getIdCliente() {
        return idCliente;
    }


    public String getResumoTransacao() {
        return  "\nID da Transação: " + this.getIdTransacao() +
                "  Montante: R$" + this.getMontante() +
                "  Descrição: " + this.getDescricao() +
                "  Endereço Origem: " + this.getContaOrigem() +
                "  Endereço Destino: " + this.getContaDestino() +
                "  Hash de Transação: " + this.getHashTransacao() +
                "  Taxa de Transação: " + this.getTaxaTransacao() + ".";
    }
}
