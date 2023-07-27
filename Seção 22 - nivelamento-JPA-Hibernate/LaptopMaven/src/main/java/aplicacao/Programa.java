package aplicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Laptop;

public class Programa {

	public static void main(String[] args) {

		Laptop l1 = new Laptop(null, "Asus", "Predator", 37);
		//Laptop l2 = new Laptop(null, "Acer", "Nitro 5", 80);
		//Laptop l3 = new Laptop(null, "HP", "ProBook", 10);
		
				
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("laptop-jpa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(l1);
		//em.persist(l2);
		//em.persist(l3);
		//Laptop l = em.find(Laptop.class, 5);
		//l.carregar(10);
		//em.remove(l);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		
		
		System.out.println("Sucesso");
		
	}
	

}
