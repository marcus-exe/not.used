package com.fiap.br.dao;

import com.fiap.br.factory.ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitSQL {

    String sqlFilePath = "back_tio.sql";
    private Connection conection;
    private Statement statement;

    public InitSQL() throws SQLException {
        conection = ConnectionFactory.getConnection();
        statement = conection.createStatement();
    }

    public void initSQL() throws SQLException, IOException {
        StringBuilder sql = getStringBuilder(sqlFilePath);

        String[] sqlStatements = sql.toString().split(";");

        for (String sqlStatement : sqlStatements) {
            if (!sqlStatement.trim().isEmpty()) {
                statement.executeUpdate(sqlStatement.trim());
            }
        }
        System.out.println("Table created successfully!");
    }

    private static StringBuilder getStringBuilder(String sqlFilePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(sqlFilePath));
        StringBuilder sql = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sql.append(line).append("\n");
        }
        br.close();
        return sql;
    }

    public void closeConection() throws SQLException {
        conection.close();
    }
}

