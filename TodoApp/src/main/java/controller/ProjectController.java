/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Martarelli
 */
public class ProjectController {
    
    public void save(Project project){
        
        String sql = " INSERT INTO projects ("
                + "name,"
                + "description,"
                + "createdAt,"
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //CRIA CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //CRIA UM PREPAREDSTATEMENT, CLASSE USADA PARA EXECUTAR A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO VALORES NO STATEMENT
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            //EXECUTANDO A QUERY
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar o projeto" + 
                    ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
        
    }
}
