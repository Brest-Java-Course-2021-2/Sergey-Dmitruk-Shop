package com.epam.brest.restservice;

import com.epam.brest.Product;
import com.epam.brest.config.RestServiceConfigTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import({RestServiceConfigTest.class})
class ProductRestServiceTest {

    private final Logger logger = LogManager.getLogger(ProductRestServiceTest.class);
    @Autowired
    RestTemplate restTemplate;
    private String url = "http://localhost:8080/products";
    private ProductRestService productRestService;
    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mockServer = MockRestServiceServer.createServer(restTemplate);
        productRestService = new ProductRestService(url, restTemplate);
    }


    @Test
    void shouldFindAllProduct() throws JsonProcessingException, URISyntaxException {
        logger.debug("shouldFindAllProduct(");
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(url)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );
        List<Product> products = productRestService.findAllProduct();
        mockServer.verify();
        assertNotNull(products);
        assertTrue(products.size() > 0);

    }

    @Test
    void shouldCreateProduct() throws JsonProcessingException, URISyntaxException {
        logger.debug("shouldCreateProduct()");
        Integer id = 4;
        Product product = create(id);

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(url)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)
                        ));
        Integer idResult = productRestService.createProduct(product);
        mockServer.verify();
        assertEquals(id, idResult);
    }

    @Test
    void shouldUpdateProduct() throws JsonProcessingException, URISyntaxException {
        logger.debug("shouldUpdateProduct()");
        Integer id = 4;

        Product product = create(id);
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(url)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)
                        ));
        Integer idResult = productRestService.updateProduct(product);
        mockServer.verify();
        assertEquals(id, idResult);

    }

    @Test
    void shouldDeleteProduct() throws JsonProcessingException, URISyntaxException {
        logger.debug("shouldDeleteProduct()");
        Integer id = 5;
        Product product = create(id);
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(url + "/" + id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)
                        ));
        Integer idResult = productRestService.deleteProduct(id);
        mockServer.verify();
        assertEquals(id, idResult);

    }

    @Test
    void shouldGetProductById() throws JsonProcessingException, URISyntaxException {
        logger.debug("shouldGetProductById()");
        Integer id = 6;
        Product product = create(id);

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(url + "/" + id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(product)
                        ));
        Product result = productRestService.getProductById(id);
        mockServer.verify();
        assertNotNull(result);
        assertEquals(id, result.getIdProduct());


    }

    private Product create(int id) {
        LocalDate date = LocalDate.of(2021, 1, id);
        Product product = new Product();
        product.setIdProduct(id);
        product.setNameProduct("Test product id = " + id);
        product.setParentDepartmentName("Dairy");
        product.setPrice(100);
        product.setDeliveryTime(date.toString());
        return product;

    }
}