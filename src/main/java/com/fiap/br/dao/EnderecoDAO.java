package com.fiap.br.dao;

import com.fiap.br.exception.EntidadeNaoEcontradaException;
import com.fiap.br.factory.ConnectionFactory;
import com.fiap.br.models.register.Endereco;
import com.fiap.br.models.register.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class EnderecoDAO {
    private Connection connection;

    public EnderecoDAO() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }
    public void registerEndereco(Endereco endereco) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("insert into endereco values(?,?,?,?,?,?,?,?,?)");
        stm.setString(1, endereco.getIdEndereco().toString());
        stm.setString(2, endereco.getRua());
        stm.setInt(3, endereco.getNumero());
        stm.setString(4, endereco.getComplemento());
        stm.setString(5, endereco.getBairro());
        stm.setString(6, endereco.getCidade());
        stm.setString(7, endereco.getCdEstado());
        stm.setString(8, endereco.getCep());
        stm.setString(9, endereco.getPais());
        stm.executeUpdate();
    }

    public List<Endereco> listar() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM ENDERECO");
        ResultSet result = stm.executeQuery();
        List<Endereco> lista = new ArrayList<>();
        while (result.next()){

            lista.add(parseEndereco(result));
        }
        return lista;
    }

    public Endereco getEndereco(String id) throws SQLException, EntidadeNaoEcontradaException {
        PreparedStatement stm = connection.prepareStatement("select * from endereco where id_endereco = ?");
        stm.setString(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEcontradaException("Endereco não encontrado");
        return parseEndereco(result);
    }

    private Endereco parseEndereco(ResultSet result) throws SQLException {
        String id = result.getString("id_endereco");
        String rua = result.getString("rua");
        int numero = result.getInt("numero");
        String complemento = result.getString("complemento");
        String bairro = result.getString("bairro");
        String cidade = result.getString("cidade");
        String cdEstado = result.getString("cd_estado");
        String cep = result.getString("cep");
        String pais = result.getString("pais");
        return new Endereco(id, rua, numero, complemento, bairro, cidade, cdEstado, cep, pais);
    }

    public void updateEndereco(Endereco endereco) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("update endereco set rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, cd_estado = ?, cep = ?, pais = ? where id_endereco = ?");
        stm.setString(1, endereco.getRua());
        stm.setInt(2, endereco.getNumero());
        stm.setString(3, endereco.getComplemento());
        stm.setString(4, endereco.getBairro());
        stm.setString(5, endereco.getCidade());
        stm.setString(6, endereco.getCdEstado());
        stm.setString(7, endereco.getCep());
        stm.setString(8, endereco.getPais());
        stm.setString(9, endereco.getIdEndereco().toString());
        stm.executeUpdate();
    }

    public void deleteEndereco(String id_endereco) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("delete from endereco where id_endereco = ?");
        stm.setString(1, id_endereco);
        int linha = stm.executeUpdate();
        if (linha == 0)
            throw new SQLException("Endereco não encontrado para ser removido");
    }
}
