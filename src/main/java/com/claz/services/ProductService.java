package com.claz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.claz.models.Product;

@Service
public interface ProductService {

<<<<<<< HEAD
    private final ProductRepository productRepository;
    public List<Product> getProductsByCategoryId(int Category_ID) {
        return productRepository.findAllByCategoryId(Category_ID);
    }
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
//public List<Product> getAllNameOfProduct(String name) {
//        return productRepository.findByName(name);
//}
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
=======
	List<Product> findAll();

	Product finById(int id);

	List<Product> findByCategoryId(int cid);

	Product create(Product product);

	Product update(Product product);

	void delete(int id);
}
>>>>>>> cefbaa9380fbe81f6f5454181f197dfe67734ff9
