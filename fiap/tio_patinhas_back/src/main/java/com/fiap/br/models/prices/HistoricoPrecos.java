package com.fiap.br.models.prices;

import com.fiap.br.models.enums.TipoCriptoativo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class HistoricoPrecos {
    public TipoCriptoativo tipoCriptoativo;
    public UUID idRegistro;
    private LocalDateTime dataHora;
    private BigDecimal precoAbertura;
    private BigDecimal precoFechamento;
    private double volumeNegociacao;

    public HistoricoPrecos() {
        idRegistro = UUID.randomUUID();
        dataHora = LocalDateTime.now();
    }

    public HistoricoPrecos(BigDecimal precoAbertura, BigDecimal precoFechamento, double volumeNegociacao) {
        this.precoAbertura = precoAbertura;
        this.precoFechamento = precoFechamento;
        this.volumeNegociacao = volumeNegociacao;
    }

    public TipoCriptoativo getTipoCriptoativo() {
        return tipoCriptoativo;
    }

    public void setTipoCriptoativo(TipoCriptoativo tipoCriptoativo) {
        this.tipoCriptoativo = tipoCriptoativo;
    }

    public UUID getIdRegistro() {
        return idRegistro;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getPrecoAbertura() {
        return precoAbertura;
    }

    public void setPrecoAbertura(BigDecimal precoAbertura) {
        this.precoAbertura = precoAbertura;
    }

    public BigDecimal getPrecoFechamento() {
        return precoFechamento;
    }

    public void setPrecoFechamento(BigDecimal precoFechamento) {
        this.precoFechamento = precoFechamento;
    }

    public double getVolumeNegociacao() {
        return volumeNegociacao;
    }

    public void setVolumeNegociacao(double volumeNegociacao) {
        this.volumeNegociacao = volumeNegociacao;
    }

    public abstract String getResumoAtivo();
}
