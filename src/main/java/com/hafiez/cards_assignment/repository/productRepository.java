package com.hafiez.cards_assignment.repository;

import com.hafiez.cards_assignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}