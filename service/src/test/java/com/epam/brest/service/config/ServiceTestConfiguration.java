package com.epam.brest.service.config;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.dao.DepartmentDaoJDBCImp;
import com.epam.brest.dao.DepartmentDtoDAOJDBC;
import com.epam.brest.dao.dto.DepartmentDtoDAO;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.impl.DepartmentDtoServiceImpl;
import com.epam.brest.service.impl.DepartmentServiceImpl;
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
}
