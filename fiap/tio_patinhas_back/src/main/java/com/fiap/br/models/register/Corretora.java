package com.fiap.br.models.register;



import com.fiap.br.models.enums.TipoCriptoativo;

import java.util.UUID;

public class Corretora {
    public UUID idCorretora;
    public String nomeCorretora;
    private String cnpj;
    private String telefone;
    private String email;
    private TipoCriptoativo tiposCriptoativosSuportados;
    private String enderecoCarteiraCorretora;

    public Corretora() {
        idCorretora = UUID.randomUUID();
    }

    public Corretora(String id) {
        idCorretora = UUID.randomUUID();
    }

    public Corretora(String id, String nomeCorretoraInput, String cnpjInput, String emailInput, String telefoneInput, TipoCriptoativo tiposCriptoativosSuportadosInput, String enderecoCarteiraCorretoraInput) {
        idCorretora = UUID.fromString(id);
        nomeCorretora = nomeCorretoraInput;
        cnpj = cnpjInput;
        email = emailInput;
        telefone = telefoneInput;
        tiposCriptoativosSuportados = tiposCriptoativosSuportadosInput;
        enderecoCarteiraCorretora = enderecoCarteiraCorretoraInput;
    }

    //getter and setters

    public UUID getIdCorretora() {
        return idCorretora;
    }

    public String getNomeCorretora() {
        return nomeCorretora;
    }

    public void setNomeCorretora(String nomeCorretora) {
        this.nomeCorretora = nomeCorretora;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoCriptoativo getTiposCriptoativosSuportados() {
        return tiposCriptoativosSuportados;
    }

    public void setTiposCriptoativosSuportados(TipoCriptoativo tiposCriptoativosSuportados) {
        this.tiposCriptoativosSuportados = tiposCriptoativosSuportados;
    }

    public String getEnderecoCarteiraCorretora() {
        return enderecoCarteiraCorretora;
    }

    public void setEnderecoCarteiraCorretora(String enderecoCarteiraCorretora) {
        this.enderecoCarteiraCorretora = enderecoCarteiraCorretora;
    }

    public String getResumoCorretora(){
        return "\nId da corretora: " + this.getIdCorretora() +
                "  Nome da corretora: " + this.getNomeCorretora() +
                "  CNPJ: " + this.getCnpj() +
                "  Telefone: " + this.getTelefone() +
                "  Email: " + this.getEmail() +
                "  Tipos de Criptoativos Suportados: " + this.getTiposCriptoativosSuportados() +
                "  Endereço de Carteira da Corretora: " + this.getEnderecoCarteiraCorretora() + ".";
    }
}
