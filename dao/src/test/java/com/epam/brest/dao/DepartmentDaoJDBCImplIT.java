package com.epam.brest.dao;

import com.epam.brest.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml","classpath*:test-jdbc-config.xml"})
@Transactional
@Rollback
class DepartmentDaoJDBCImplIT {
  private   DepartmentDaoJDBCImp departmentDaoJDBCImp;
    @Autowired
    public DepartmentDaoJDBCImplIT(DepartmentDao departmentDao) {
        this.departmentDaoJDBCImp = (DepartmentDaoJDBCImp) departmentDao;
    }


    private static final Logger LOGGER = LogManager.getLogger(DepartmentDaoJDBCImplIT.class);

    @Test
    void findAll() {
        LOGGER.debug("Execute test: findAll()");
        assertNotNull(departmentDaoJDBCImp);
        assertNotNull(departmentDaoJDBCImp.findAll());
    }

    @Test
    void create(){
        assertNotNull(departmentDaoJDBCImp);
        int departmentSize = departmentDaoJDBCImp.findAll().size();
Department department = new Department("Test");
Integer departmentId =
 departmentDaoJDBCImp.create(department);
        System.out.println(departmentId);
assertNotNull(departmentId);
assertTrue(departmentSize < departmentDaoJDBCImp.findAll().size());
    }
    @Test
    void tryToCreateEqualsDepartments() {
        assertNotNull(departmentDaoJDBCImp);
        Department department = new Department("Dairy");

        assertThrows(IllegalArgumentException.class, () -> {
            departmentDaoJDBCImp.create(department);
            //departmentDaoJDBCImp.create(department);
        });
    }
    @Test
    void shouldCount(){
        assertNotNull(departmentDaoJDBCImp);
        Integer quantity  =
                departmentDaoJDBCImp.count();
assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(2), quantity);
    }



}