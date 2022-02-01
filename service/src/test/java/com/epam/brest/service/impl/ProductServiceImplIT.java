package com.epam.brest.service.impl;


import com.epam.brest.Product;
import com.epam.brest.service.ProductService;
import com.epam.brest.service.config.ServiceTestConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfiguration.class})
@PropertySource({"classpath:sql-dao.properties"})
@Transactional
public class ProductServiceImplIT {

    Logger logger = LogManager.getLogger(ProductServiceImplIT.class);

    @Autowired
    ProductService productService;


    @Test
    void findAllProduct() {
        logger.debug("findAllProduct()");
        assertNotNull(productService);
        List<Product> products = productService.findAllProduct();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    void createProduct() {
        logger.debug("createProduct()");
        assertNotNull(productService);
        List<Product> products = productService.findAllProduct();
        LocalDate date = LocalDate.of(2021, 1, 1);

        assertNotNull(products);
        Integer sizeBefore =
                products.size();
        Product product = new Product("Test", "Dairy", date.toString(), 100);
        productService.createProduct(product);
        Integer sizeAfter = productService.findAllProduct().size();
        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @Test
    void updateProduct() {
        logger.debug("updateProduct()");
        assertNotNull(productService);
        Product product = productService.getProductById(1);
        String newName = "NewTestName";
        String newDepName = "Butcher";
        product.setNameProduct(newName);
        product.setParentDepartmentName(newDepName);
        productService.updateProduct(product);
        assertEquals(newName, product.getNameProduct());
        assertEquals(newDepName, product.getParentDepartmentName());
    }

    @Test
    void deleteProduct() {
        logger.debug("deleteProduct()");
        assertNotNull(productService);
        List<Product> products = productService.findAllProduct();

        assertNotNull(products);
        Integer sizeBefore =
                products.size();
        productService.deleteProduct(1);
        Integer sizeAfter = productService.findAllProduct().size();
        assertEquals(sizeBefore - 1, sizeAfter);

    }

    @Test
    void getProductById() {
        logger.debug("getProductById() ");
        assertNotNull(productService);
        Integer id = 1;
        Product product = productService.getProductById(id);
        assertNotNull(product);
        assertEquals(id, product.getIdProduct());
    }
}
