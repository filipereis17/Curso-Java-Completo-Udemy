package com.educandoWeb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoWeb.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
