package com.fiap.br.dao;

import com.fiap.br.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckTablesExistence {

    private Connection connection;

    public CheckTablesExistence() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }

    public String[] tablesToCheck = {
            "ENDERECO",
            "CLIENTES",
            "CORRETORA",
            "CONTAS_CRIPTO",
            "TRANSACAO_CRIPTO",
            "CRIPTOMOEDAS",
            "HISTORICO_PRECOS"
    };

    public boolean checkTableExists(String tableName) throws SQLException {
        String checkTableSQL = "SELECT 1 FROM USER_TABLES WHERE TABLE_NAME = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkTableSQL)) {
            checkStmt.setString(1, tableName.toUpperCase()); // Table names are usually stored in uppercase
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Table " + tableName + " exists.");
                return true;
            } else {
                System.out.println("Table " + tableName + " does not exist.");
                return false;
            }
        }
    }

}
