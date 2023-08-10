package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
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