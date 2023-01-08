package com.htw.be.demo.repository;

import com.htw.be.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findByNameContaining(String name);

    List<Product> findByPriceGreaterThan(Double price);

    List<Product> findByPublished(boolean published);

    List<Product> findByPriceBetween(Double price1, Double price2);

    List<Product> findByPriceLessThan(Double price);

    List<Product> findByOrderByPriceAsc();

    List<Product> findByOrderByPriceDesc();

}

