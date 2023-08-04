package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {
		
		// Inicializa as variáveis para conexão com o banco de dados e declaração preparada
		Connection conn = null;
		PreparedStatement st = null;
		try {
			// Estabelece a conexão com o banco de dados
			conn = DB.getConnection();
			
			// Cria uma declaração preparada para excluir um departamento
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE "
					+ "Id = ?");
			
			//st.setInt(1, 5);
			st.setInt(1, 2);
			
			// Executa a deleção e obtém o número de linhas afetadas
			int rowsAffected = st.executeUpdate();
			
			// Imprime o resultado, mostrando quantas linhas foram afetadas
			System.out.println("Done! Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			// Lança erro personalizado caso exista problema de integridade
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}

}