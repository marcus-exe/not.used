package com.fiap.br.models.enums;

public enum TipoCriptoativo {
    BITCOIN("Bitcoin", "BTC"),
    ETHEREUM("Ethereum", "ETH"),
    LITECOIN("Litecoin", "LTC"),
    CARDANO("Cardano", "ADA"),
    POLKADOT("Polkadot", "DOT"),
    RIPPLE("Ripple", "XRP"),
    DOGECOIN("Dogecoin", "DOGE"),
    SOLANA("Solana", "SOL"),
    CHAINLINK("Chainlink", "LINK"),
    UNISWAP("Uniswap", "UNI");

    private final String nome;
    private final String simbolo;

    TipoCriptoativo(String nome, String simbolo) {
        this.nome = nome;
        this.simbolo = simbolo;
    }

    public String getNome() {
        return nome;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
