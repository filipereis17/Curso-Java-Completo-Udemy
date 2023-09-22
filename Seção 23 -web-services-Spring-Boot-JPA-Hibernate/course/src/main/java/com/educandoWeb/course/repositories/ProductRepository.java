package com.educandoWeb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoWeb.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
