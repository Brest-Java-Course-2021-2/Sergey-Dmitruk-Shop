package com.epam.brest.dao;

import com.epam.brest.Department;
import com.epam.brest.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


@Component
public class ProductDaoJDBCImpl implements ProductDao{

    private static final Logger LOGGER = LogManager.getLogger(ProductDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${SQL_Select_All_Product}")
    private String sqlGetAllProduct;
    @Value("${SQL_Check_Unique_name_Product}")
    private String sqlUniqueNameProduct;
    @Value("${SQL_Select_IDDepartmentByName_And_Check_Parent_Department_Exist}")
    private String sqlParentDepartmentIsExist;
    @Value("${SQL_Create_Product}")
    private String sqlCreateProduct;
    @Value("${SQL_Select_Product_By_Id}")
    private String sqlGetProductById;
    @Value("${SQL_Update_Product}")
    private String sqlUpdateProduct;
    @Value("${SQL_Delete_Product}")
    private String sqlDeleteProduct;

    @Value("${SQL_Select_Department_By_Id}")
    private String sqlDepartmentById;

//Basic working methods with Products


    @Value("${SQL_Sorted_Products_By_Date}")
    private String sqlSortedProducts;


    @Override
    public List<Product> findAllProduct() {
        LOGGER.debug("findAllProduct()");
        return namedParameterJdbcTemplate.query(sqlGetAllProduct, new ProductRowMapper());
    }

    @Override
    public Integer createProduct(Product product) {

        LOGGER.debug("createProduct({})", product);

        if (!productIsUnique(product.getNameProduct()) || product.getParentDepartmentName() == null) {
            LOGGER.warn("Product with this name already exists", product.getNameProduct());
            throw new IllegalArgumentException("Name product isn't unique");
        }

        if (returnIdParentDepartmentAndCheckDepartmentIsExist(product.getParentDepartmentName()) == null) {
            LOGGER.warn("Department with this name not exists");
            throw new IllegalArgumentException("Name parent department not found");
        }

        KeyHolder retKeyProd = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Product", product.getNameProduct())
                .addValue("parent_Department_Name", product.getParentDepartmentName())
                .addValue("delivery_Date", product.getDeliveryTime())
                .addValue("price", product.getPrice())
                .addValue("id_Department", returnIdParentDepartmentAndCheckDepartmentIsExist(product.getParentDepartmentName()));

        namedParameterJdbcTemplate.update(sqlCreateProduct, sqlParameterSource, retKeyProd);
        return (Integer) retKeyProd.getKey();


    }

    private Boolean productIsUnique(String nameProduct) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Product", nameProduct);
        return namedParameterJdbcTemplate.queryForObject(sqlUniqueNameProduct, sqlParameterSource, Integer.class) == 0;
    }

    //This method check exist parent department and returned his id
    private Integer returnIdParentDepartmentAndCheckDepartmentIsExist(String nameDepartment) {
        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Department", nameDepartment);
            return namedParameterJdbcTemplate.queryForObject(sqlParentDepartmentIsExist, sqlParameterSource, Integer.class).intValue();
        } catch (DataAccessException e) {
            return null;
        }

    }

    @Override
    public Integer updateProduct(Product product) {
        LOGGER.debug("updateProduct({})", product);
        if (returnIdParentDepartmentAndCheckDepartmentIsExist(product.getParentDepartmentName()) == null) {
            LOGGER.warn("Department with this name not exists");
            throw new IllegalArgumentException("Name parent department not found");
        }
        if (!productIsUnique(product.getNameProduct())
                & !Objects.equals(getProductById(product.getIdProduct()).getNameProduct(), product.getNameProduct())) {
            LOGGER.warn("Product is exist", product.getNameProduct());
            throw new IllegalArgumentException("Product is exist");
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Product", product.getNameProduct())
                .addValue("id_Product", product.getIdProduct())
                .addValue("parent_Department_Name", product.getParentDepartmentName())
                .addValue("delivery_Date", product.getDeliveryTime())
                .addValue("price", product.getPrice())
                .addValue("id_Department", returnIdParentDepartmentAndCheckDepartmentIsExist(product.getParentDepartmentName()));
        return namedParameterJdbcTemplate.update(sqlUpdateProduct, sqlParameterSource);
    }

    @Override
    public Integer deleteProduct(Integer idProduct) {
        LOGGER.debug("deleteProduct({})", idProduct);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id_Product", idProduct);
        return namedParameterJdbcTemplate.update(sqlDeleteProduct, sqlParameterSource);


    }

    @Override
    public Product getProductById(Integer id) {
        LOGGER.debug(" getProductById({})", id);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id_Product", id);
        return namedParameterJdbcTemplate.queryForObject(sqlGetProductById, sqlParameterSource, new ProductRowMapper());

    }


    class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setIdProduct(rs.getInt("id_Product"));
            product.setNameProduct(rs.getString("name_Product"));
            product.setParentDepartmentName(rs.getString("parent_Department_Name"));
            product.setDeliveryTime(rs.getString("delivery_Date"));
            product.setPrice(rs.getInt("price"));
            product.setIpDepartment(rs.getInt("id_Department"));

            return product;
        }
    }

    }


