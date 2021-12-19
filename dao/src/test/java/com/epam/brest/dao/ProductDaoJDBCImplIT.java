package com.epam.brest.dao;

import com.epam.brest.Department;
import com.epam.brest.Product;
import com.epam.brest.testdb.SpringJdbcConfig;
import static org.junit.jupiter.api.Assertions.*;

import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Locale;


@DataJdbcTest
@Import({ProductDaoJDBCImpl.class,DepartmentDaoJDBCImp.class})
@PropertySource({"classpath:sql-dao.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = SpringJdbcConfig.class)
@Transactional
@Rollback
class ProductDaoJDBCImplIT {

    private final Logger logger = LogManager.getLogger(ProductDaoJDBCImplIT.class);

    private DepartmentDaoJDBCImp departmentDaoJDBC;

    private ProductDaoJDBCImpl productDaoJDBC;

@Autowired
    public ProductDaoJDBCImplIT(ProductDaoJDBCImpl productDaoJDBC, DepartmentDaoJDBCImp departmentDaoJDBC){
        this.productDaoJDBC = productDaoJDBC;
        this.departmentDaoJDBC = departmentDaoJDBC;
    }


    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllProduct() {
    logger.debug("findAllProduct()");
       assertNotNull(productDaoJDBC);
        List<Product> products = productDaoJDBC.findAllProduct();
        assertTrue(products.size() > 0);
    }

    @Test
    void createProduct(){
        logger.debug("createProduct()");
        LocalDate date = LocalDate.of(2021,1,1);
        Department department1 =
        departmentDaoJDBC.getDepartmentById(1);
        assertNotNull(productDaoJDBC);
        Integer sizeProductsBefore = productDaoJDBC.findAllProduct().size();
        assertNotNull(sizeProductsBefore);
        Product product = new Product("TestNameProduct",department1.getNameDepartment(),date,300);
        productDaoJDBC.createProduct(product);
        Integer sizeProductsAfter = productDaoJDBC.findAllProduct().size();
        assertEquals(sizeProductsBefore + 1, sizeProductsAfter);

    }
    @Test
    void tryToCreateEqualsProducts() {
        logger.debug("tryToCreateEqualsProducts()");
        assertNotNull(productDaoJDBC);
        Product product = new Product("Milk","Dairy");
        assertThrows(IllegalArgumentException.class, () -> {
            productDaoJDBC.createProduct(product);
        });
    }

    @Test
    void tryCreateProductWithNotExistDepartment(){
        logger.debug("tryCreateProductWithNotExistDepartment()");
        assertNotNull(productDaoJDBC);
        Product product = new Product("Test","NotExistDepartment");
        assertNotNull(product);
        assertThrows(IllegalArgumentException.class, () -> {
            productDaoJDBC.createProduct(product);
        });
    }

    @Test
    void getProductById(){
    logger.debug("getProductById()");
     assertNotNull(productDaoJDBC);
     Integer id = 1;
     Product product = productDaoJDBC.getProductById(id);
     String nameProduct = productDaoJDBC.getProductById(id).getNameProduct();
     assertNotNull(product);
     assertEquals(product.getNameProduct(),nameProduct);

    }
    @Test
    void updateProduct(){
    logger.debug("updateProduct()");
    assertNotNull(productDaoJDBC);
    String newName = RandomString.hashOf(20);
    Product product = productDaoJDBC.getProductById(3);
        assertNotNull(product);
        product.setNameProduct(newName);
        product.setParentDepartmentName("Dairy");
    productDaoJDBC.updateProduct(product);
    Product updatedProduct = productDaoJDBC.getProductById(3);
    assertEquals(newName,updatedProduct.getNameProduct());
    }
    @Test
    void deleteProduct(){
        logger.debug("updateProduct()");
        assertNotNull(productDaoJDBC);
        Integer id = 2;
        Integer sizeBefore = productDaoJDBC.findAllProduct().size();
        Product product = productDaoJDBC.getProductById(id);
        assertNotNull(product);
        productDaoJDBC.deleteProduct(id);
        Integer sizeAfter = productDaoJDBC.findAllProduct().size();
        assertTrue(sizeBefore > sizeAfter);
    }

}