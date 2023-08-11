package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

//Declaração da classe DaoFactory, responsável por criar instâncias dos DAOs
public class DaoFactory {

 // Método estático que cria e retorna uma instância de SellerDao
 public static SellerDao createSellerDao() {
     // Cria uma nova instância da classe de implementação SellerDaoJDBC
     return new SellerDaoJDBC(DB.getConnection());
 }
 
 public static DepartmentDao createDepartmentDao() {
	 return new DepartmentDaoJDBC(DB.getConnection());
 }
}
