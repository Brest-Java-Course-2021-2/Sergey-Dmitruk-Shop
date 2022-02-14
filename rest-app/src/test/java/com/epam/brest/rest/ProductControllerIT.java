package com.epam.brest.rest;

import com.epam.brest.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class ProductControllerIT {
    Logger logger = LogManager.getLogger(ProductControllerIT.class);

    @Autowired
    ProductController productController;
    ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldFindAllProducts() throws Exception {
        logger.debug("shouldFindAllProducts()");
        Integer countProducts = productController.findAllProducts().size();
        mockMvc.perform(
                        get("/products")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$", hasSize(countProducts)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetProductById() throws Exception {
        logger.debug("shouldGetProductById()");
        Integer id = 1;
        mockMvc.perform(
                        get("/products/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(id))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.idProduct").value(id))
                .andExpect(jsonPath("$.nameProduct").value("Milk"))
                .andExpect(jsonPath("$.parentDepartmentName").value("Dairy"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        logger.debug(" shouldUpdateProduct()");
        Product product = productController.getProductById(1);
        product.setNameProduct("TestName");
        mockMvc.perform(
                        put("/products")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(product))
                                .accept("application/json")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        logger.debug("shouldDeleteProduct()");
        Integer newId = productController.findAllProducts().size() + 1;
        LocalDate date = LocalDate.of(2021, 1, 1);
        Product product = new Product("Test", "Dairy", date.toString(), 100);
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(product))
                                .accept("application/json")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(newId.toString()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldDeleteProduct() throws Exception {
        logger.debug("shouldDeleteProduct()");
        Integer id = 3;
        mockMvc.perform(
                        delete("/products/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(id))
                                .accept("application/json")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("1"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldSortedProductsByDate() throws Exception {
        logger.debug("shouldSortedProductsByDate()");
        LocalDate from = LocalDate.of(2021, 10, 1);
        LocalDate to = LocalDate.of(2021, 10, 19);
        mockMvc.perform(
                        get("/products_sort")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .param("from", from.toString())
                                .param("to", to.toString())
                                .accept("application/json")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

}
