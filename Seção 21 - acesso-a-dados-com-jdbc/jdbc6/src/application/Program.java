package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

    public static void main(String[] args) {
        
        // Inicializa variáveis para conexão e declaração
        Connection conn = null;
        Statement st = null;
        try {
            // Estabelece uma conexão com o banco de dados
            conn = DB.getConnection();
            
            // Desativa o modo de autocommit para iniciar uma transação
            conn.setAutoCommit(false);
            
            // Cria uma declaração para executar comandos SQL
            st = conn.createStatement();
            
            // Executa a primeira atualização de salário e armazena o número de linhas afetadas
            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
            
            // Simulação de erro
            /*int x = 1;
            if (x < 2) {
                throw new SQLException("Fake error");
            }*/
            
            // Executa a segunda atualização de salário e armazena o número de linhas afetadas
            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
            
            // Realiza o commit da transação
            conn.commit();
            
            // Imprime o número de linhas afetadas em cada atualização
            System.out.println("rows1 " + rows1);
            System.out.println("rows2 " + rows2);            
        } catch (SQLException e) {
            try {
                // Em caso de erro, tenta fazer o rollback da transação
                conn.rollback();
                // Lança uma exceção personalizada indicando que a transação foi revertida
                throw new DbException("Transação revertida! Causado por: " + e.getMessage());
            } catch (SQLException e1) {
                // Se ocorrer um erro durante o rollback, lança uma exceção personalizada
                throw new DbException("Erro ao tentar reverter a transação! Causado por: " + e1.getMessage());
            }
        } 
        finally {
            // Fecha a declaração e a conexão com o banco de dados, liberando recursos
            DB.closeStatement(st);
            DB.closeConnection();
        }
        
    }

}