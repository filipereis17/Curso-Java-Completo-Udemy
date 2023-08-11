package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// Preparando um PreparedStatement para a inserção de um novo vendedor
		PreparedStatement st = null;
		try {
		    st = conn.prepareStatement (
		        "INSERT INTO seller "
		        + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
		        + "VALUES "
		        + "(?, ?, ?, ?, ?)",
		        Statement.RETURN_GENERATED_KEYS); // Obtendo as chaves geradas (como o ID gerado) após a inserção
		    
		    // Configurando os parâmetros do PreparedStatement com os valores do novo vendedor
		    st.setString(1, obj.getName());
		    st.setString(2, obj.getEmail());
		    st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
		    st.setDouble(4, obj.getBaseSalary());
		    st.setInt(5, obj.getDepartment().getId());
		    
		    // Executando a inserção no banco de dados e obtendo o número de linhas afetadas
		    int rowsAffected = st.executeUpdate();
		    
		    // Verificando se houve linhas afetadas (ou seja, se a inserção foi bem-sucedida)
		    if (rowsAffected > 0) {
		        // Obtendo as chaves geradas (incluindo o ID) após a inserção
		        ResultSet rs = st.getGeneratedKeys();
		        if (rs.next()) {
		            int id = rs.getInt(1); // Obtendo o ID gerado
		            obj.setId(id); // Configurando o ID gerado no objeto vendedor
		        }
		        // Fechando o ResultSet das chaves geradas
		        DB.closeResultSet(rs);
		    } else {
		        // Se nenhuma linha foi afetada, lançar uma exceção indicando um erro inesperado
		        throw new DbException("Unexpected error! No rows affected!");
		    }
		} catch (Exception e) {
		    // Capturando qualquer exceção que ocorra durante a inserção e lançando uma exceção personalizada
		    throw new DbException(e.getMessage());
		} finally {
		    // Finalmente, garantindo que o PreparedStatement seja fechado para liberar recursos
		    DB.closeStatement(st);
		}	
	}

	@Override
	public void update(Seller obj) {
				PreparedStatement st = null;
				try {
				    st = conn.prepareStatement (
				    		"UPDATE seller "
				    		+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
				    		+ "WHERE Id = ?");
				    
				    // Configurando os parâmetros do PreparedStatement com os valores do novo vendedor
				    st.setString(1, obj.getName());
				    st.setString(2, obj.getEmail());
				    st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
				    st.setDouble(4, obj.getBaseSalary());
				    st.setInt(5, obj.getDepartment().getId());
				    st.setInt(6, obj.getId());
				    
				    st.executeUpdate();
				} catch (Exception e) {
				    // Capturando qualquer exceção que ocorra durante a inserção e lançando uma exceção personalizada
				    throw new DbException(e.getMessage());
				} finally {
				    // Finalmente, garantindo que o PreparedStatement seja fechado para liberar recursos
				    DB.closeStatement(st);
				}	
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");			
			st.setInt(1, id);			
			
			int rows = st.executeUpdate();
			
			if (rows == 0) {
				throw new Exception("Non-existent seller");
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		// Preparando um PreparedStatement para buscar um vendedor por ID no banco de dados
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
		    st = conn.prepareStatement(
		        "SELECT seller.*, department.Name as DepName "
		        + "FROM seller INNER JOIN department "
		        + "ON seller.DepartmentId = department.Id "
		        + "WHERE seller.Id = ?"
		    );
		    // Configurando o parâmetro do PreparedStatement com o ID do vendedor
		    st.setInt(1, id);
		    
		    // Executando a consulta no banco de dados e obtendo o resultado
		    rs = st.executeQuery();
		    
		    // Verificando se há um resultado (ou seja, se encontramos um vendedor com o ID especificado)
		    if (rs.next()) {
		        // Instanciando um objeto Department com os detalhes do departamento associado
		        Department dep = instantiateDepartment(rs);
		        // Instanciando um objeto Seller com os detalhes do vendedor encontrado e o departamento associado
		        Seller obj = instantiateSeller(rs, dep);
		        // Retornando o objeto Seller encontrado
		        return obj;
		    }
		    // Se não houver resultado, retornar null (nenhum vendedor encontrado)
		    return null;
		} catch (SQLException e) {
		    // Capturando qualquer exceção que ocorra durante a consulta e lançando uma exceção personalizada
		    throw new DbException(e.getMessage());
		} finally {
		    // Finalmente, garantindo que o PreparedStatement e o ResultSet sejam fechados para liberar recursos
		    DB.closeStatement(st);
		    DB.closeResultSet(rs);
		}		
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        // Preparando a consulta SQL para selecionar vendedores com o departamento correspondente
	        st = conn.prepareStatement(
	            "SELECT seller.*, department.Name as DepName "
	            + "FROM seller INNER JOIN department "
	            + "ON seller.DepartmentId = department.Id "
	            + "ORDER BY Name"
	        );
	        
	        // Executando a consulta
	        rs = st.executeQuery();
	        
	        // Lista para armazenar os vendedores encontrados
	        List<Seller> list = new ArrayList<>();
	        // Mapa para armazenar departamentos e evitar duplicações
	        Map<Integer, Department> map = new HashMap<>();
	        
	        while (rs.next()) {
	            // Verificando se o departamento já foi processado
	            Department dep = map.get(rs.getInt("DepartmentId"));
	            
	            if (dep == null) {
	                // Se o departamento ainda não estiver no mapa, instancie-o
	                dep = instantiateDepartment(rs);
	                map.put(rs.getInt("DepartmentId"), dep);
	            }
	            
	            // Instanciando o vendedor com informações do ResultSet e o departamento associado
	            Seller obj = instantiateSeller(rs, dep);
	            list.add(obj);                
	        }
	        // Retornando a lista de vendedores com seus respectivos departamentos
	        return list;
	    } catch (SQLException e) {
	        // Em caso de exceção SQL, lançar uma exceção personalizada de banco de dados
	        throw new DbException(e.getMessage());
	    } finally {
	        // Fechando o statement e o ResultSet para liberar recursos
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    try {
	        // Preparando a consulta SQL para selecionar vendedores com o departamento correspondente
	        st = conn.prepareStatement(
	            "SELECT seller.*, department.Name as DepName "
	            + "FROM seller INNER JOIN department "
	            + "ON seller.DepartmentId = department.Id "
	            + "WHERE DepartmentId = ? "
	            + "ORDER BY Name"
	        );
	        
	        // Definindo o valor do parâmetro na consulta SQL
	        st.setInt(1, department.getId());
	        // Executando a consulta
	        rs = st.executeQuery();
	        
	        // Lista para armazenar os vendedores encontrados
	        List<Seller> list = new ArrayList<>();
	        // Mapa para armazenar departamentos e evitar duplicações
	        Map<Integer, Department> map = new HashMap<>();
	        
	        while (rs.next()) {
	            // Verificando se o departamento já foi processado
	            Department dep = map.get(rs.getInt("DepartmentId"));
	            
	            if (dep == null) {
	                // Se o departamento ainda não estiver no mapa, instancie-o
	                dep = instantiateDepartment(rs);
	                map.put(rs.getInt("DepartmentId"), dep);
	            }
	            
	            // Instanciando o vendedor com informações do ResultSet e o departamento associado
	            Seller obj = instantiateSeller(rs, dep);
	            list.add(obj);                
	        }
	        // Retornando a lista de vendedores com seus respectivos departamentos
	        return list;
	    } catch (SQLException e) {
	        // Em caso de exceção SQL, lançar uma exceção personalizada de banco de dados
	        throw new DbException(e.getMessage());
	    } finally {
	        // Fechando o statement e o ResultSet para liberar recursos
	        DB.closeStatement(st);
	        DB.closeResultSet(rs);
	    }
	}
}