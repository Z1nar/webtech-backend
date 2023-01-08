package com.htw.be.demo.controller;


import com.htw.be.demo.model.Product;
import com.htw.be.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")

public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            return new ResponseEntity<>(productData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = productRepository.save(product);
            return ResponseEntity.status(201).body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setName(product.getName());
            _product.setDescription(product.getDescription());
            _product.setPrice(product.getPrice());
            _product.setPublished(product.isPublished());
            return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        try {
            Product currentProduct = productRepository.findById(id).get();
            if (currentProduct == null) {
                return ResponseEntity.status(204).body(null);
            }
            productRepository.delete(currentProduct);
            return ResponseEntity.status(200).body(currentProduct);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @DeleteMapping("/products")
    public ResponseEntity<String> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return ResponseEntity.status(200).body("All products have been deleted!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/products/published")
    public ResponseEntity<List<Product>> findByPublished() {
        try {
            List<Product> products = productRepository.findByPublished(true);
            if (products.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(200).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



}
