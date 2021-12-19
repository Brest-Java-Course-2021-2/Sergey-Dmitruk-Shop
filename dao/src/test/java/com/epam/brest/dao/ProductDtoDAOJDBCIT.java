package com.epam.brest.dao;

import com.epam.brest.Product;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({ProductDtoDAOJDBC.class})
@PropertySource({"classpath:sql-dao.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = SpringJdbcConfig.class)
@Transactional
@Rollback
class ProductDtoDAOJDBCIT {

    private ProductDtoDAOJDBC dtoDaoJDBC;

    @Autowired
    public ProductDtoDAOJDBCIT(ProductDtoDAOJDBC productDtoDAOJDBC){
        this.dtoDaoJDBC = productDtoDAOJDBC;
    }


    @Test
    void sortedProductsByDate() {
        LocalDate from  = LocalDate.of(2021,10, 1);
        LocalDate to = LocalDate.of(2021,10,20);
        assertNotNull(dtoDaoJDBC);
        List<Product> products = dtoDaoJDBC.sortedProductsByDate(from,to);
        assertNotNull(products);

    }
}