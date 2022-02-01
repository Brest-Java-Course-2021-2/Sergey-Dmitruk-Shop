package com.epam.brest.service;

import com.epam.brest.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    Integer createProduct(Product product);
    Integer updateProduct(Product product);
    Integer deleteProduct(Integer idProduct);
    Product getProductById(Integer id);


}
