package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;


public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Formata a data
		Connection conn = null;
		PreparedStatement st = null; // Permite montar consulta SQL
		try {
			conn = DB.getConnection();
			
			/*
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime())); // Abaixo
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			*/
			
			st = conn.prepareStatement(
					"insert into department (Name) values ('D1'),('D2')",
					Statement.RETURN_GENERATED_KEYS);
			
			int rowsAffected = st.executeUpdate(); //retorna um valor inteiro
						
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys(); // retorna um objeto do tipo ResultSet
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			} else {
				System.out.println("No rown affected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		/*catch (ParseException e) {
			e.printStackTrace();
		} */
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}

}

/*
 * 		sdf.parse("22/04/1985"): Essa parte do código está analisando a string "22/04/1985" 
 * para criar um objeto java.util.Date. Esse objeto representa a data 22 de abril de 1985, 
 * mas não possui informações sobre horas, minutos e segundos.
 * 		new java.sql.Date(...): Essa parte está criando um objeto 
 * 		java.sql.Date a partir do objeto java.util.Date criado anteriormente. O construtor 
 * java.sql.Date(long date) espera um valor em milissegundos para inicializar a data SQL. 
 * A chamada ao método getTime() do objeto java.util.Date retorna o valor em milissegundos 
 * correspondente a essa data.
*/