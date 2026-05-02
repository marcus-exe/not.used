package com.fiap.br.dao;

import com.fiap.br.exception.EntidadeNaoEcontradaException;
import com.fiap.br.factory.ConnectionFactory;
import com.fiap.br.models.register.Corretora;
import com.fiap.br.models.register.Endereco;
import com.fiap.br.models.register.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fiap.br.models.enums.TipoCriptoativo;

public class CorretoraDAO {
    private Connection connection;

    public CorretoraDAO() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }
    public void registerCorretora(Corretora corretora) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("insert into corretora values(?,?,?,?,?,?,?)");
        stm.setString(1, corretora.getIdCorretora().toString());
        stm.setString(2, corretora.getNomeCorretora());
        stm.setString(3, corretora.getCnpj());
        stm.setString(4, corretora.getEmail());
        stm.setString(5, corretora.getTelefone());
        stm.setString(6, corretora.getTiposCriptoativosSuportados().toString());
        stm.setString(7, corretora.getEnderecoCarteiraCorretora());
        stm.executeUpdate();
    }

    public List<Corretora> listar() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM CORRETORA");
        ResultSet result = stm.executeQuery();
        List<Corretora> lista = new ArrayList<>();
        while (result.next()){

            lista.add(parseCorretora(result));
        }
        return lista;
    }

    public Corretora getCorretora(String id) throws SQLException, EntidadeNaoEcontradaException {
        PreparedStatement stm = connection.prepareStatement("select * from corretora where id_corretora = ?");
        stm.setString(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEcontradaException("Corretora não encontrada");
        return parseCorretora(result);
    }

    private Corretora parseCorretora(ResultSet result) throws SQLException {
        String id = result.getString("id_corretora");
        String nomeCorretora = result.getString("nome_corretora");
        String cnpj = result.getString("cnpj");
        String email = result.getString("email");
        String telefoneCorretora = result.getString("telefone");
        TipoCriptoativo tipos = TipoCriptoativo.valueOf(result.getString("tipos_criptoativos_suportados"));
        String enderecoCarteira = result.getString("endereco_carteira_corretora");
        return new Corretora(id, nomeCorretora, cnpj, email, telefoneCorretora, tipos, enderecoCarteira);
    }

    public void updateCorretora(Corretora corretora) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("update corretora set nome_corretora = ?, cnpj = ?, email = ?, telefone = ?, tipos_criptoativos_suportados = ?, endereco_carteira_corretora = ? where id_corretora = ?");
        stm.setString(1, corretora.getNomeCorretora());
        stm.setString(2, corretora.getCnpj());
        stm.setString(3, corretora.getEmail());
        stm.setString(4, corretora.getTelefone());
        stm.setString(5, corretora.getTiposCriptoativosSuportados().toString());
        stm.setString(6, corretora.getEnderecoCarteiraCorretora());
        stm.setString(7, corretora.getIdCorretora().toString());
        stm.executeUpdate();
    }

    public void deleteCorretora(String id_corretora) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("delete from corretora where id_corretora = ?");
        stm.setString(1, id_corretora);
        int linha = stm.executeUpdate();
        if (linha == 0)
            throw new SQLException("Corretora não encontrada para ser removida");
    }
}
