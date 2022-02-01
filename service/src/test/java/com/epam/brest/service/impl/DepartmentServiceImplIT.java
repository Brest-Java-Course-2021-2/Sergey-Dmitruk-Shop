package com.epam.brest.service.impl;

import com.epam.brest.Department;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.config.ServiceTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfiguration.class})
@PropertySource({"classpath:sql-dao.properties"})
@Transactional
class DepartmentServiceImplIT {

    @Autowired
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllDepartments() {
        assertNotNull(departmentService);
        List<Department> departments = departmentService.departmentsFindAll();
        assertTrue(departments.size() > 0);

    }

    @Test
    void delete() {
        assertNotNull(departmentService);
        int departmentSizeBefore = departmentService.count();
        List<Department> departments = departmentService.departmentsFindAll();
        assertTrue(departments.size() > 0);
        departmentService.delete(departments.get(0).getIdDepartment());
        int departmentSizeAfter = departmentService.count();
        assertEquals(departmentSizeBefore, departmentSizeAfter + 1);
    }

    @Test
    void create() {
        assertNotNull(departmentService);
        int departmentSize = departmentService.count();
        assertNotNull(departmentSize);
        Department department = new Department("Test", "ResponsibleTest");
        Integer departmentId =
                departmentService.create(department);
        assertNotNull(departmentId);
        assertEquals(departmentSize, departmentService.count() - 1);
    }

    @Test
    void shouldCount() {
        assertNotNull(departmentService);
        Integer quantity =
                departmentService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
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

    @Test
    void update() {
        assertNotNull(departmentService);
        List<Department> departments = departmentService.departmentsFindAll();
        assertTrue(departments.size() > 0);
        String beforeNameDepartment = departments.get(1).getNameDepartment();
        Department departmentModified =
                departments.get(1);
        departmentModified.setNameDepartment("Test");
        String afterNameDepartment = departments.get(1).getNameDepartment();
        departmentService.update(departmentModified);
        assertNotEquals(beforeNameDepartment, afterNameDepartment);

    }

    @Test
    void shouldGetDepartmentById() {
        Integer id = 1;
        assertNotNull(departmentService);
        Department department = departmentService.getDepartmentById(id);
        String nameDepartment = departmentService.getDepartmentById(id).getNameDepartment();
        assertEquals(department.getNameDepartment(), nameDepartment);

    }


}