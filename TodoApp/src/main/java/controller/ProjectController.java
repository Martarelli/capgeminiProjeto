/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Martarelli
 */
public class ProjectController {

    public void save(Project project) {

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
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Project project) {

        String sql = "UPDATE projects SET "
                + "name= ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updateAt = ? "
                + "WHERE id = ?";

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
            statement.setInt(5, project.getId());
            //EXECUTANDO A QUERY
            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {

        String sql = "SELECT * FROM projects";

        List<Project> projects = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet resultSet = null;

        try {
            //ESTABELECENDO CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);

            //EXECUTANDO A QUERY
            resultSet = statement.executeQuery();

            //ENQUANTO HOUVEREM VALORES A SEREM PERCORRIDOS NO RESULTSET
            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao carregar os projetos"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        //LISTA DE TAREFAS QUE FOI CRIADA E CARREGADA DO BANCO DE DADOS
        return projects;
    }

    public void removeById(int idProject) {

        String sql = " DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //CRIA CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //CRIA UM PREPAREDSTATEMENT, CLASSE USADA PARA EXECUTAR A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO VALORES NO STATEMENT
            statement.setInt(1, idProject);
            //EXECUTANDO A QUERY
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }
}
