package com.epam.brest.service.impl.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class ProductNotFoundException extends EmptyResultDataAccessException {
    public ProductNotFoundException(Integer id) {
        super("Product not found for id " + id,1);
    }
}
