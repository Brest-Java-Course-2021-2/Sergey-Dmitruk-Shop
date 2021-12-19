package com.epam.brest.dao;

import com.epam.brest.Department;
import com.epam.brest.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAllProduct();
    Integer createProduct(Product product);
    Integer updateProduct(Product product);
    Integer deleteProduct(Integer idProduct);
    Product getProductById(Integer id);
    Department getIdDepartmentByName(String nameDepartment);

}
