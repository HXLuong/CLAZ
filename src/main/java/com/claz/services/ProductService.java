package com.claz.services;

import com.claz.repository.ProductRepository;
import com.claz.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//
//    public Product getProductById(int id) {
//        return productRepository.findById(id).get();
//    }
//
//    public void saveProduct(Product product) {
//        productRepository.save(product);
//    }
//
//    public void deleteProduct(int id) {
//        productRepository.deleteById(id);
//    }
}