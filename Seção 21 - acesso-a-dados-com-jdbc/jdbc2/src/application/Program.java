package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;


public class Program {

	public static void main(String[] args) {
		
		Connection conn = null; //Conectar ao banco
		Statement st = null; //Consulta ao SQL
		ResultSet rs = null; //Armazena o resultado da consulta
		
		try {
			conn = DB.getConnection();
			
			st = conn.createStatement();
			//rs = st.executeQuery("select *from department");
			rs = st.executeQuery("select Id, Name, Email from seller");
			
			while (rs.next()) {
				//System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
				System.out.println(rs.getInt("Id") + ", " + rs.getString("Name") + ", " + rs.getString("Email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();			
		}

	}

}