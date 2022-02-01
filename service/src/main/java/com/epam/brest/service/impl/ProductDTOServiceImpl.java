package com.epam.brest.service.impl;

import com.epam.brest.dao.dto.ProductDtoDAO;
import com.epam.brest.dto.ProductDto;
import com.epam.brest.service.ProductDTOService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ProductDTOServiceImpl implements ProductDTOService {


    private ProductDtoDAO productDtoDao;

    public ProductDTOServiceImpl(ProductDtoDAO productDtoDao) {
        this.productDtoDao = productDtoDao;
    }

    @Override
    public List<ProductDto> sortedProductsByDate(LocalDate from, LocalDate to) {
        return productDtoDao.sortedProductsByDate(from, to);
    }

}
