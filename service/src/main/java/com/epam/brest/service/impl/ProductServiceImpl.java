package com.epam.brest.service.impl;

import com.epam.brest.Product;
import com.epam.brest.dao.ProductDao;
import com.epam.brest.service.ProductService;
import com.epam.brest.service.impl.exception.ProductNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);

    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAllProduct() {
        LOGGER.debug("findAllProduct()");
        return productDao.findAllProduct();
    }

    @Override
    public Integer createProduct(Product product) {
        LOGGER.debug("createProduct({})", product);
        return productDao.createProduct(product);
    }

    @Override
    public Integer updateProduct(Product product) {
        LOGGER.debug("updateProduct({})", product);
        return productDao.updateProduct(product);
    }

    @Override
    public Integer deleteProduct(Integer idProduct) {
        LOGGER.debug("deleteProduct({})", idProduct);
        return productDao.deleteProduct(idProduct);
    }

    @Override
    public Product getProductById(Integer id) {
        LOGGER.debug("getProductById({})", id);
        try {
            return productDao.getProductById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ProductNotFoundException(id);
        }

    }


}
