package com.epam.brest.dao.dto;

import com.epam.brest.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductDtoDAO {
    List<Product> sortedProductsByDate(LocalDate from, LocalDate to);
}
