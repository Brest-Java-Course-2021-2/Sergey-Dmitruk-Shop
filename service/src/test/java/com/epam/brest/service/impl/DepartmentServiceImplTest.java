package com.epam.brest.service.impl;

import com.epam.brest.Department;
import com.epam.brest.dao.DepartmentDtoDAOJDBC;
import com.epam.brest.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class DepartmentServiceImplTest {

    @Autowired
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
        assertNotNull(departmentService);
        int departmentSize = departmentService.count();
        assertNotNull(departmentSize);
        Department department = new Department("Test");
        Integer departmentId =
                departmentService.create(department);
        assertNotNull(departmentId);
        assertEquals(departmentSize, departmentService.count() - 1);
    }

    @Test
    void shouldCount(){
        assertNotNull(departmentService);
        Integer quantity  =
                departmentService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(2), quantity);
    }

    @Test
    void tryToCreateEqualsDepartments() {
        assertNotNull(departmentService);
        Department department = new Department("Dairy");

        assertThrows(IllegalArgumentException.class, () -> {
            departmentService.create(department);
            //departmentDaoJDBCImp.create(department);
        });
    }

}