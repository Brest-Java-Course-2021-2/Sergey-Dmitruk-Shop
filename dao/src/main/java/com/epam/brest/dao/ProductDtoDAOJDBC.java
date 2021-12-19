package com.epam.brest.dao;

import com.epam.brest.Product;
import com.epam.brest.dao.dto.ProductDtoDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


@Component
public class ProductDtoDAOJDBC implements ProductDtoDAO {


    private static final Logger LOGGER = LogManager.getLogger(ProductDtoDAOJDBC.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDtoDAOJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${SQL_Sorted_Products_By_Date}")
    private  String sqlSortedProducts;

 //This method returned list products, sorted by dates
    @Override
    public List<Product> sortedProductsByDate(LocalDate from, LocalDate to) {
        LOGGER.debug("sortedProductsByDate({}{})",from,to);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("date_From",from)
                .addValue("date_To", to);
       return namedParameterJdbcTemplate.query(sqlSortedProducts, sqlParameterSource, new ProductRowMapper());}

    }
    class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setIdProduct(rs.getInt("id_Product"));
            product.setNameProduct(rs.getString("name_Product"));
            product.setParentDepartmentName(rs.getString("parent_Department_Name"));
            product.setDeliveryTime(rs.getDate("delivery_Date").toLocalDate());
            product.setPrice(rs.getInt("price"));
            product.setIpDepartment(rs.getInt("id_Department"));
            return product;
        }
    }

