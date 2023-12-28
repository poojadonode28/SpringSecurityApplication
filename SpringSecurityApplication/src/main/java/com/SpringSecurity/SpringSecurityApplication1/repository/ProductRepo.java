package com.SpringSecurity.SpringSecurityApplication1.repository;

import com.SpringSecurity.SpringSecurityApplication1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {

}
