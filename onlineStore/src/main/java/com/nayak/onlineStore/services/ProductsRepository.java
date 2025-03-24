package com.nayak.onlineStore.services;

import com.nayak.onlineStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
    
}
