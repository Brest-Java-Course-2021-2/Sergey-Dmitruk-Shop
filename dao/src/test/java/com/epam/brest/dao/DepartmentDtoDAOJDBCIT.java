package com.epam.brest.dao;

import com.epam.brest.dao.dto.DepartmentDtoDAO;
import com.epam.brest.dto.DepartmentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml","classpath*:test-jdbc-config.xml"})
@Transactional
@Rollback
class DepartmentDtoDAOJDBCIT {

    private DepartmentDtoDAOJDBC departmentDtoDAOJDBC;
    @Autowired
    DepartmentDtoDAOJDBCIT(DepartmentDtoDAO departmentDtoDAO){
        this.departmentDtoDAOJDBC = (DepartmentDtoDAOJDBC) departmentDtoDAO;
    }
    private static final Logger logger = LogManager.getLogger(DepartmentDtoDAOJDBCIT.class);


    @Test
    void retAllDepartments() {
logger.debug("Test: retAllDepartments()");
assertNotNull(departmentDtoDAOJDBC);
assertNotNull(departmentDtoDAOJDBC.retAllDepartments());
List<DepartmentDTO> list =departmentDtoDAOJDBC.retAllDepartments();
        assertEquals(3, list.size());
    }
}