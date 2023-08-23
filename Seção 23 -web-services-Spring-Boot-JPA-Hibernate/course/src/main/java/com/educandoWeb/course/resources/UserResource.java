package com.educandoWeb.course.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoWeb.course.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	// Endpoint para buscar todos os usuários
	@GetMapping
	public ResponseEntity<User> findAll() {
		// Criando um usuário de exemplo
		User u = new User(1L, "Maria", "maria@gmail.com", "999999999", "12345");
		
		// Retornando o usuário como resposta da requisição
		return ResponseEntity.ok().body(u);
	}
}
