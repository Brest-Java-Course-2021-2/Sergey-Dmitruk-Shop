package com.epam.brest.service.dto;

import com.epam.brest.dto.ProductDto;
import com.epam.brest.service.ProductDTOService;
import com.epam.brest.service.config.ServiceTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfiguration.class})
@PropertySource({"classpath:sql-dao.properties"})
@Transactional
class ProductDTOServiceImplTest {
    @Autowired
    ProductDTOService productDTOService;

    @Test
    void sortedProductsByDate() {
        assertNotNull(productDTOService);
        LocalDate from = LocalDate.of(2021, 10, 1);
        LocalDate to = LocalDate.of(2021, 10, 20);
        List<ProductDto> products = productDTOService.sortedProductsByDate(from, to);
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }
}