package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		// Criando uma instância do SellerDao usando a fábrica DaoFactory
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        // Testando o método findById do SellerDao para buscar um vendedor por ID
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findByDepartment ===");
        // Criando um departamento com ID 2 para usar como filtro na busca
        Department department = new Department(2, null);
        // Testando o método findByDepartment do SellerDao para buscar vendedores por departamento
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }
        
        System.out.println("\n=== TEST 3: seller findAll ===");
        // Chamando o método findAll do sellerDao para buscar todos os vendedores no banco de dados
        list = sellerDao.findAll();
        // Iterando sobre a lista de vendedores retornados pela consulta
        for (Seller obj : list) {
            System.out.println(obj);
        }
    }

}
