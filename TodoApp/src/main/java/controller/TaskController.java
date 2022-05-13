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
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Martarelli
 */
public class TaskController {
    
    public void save(Task task) {
        
        String sql = "INSERT INTO tasks ("
                + "idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?,?,?,?,?,?,?,?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //ESTABELECENDO CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO VALORES NO STATEMENT
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            //EXECUTANDO A QUERY
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" + 
                    ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public void update(Task task) {
        
        String sql = "UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ? "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //ESTABELECENDO CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO VALORES NO STATEMENT
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            //EXECUTANDO A QUERY
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + 
                    ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public void removeById(int taskId) throws SQLException {
        
        String sql = "DELETE FROM tasks WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //ESTABELECENDO CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO VALORES NO STATEMENT
            statement.setInt(1, taskId);
            //EXECUTANDO A QUERY
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public List<Task> getAll(int idProject) {
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de tarefas que será devolvida quando a chamda do método acontecer
        List<Task> tasks = new ArrayList<>();
        
        try {
            //ESTABELECENDO CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO VALORES NO STATEMENT
            statement.setInt(1, idProject);
            //EXECUTANDO A QUERY
            resultSet = statement.executeQuery();
            
            //ENQUANTO HOUVEREM VALORES A SEREM PERCORRIDOS NO RESULTSET
            while(resultSet.next()){
            
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
            
            }
                    
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao carregar as tarefas" 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        //LISTA DE TAREFAS QUE FOI CRIADA E CARREGADA DO BANCO DE DADOS
        return tasks;
    }
}
