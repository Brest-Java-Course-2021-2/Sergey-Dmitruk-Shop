package com.epam.brest.dao;

import com.epam.brest.Department;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({DepartmentDaoJDBCImp.class})
@PropertySource({"classpath:sql-dao.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = SpringJdbcConfig.class)
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
Department department = new Department("Test","testResponsible");
Integer departmentId =
 departmentDaoJDBCImp.create(department);
        System.out.println(departmentId);
assertNotNull(departmentId);
assertTrue(departmentSize < departmentDaoJDBCImp.findAll().size());
        assertEquals("testResponsible",departmentDaoJDBCImp.getDepartmentById(departmentId).getResponsible());
    }
    @Test
    void tryToCreateEqualsDepartments() {
        assertNotNull(departmentDaoJDBCImp);
        Department department = new Department("Butcher","Test");

        assertThrows(IllegalArgumentException.class, () -> {
            departmentDaoJDBCImp.create(department);
        });
    }
    @Test
    void shouldCount(){
        assertNotNull(departmentDaoJDBCImp);
        Integer quantity  =
                departmentDaoJDBCImp.count();
assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
    @Test
    void updateDepartment(){
        assertNotNull(departmentDaoJDBCImp);
        List<Department> departments = departmentDaoJDBCImp.findAll();
        assertTrue(departments.size() > 0);

        Department departmentSrc = departments.get(0);
        departmentSrc.setNameDepartment("Test");
        departmentSrc.setResponsible("test");
        departmentDaoJDBCImp.update(departmentSrc);

        Department departmentDst = departmentDaoJDBCImp.getDepartmentById(departmentSrc.getIdDepartment());
        assertEquals(departmentSrc.getNameDepartment(), departmentDst.getNameDepartment());
        assertEquals(departmentSrc.getResponsible(), departmentDst.getResponsible());

    }
    @Test
    void getDepartmentById(){
        assertNotNull(departmentDaoJDBCImp);
        List<Department> departments = departmentDaoJDBCImp.findAll();
        assertTrue(departments.size() > 0);
        Department departmentSrc = departments.get(0);
       Department departmentDst  = departmentDaoJDBCImp.getDepartmentById(departmentSrc.getIdDepartment());
assertEquals(departmentSrc.getNameDepartment(), departmentDst.getNameDepartment());
    }

    @Test
    void deleteDepartment(){
        assertNotNull(departmentDaoJDBCImp);
        departmentDaoJDBCImp.create(new Department("Test", "testResponsible"));
        List<Department> departments = departmentDaoJDBCImp.findAll();
        assertTrue(departments.size() > 0);
        departmentDaoJDBCImp.delete(departments.get(0).getIdDepartment());
        assertEquals(departments.size() - 1, departmentDaoJDBCImp.findAll().size());
    }



}