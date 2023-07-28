package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;


public class Program {

	public static void main(String[] args) {
		
		Connection conn = null; //Conectar ao banco
		Statement st = null; 	//Consulta ao SQL
		ResultSet rs = null;	//Armazena o resultado da consulta
		try {
			conn = DB.getConnection();
			
			// Instanciar um objeto do tipo Statement
			st = conn.createStatement();
			
			// st já instanciada
			// rs recebe o resultado
			rs = st.executeQuery("select * from department"); // método espera uma string
			
			//rs começa na posição 0
			while(rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			// Fechar conexão por método para evitar ter que criar try catch para cada um
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}

	}

}