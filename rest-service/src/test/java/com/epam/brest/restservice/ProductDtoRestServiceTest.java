package com.epam.brest.restservice;

import com.epam.brest.config.RestServiceConfigTest;
import com.epam.brest.dto.ProductDto;
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
class ProductDtoRestServiceTest {

    ProductDtoRestService productDtoRestService;

    private final Logger logger = LogManager.getLogger(ProductDtoRestServiceTest.class);
    private String url = "http://localhost:8080/products_sort";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        mapper.registerModule( new JavaTimeModule());
        mockServer = MockRestServiceServer.createServer(restTemplate);
        productDtoRestService = new ProductDtoRestService(url, restTemplate);
    }

    @Test
    void shouldSortedProductsByDate() throws URISyntaxException, JsonProcessingException {
        LocalDate from = LocalDate.of(2021, 10, 1);
        LocalDate to = LocalDate.of(2021, 10, 2);
        String URI = url+ "?" +  "from=" +from +"&" +"to=" + to;
        logger.debug("sortedProductsByDate()");
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URI)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)

                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(1), create(5))))
                );


        List<ProductDto> products = productDtoRestService.sortedProductsByDate(from,to);
        mockServer.verify();
        assertNotNull(products);


    }


    private ProductDto create(int id) {
        LocalDate date = LocalDate.of(2021, 10, id);
        ProductDto product = new ProductDto();
        product.setIdProduct(id);
        product.setNameProduct("Test product id = " + id);
        product.setParentDepartmentName("Dairy");
        product.setPrice(100);
        product.setDeliveryTime(date);
        return product;

    }
}
