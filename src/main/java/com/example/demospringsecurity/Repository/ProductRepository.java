package com.example.demospringsecurity.Repository;

import com.example.demospringsecurity.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
