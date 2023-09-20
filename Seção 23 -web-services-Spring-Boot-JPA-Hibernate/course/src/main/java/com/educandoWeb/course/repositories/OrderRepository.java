package com.educandoWeb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoWeb.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
