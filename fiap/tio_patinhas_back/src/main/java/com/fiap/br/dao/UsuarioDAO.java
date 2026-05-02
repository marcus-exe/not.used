package com.fiap.br.dao;

import com.fiap.br.exception.EntidadeNaoEcontradaException;
import com.fiap.br.factory.ConnectionFactory;
import com.fiap.br.models.register.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }
    public void registerUsuario(Usuario usuario) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("insert into clientes values(?,?,?,?,?,?,?,?,?,?)");
        stm.setString(1, usuario.getIdUsuario().toString());
        stm.setString(2, usuario.getNomeCompleto());
        stm.setDate(3, usuario.getDataNascimento());
        stm.setString(4, usuario.getCpfCnpj());
        stm.setString(5, usuario.getEmail());
        stm.setString(6, usuario.getSenha());
        stm.setString(7, usuario.getTelefone());
        stm.setDate(8, usuario.getDataCriacao());
        stm.setString(9, usuario.getCep());
        stm.setString(10, usuario.getPais());
        stm.executeUpdate();
    }

    public List<Usuario> listar() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM CLIENTES");
        ResultSet result = stm.executeQuery();
        List<Usuario> lista = new ArrayList<>();
        while (result.next()){
            lista.add(parseUsuario(result));
        }
        return lista;
    }
    public Usuario getUsuario(String id) throws SQLException, EntidadeNaoEcontradaException {
        PreparedStatement stm = connection.prepareStatement("select * from clientes where id_cliente = ?");
        stm.setString(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEcontradaException("Usuário não encontrado");
        return parseUsuario(result);
    }

    private Usuario parseUsuario(ResultSet result) throws SQLException {
        String id = result.getString("id_cliente");
        String nomeCompleto = result.getString("nome_completo");
        String email = result.getString("email");
        String senha = result.getString("senha");
        String cpfCnpj = result.getString("cpf_cnpj");
        String telefone = result.getString("telefone");
        return new Usuario(id, nomeCompleto, email, senha, cpfCnpj, telefone);
    }



    public void updateUsuario(Usuario usuario) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("update clientes set nome_completo = ?, email = ?, senha = ?, cpf_cnpj = ?, telefone = ?  where id_cliente = ?");
        stm.setString(1, usuario.getNomeCompleto());
        stm.setString(2, usuario.getEmail());
        stm.setString(3, usuario.getSenha());
        stm.setString(4, usuario.getCpfCnpj());
        stm.setString(5, usuario.getTelefone());
        stm.setString(6, usuario.getIdUsuario().toString());

        stm.executeUpdate();
    }

    public void deleteUsuario(String id_usuario) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("delete from clientes where id_cliente = ?");
        stm.setString(1, id_usuario);
        int linha = stm.executeUpdate();
        if (linha == 0)
            throw new SQLException("Usuário não encontrado para ser removido");
    }
}


/*
package com.fiap.br.dao;

import com.fiap.br.exception.EntidadeNaoEncontradaException;

import java.sql.ResultSet;
import java.util.*;
import com.fiap.br.factory.ConnectionFactory;
import com.fiap.br.models.register.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.fiap.br.utils.InitSQLString.initSQLString;

public class UsuarioDao {
    private Connection conection;

    public UsuarioDao() throws SQLException {
        conection = ConnectionFactory.getConnection();
    }

    public void cadastrar(Usuario usuario) throws SQLException {
        PreparedStatement stm = conection.prepareStatement("INSERT INTO CLIENTES (nome_completo, email, senha, cpf_cnpj, telefone, data_criacao) VALUES (seq_usuario.nextval, ?, ?, ?, ?, ?, ?)");
        stm.setString(1, usuario.getNomeCompleto());
        stm.setString(2, usuario.getEmail());
        stm.setString(3, usuario.getSenha());
        stm.setString(4, usuario.getCpfCnpj());
        stm.setString(5, usuario.getTelefone());
        stm.setDate(6, usuario.getDataCriacao());
        stm.executeUpdate();
    }

    public void closeConection() throws SQLException {
        conection.close();
    }

    public Usuario pesquisar(String nome_completo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conection.prepareStatement("SELECT * FROM CLIENTES WHERE nome_completo = ?");
        stm.setString(1, nome_completo);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEncontradaException("Usuário não encontrado");
        return parseUsuario(result);
    }

    private Usuario parseUsuario(ResultSet result) throws SQLException {
        String nome = result.getString("nome_completo");
        String tel = result.getString("telefone");
        return new Usuario(nome, tel);
    }

    public List<Usuario> listar() throws SQLException {
        PreparedStatement stm = conection.prepareStatement("SELECT * FROM CLIENTES");
        ResultSet result = stm.executeQuery();
        List<Usuario> lista = new ArrayList<>();
        while (result.next()){

            lista.add(parseUsuario(result));
        }
        return lista;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        PreparedStatement stm = conection.prepareStatement("UPDATE CLIENTES SET nome_completo = ?, email = ?, senha = ?, telefone = ? where id_cliente = ?");
        stm.setString(1, usuario.getNomeCompleto());
        stm.setString(2, usuario.getEmail());
        stm.setString(3, usuario.getSenha());
        stm.setString(4, usuario.getTelefone());
        stm.setString(5, usuario.getIdUsuario());
        stm.executeUpdate();
    }

    public void remover(String nome_completo) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conection.prepareStatement("DELETE from CLIENTES where nome_completo = ?");
        stm.setString(1, nome_completo);
        int linha = stm.executeUpdate();
        if (linha == 0)
            throw new EntidadeNaoEncontradaException("Usuário não encontrado para ser removido");
    }

}
*/



