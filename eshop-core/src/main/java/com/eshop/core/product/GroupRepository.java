package com.eshop.core.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eshop.core.product.model.Product;
import com.eshop.core.product.model.ProductGroup;


public interface GroupRepository extends JpaRepository<ProductGroup, Long> { }