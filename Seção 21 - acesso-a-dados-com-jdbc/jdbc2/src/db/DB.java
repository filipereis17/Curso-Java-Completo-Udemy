package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	// Criar conexão com o BD
	private static Connection conn = null;
	
	// Método para criar coneção
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props); // url do BD e propriedades de conexão
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
		}
		return conn;
	}
	
	//Método para fechar conexão
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	// Método auxiliar para carregar dados do db.properties
	private static Properties loadProperties() {
		
		// FileInputStream armazena dados do arquivo db.properties
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			
			// Instanciar um objeto do tipo Properties
			Properties props = new Properties();
			props.load(fs); // Carrega arquivo em objeto props 
			return props;
		}
		// Em caso de erro
		catch (IOException e) {
			// Exceção personalizada
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
