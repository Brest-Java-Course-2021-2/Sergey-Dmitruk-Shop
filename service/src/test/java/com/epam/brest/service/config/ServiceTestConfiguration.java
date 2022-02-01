package com.epam.brest.service.config;

import com.epam.brest.dao.*;
import com.epam.brest.dao.dto.DepartmentDtoDAO;
import com.epam.brest.dao.dto.ProductDtoDAO;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.ProductService;
import com.epam.brest.service.impl.DepartmentDtoServiceImpl;
import com.epam.brest.service.impl.DepartmentServiceImpl;
import com.epam.brest.service.impl.ProductDTOServiceImpl;
import com.epam.brest.service.impl.ProductServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.epam.brest.testdb.SpringJdbcConfig;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class ServiceTestConfiguration extends SpringJdbcConfig{


        @Bean
        DepartmentDtoDAO departmentDtoDAO() {
            return new DepartmentDtoDAOJDBC(namedParameterJdbcTemplate());
        }

        @Bean
        DepartmentDtoServiceImpl departmentDTOService() {
            return new DepartmentDtoServiceImpl(departmentDtoDAO());
        }

        @Bean
        DepartmentDao departmentDao() {
            return new DepartmentDaoJDBCImp(namedParameterJdbcTemplate());
        }

        @Bean
        DepartmentService departmentService() {
            return new DepartmentServiceImpl(departmentDao());
        }

        @Bean
    ProductDao productDao(){
            return new ProductDaoJDBCImpl(namedParameterJdbcTemplate());
        }
        @Bean
    ProductService productService(){
            return new ProductServiceImpl(productDao());
        }

        @Bean
    ProductDtoDAO productDtoDAO(){
            return new ProductDtoDAOJDBC(namedParameterJdbcTemplate());
        }
        @Bean
    ProductDTOServiceImpl productDTOService(){
            return new ProductDTOServiceImpl(productDtoDAO());
        }

}
