package application;

import java.util.Date;
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
        // Testando o método findAll do sellerDao para buscar todos os vendedores no banco de dados
        list = sellerDao.findAll();
        // Iterando sobre a lista de vendedores retornados pela consulta
        for (Seller obj : list) {
            System.out.println(obj);
        }
        
        System.out.println("\n=== TEST 4: seller insert ===");
        // Criando um novo objeto Seller com os dados do novo vendedor a ser inserido
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        // Chamando o método insert do sellerDao para inserir o novo vendedor no banco de dados
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        
        System.out.println("\n=== TEST 5: seller update ===");
        // Buscando um vendedor pelo ID para posterior atualização
        seller = sellerDao.findById(1);
        // Modificando o nome do vendedor
        seller.setName("Martha Waine");
        // Chamando o método update do sellerDao para aplicar as alterações no vendedor
        sellerDao.update(seller);
        System.out.println("Update completed!");
    }

}
