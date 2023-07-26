 package aplicacao;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Pessoa;

public class Programa {
		public static void main(String[] args) {
				
		/* Primeira execução
		Pessoa p1 = new Pessoa(null, "Carlos da Slva", "carlos@gmail.com");
		Pessoa p2 = new Pessoa(null, "Joaquim Torres", "joaquim@gmail.com");
		Pessoa p3 = new Pessoa(null, "Ana Maria", "ana@gmail.com");*/
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		
		/* Primeira execução
		 * em.getTransaction().begin(); // Toda vez que realizar alguma alteração no banco deve inserir
		em.persist(p1); 				// o getTransaction.begin e .commit()
		em.persist(p2);
		em.persist(p3);
		em.getTransaction().commit();*/
		
		// Segunda execução
		Pessoa p = em.find(Pessoa.class, 3);
		System.out.println(p);
		
		/* Erro
		 * Pessoa p = new Pessoa(2, null, null);		
		*/
		/*Pessoa p = em.find(Pessoa.class, 1);
		em.getTransaction().begin();
		em.remove(p); 
		em.getTransaction().commit();
		*/
			
		System.out.println("Pronto!");
		em.close();
		emf.close();
		
	}

}
