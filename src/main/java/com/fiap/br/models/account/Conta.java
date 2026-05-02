package com.fiap.br.models.account;


import com.fiap.br.models.enums.StatusConta;
import com.fiap.br.models.enums.TipoConta;
import com.fiap.br.models.enums.TipoCriptoativo;

import java.util.Date;
import java.util.UUID;

public class Conta {
    private String nrConta;
    private String senhaConta;
    private double saldo;
    private TipoConta tipoConta;
    private StatusConta statusConta;
    private Date dataCriacao;
    private String enderecoCarteira;
    private TipoCriptoativo tipoCriptoativo;
    private UUID idCliente;
    private UUID idCorretora;

    public Conta() {
        dataCriacao = new Date();
        saldo = 0;
        this.statusConta = StatusConta.INATIVA;
        idCliente = UUID.randomUUID(); //sem tempo de implementar com lógica completinha
        idCorretora = UUID.randomUUID();  //sem tempo de implementar com lógica completinha
    }

    public Conta(String nrConta, String senhaConta, double saldo, TipoConta tipoConta, StatusConta statusConta, Date dataCriacao, String enderecoCarteira, UUID idCliente, UUID idCorretora) {
        dataCriacao = new Date();
        saldo = 0;
        this.statusConta = StatusConta.INATIVA;
        idCliente = UUID.randomUUID(); //sem tempo de implementar com lógica completinha
        idCorretora = UUID.randomUUID();  //sem tempo de implementar com lógica completinha
    }

    public Conta (String nr_conta){
        this.nrConta = nrConta;
    }

    public Conta(String nrConta, String senhaConta, TipoConta tipoConta) {
        this.nrConta = nrConta;
        saldo = 0;
        this.senhaConta = senhaConta;
        this.tipoConta = tipoConta;
        this.statusConta = StatusConta.INATIVA;
        dataCriacao = new Date();
        idCliente = UUID.randomUUID(); //sem tempo de implementar com lógica completinha
        idCorretora = UUID.randomUUID();  //sem tempo de implementar com lógica completinha
    }

    public Conta(String id, String senhaInput, String tipoContaInput, Double saldoInput, Date dataAberturaInput, String statusInput, String enderecoCarteiraInput, String idClienteInput, String idCorretoraInput) {
        nrConta = id;
        senhaConta = senhaInput;
        tipoConta = TipoConta.valueOf(tipoContaInput);
        saldo = saldoInput;
        dataCriacao = new Date();
        statusConta = StatusConta.valueOf(statusInput);
        enderecoCarteira = enderecoCarteiraInput;
        idCliente = UUID.fromString(idClienteInput);
        idCorretora = UUID.fromString(idCorretoraInput);
    }

    public UUID getIdCorretora() {
        return idCorretora;
    }

    public void setIdCorretora(UUID idCorretora) {
        this.idCorretora = idCorretora;
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public String getEnderecoCarteira() {
        return enderecoCarteira;
    }

    public void setEnderecoCarteira(String enderecoCarteira) {
        this.enderecoCarteira = enderecoCarteira;
    }

    public String getNrConta() {
        return nrConta;
    }

    public void setNrConta(String nrConta) {
        this.nrConta = nrConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getSenhaConta() {
        return senhaConta;
    }

    public void setSenhaConta(String senhaConta) {
        this.senhaConta = senhaConta;
    }

    public String getTipoConta() {
        return tipoConta.toString();
    }

    public java.sql.Date getDataCriacao() {
        return new java.sql.Date(dataCriacao.getTime());
    }

    public String getStatusConta() {
        return statusConta.toString();
    }

    public void setStatusConta(StatusConta statusConta) {
        this.statusConta = statusConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    /*public String getTipoCriptoativo() {
        return tipoCriptoativo.toString();
    }

    public void setTipoCriptoativo(TipoCriptoativo tipoCriptoativo) {
        this.tipoCriptoativo = new tipoCriptoativo;
    }*/

    public String getResumoConta() {
        return  "\nNúmero da Conta: " + this.getNrConta() +
                "  Saldo: R$" + this.getSaldo() +
                "  Senha da Conta: " + this.getSenhaConta() +
                "  Tipo da Conta: " + this.getTipoConta() +
                "  Data de Abertura: " + this.getDataCriacao() +
                "  Status da Conta: " + this.getStatusConta() +
                "  Endereço da Carteira: " + this.getEnderecoCarteira() + ".";
    }
}


