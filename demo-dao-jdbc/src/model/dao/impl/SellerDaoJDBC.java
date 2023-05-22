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
					+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) { //Percorre tabela
				Department dep = instantiateDepartment(rs);				
				Seller obj = instantiateSeller(rs, dep);				
				return obj;				
			}
			return null;
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller(); // Mesmo processo com Vendedor
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthdate(rs.getDate("BirthDate"));
		obj.setDepartment(dep); //Department pega o objeto criado acima
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); //Cria um objeto para receber dados do DB
		dep.setId(rs.getInt("DepartmentId")); //Percorre a tabela e pega um int
		dep.setName(rs.getString("DepName")); //Percorre a tabela e pega uma String
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");

			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>(); //Cria uma lista para receber dados da tabela
			Map<Integer, Department> map = new HashMap<>(); //Evita repetições do Id do regitstro
			
			while (rs.next()) { //Percorre tabela
				
				Department dep = map.get(rs.getInt("DepartmentId")); //Retorna a Id do departamento a partir do map
				if (dep == null) { //Verifica se já foi instanciado
					dep = instantiateDepartment(rs); //Primeira instanciamento adiciona
					map.put(rs.getInt("DepartmentId"), dep); //Salva no map
				}
						
				Seller obj = instantiateSeller(rs, dep);	
				list.add(obj);				
			}
			return list;
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>(); //Cria uma lista para receber dados da tabela
			Map<Integer, Department> map = new HashMap<>(); //Evita repetições do Id do regitstro
			
			while (rs.next()) { //Percorre tabela
				
				Department dep = map.get(rs.getInt("DepartmentId")); //Retorna a Id do departamento a partir do map
				if (dep == null) { //Verifica se já foi instanciado
					dep = instantiateDepartment(rs); //Primeira instanciamento adiciona
					map.put(rs.getInt("DepartmentId"), dep); //Salva no map
				}
						
				Seller obj = instantiateSeller(rs, dep);	
				list.add(obj);				
			}
			return list;
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
