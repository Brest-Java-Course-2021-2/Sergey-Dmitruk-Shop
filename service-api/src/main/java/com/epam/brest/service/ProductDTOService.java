package com.epam.brest.service;

import com.epam.brest.Product;
import com.epam.brest.dto.ProductDto;

import java.time.LocalDate;
import java.util.List;

public interface ProductDTOService {
    List<ProductDto> sortedProductsByDate(LocalDate from, LocalDate to) ;

}
