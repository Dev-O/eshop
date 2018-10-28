package com.eshop.core.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.core.product.model.Product;



public interface ProductRepository extends JpaRepository<Product, Long> {

}
