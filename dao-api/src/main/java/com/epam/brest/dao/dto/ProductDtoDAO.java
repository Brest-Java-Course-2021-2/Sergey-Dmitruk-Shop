package com.epam.brest.dao.dto;

import com.epam.brest.Product;
import com.epam.brest.dto.ProductDto;

import java.time.LocalDate;
import java.util.List;

public interface ProductDtoDAO {
    List<ProductDto> sortedProductsByDate(LocalDate from, LocalDate to);
    
}
