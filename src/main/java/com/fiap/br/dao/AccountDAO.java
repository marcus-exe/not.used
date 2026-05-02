package com.fiap.br.dao;

import com.fiap.br.exception.EntidadeNaoEcontradaException;
import com.fiap.br.factory.ConnectionFactory;
import com.fiap.br.models.account.Conta;
import com.fiap.br.models.register.Corretora;
import com.fiap.br.models.register.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AccountDAO {
    private Connection connection;

    public AccountDAO() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }
    public void registerAccount(Conta conta) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("insert into contas_cripto values(?,?,?,?,?,?,?,?,?)");
        stm.setString(1, conta.getNrConta());
        stm.setString(2, conta.getSenhaConta());
        stm.setString(3, conta.getTipoConta());
        stm.setDouble(4, conta.getSaldo());
        stm.setDate(5, conta.getDataCriacao());
        stm.setString(6, conta.getStatusConta());
        stm.setString(7, conta.getEnderecoCarteira());
        stm.setString(8, conta.getIdCliente().toString());
        stm.setString(9, conta.getIdCorretora().toString());
        stm.executeUpdate();
    }

    public List<Conta> listar() throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM contas_cripto");
        ResultSet result = stm.executeQuery();
        List<Conta> lista = new ArrayList<>();
        while (result.next()){

            lista.add(parseAccount(result));
        }
        return lista;
    }

    public Conta getAccount(String id) throws SQLException, EntidadeNaoEcontradaException {
        PreparedStatement stm = connection.prepareStatement("select * from contas_cripto where nr_conta = ?");
        stm.setString(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEcontradaException("Produto não encontrado");
        return parseAccount(result);
    }

    private Conta parseAccount(ResultSet result) throws SQLException {
        String id = result.getString("nr_conta");
        String senha = result.getString("password");
        String tipoConta = result.getString("tipo_conta");
        Double saldo = result.getDouble("saldo");
        Date dataAbertura = result.getDate("data_abertura");
        String status = result.getString("status_conta");
        String enderecoCarteira = result.getString("endereco_carteira");
        String idCliente = result.getString("id_cliente");
        String idCorretora = result.getString("id_corretora");
        return new Conta(id, senha, tipoConta, saldo, dataAbertura, status, enderecoCarteira, idCliente, idCorretora);
    }

    public void updateAccount(Conta conta) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("update contas_cripto set password = ?, tipo_conta = ?, saldo = ?, data_abertura = ?, status_conta = ?, endereco_carteira = ?, id_cliente = ?, id_corretora = ? where nr_conta = ?");
        stm.setString(1, conta.getSenhaConta());
        stm.setString(2, conta.getTipoConta());
        stm.setDouble(3, conta.getSaldo());
        stm.setDate(4,conta.getDataCriacao());
        stm.setString(5, conta.getStatusConta());
        stm.setString(6, conta.getEnderecoCarteira());
        stm.setString(7, conta.getIdCliente().toString());
        stm.setString(8, conta.getIdCorretora().toString());
        stm.setString(9, conta.getNrConta());
        stm.executeUpdate();
    }

    public void deleteAccount(String id_conta) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("delete from contas_cripto where nr_conta = ?");
        stm.setString(1, id_conta);
        int linha = stm.executeUpdate();
        if (linha == 0)
            throw new SQLException("Conta não encontrada para ser removida");
    }
}
