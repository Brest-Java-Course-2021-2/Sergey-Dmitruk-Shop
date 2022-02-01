package com.epam.brest.web_app;

import com.epam.brest.Product;
import com.epam.brest.dto.ProductDto;
import com.epam.brest.service.ProductDTOService;
import com.epam.brest.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Disabled
class ProductsControllerIT {

    private ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LogManager.getLogger(ProductsControllerIT.class);

    private final   String URL_PRODUCTS = "http://localhost:8088/products";
    private final   String URL_PRODUCTS_SORT = "http://localhost:8088/products_sort";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDTOService productDTOService;


    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    void setUp() {
mapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldSortedByDate() throws Exception {
        logger.debug("shouldGetDateForSorted()");
        ProductDto product1 = createProductDto(1);
        ProductDto product2 = createProductDto(2);
        LocalDate from = LocalDate.of(2021, 10, 10);
        LocalDate to = LocalDate.of(2021, 10, 20);
        String URI = URL_PRODUCTS_SORT+ "?" +  "from=" +from +"&" +"to=" + to;
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URI)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(product1,product2)))
                );

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("from","2021-10-10")
                        .param("to","2021-10-20")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("products"));

        mockServer.verify();

    }



    @Test
    void shouldProductsPage() throws Exception {
        Product product1 = createProduct(1);
        Product product2 = createProduct(2);
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_PRODUCTS)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                        .body(mapper.writeValueAsString(Arrays.asList(product1,product2))));

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("products"));
        mockServer.verify();
    }

    @Test
    void shouldAddProduct() throws Exception {
        Integer id = 4;
        Product product = createProduct(id);
logger.debug("shouldAddProductPage()");
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_PRODUCTS)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)
                        ));

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
             .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(mapper.writeValueAsString(product))
                        .param("ipDepartment", String.valueOf(id))
                .param("nameProduct",product.getNameProduct())
                                .param("parentDepartmentName",product.getParentDepartmentName())
                                .param("price",product.getPrice().toString())
                              .param("DeliveryTime",product.getDeliveryTime().toString())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"))
                .andExpect(redirectedUrl("/products"));

        mockServer.verify();
    }
@Test
void shouldEditProductPageById() throws Exception {
    Integer id = 4;
    Product product = createProduct(id);
logger.debug("shouldEditProductPageById()");
    mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_PRODUCTS + "/" + id)))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.writeValueAsString(product)
                    ));

    mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", 4)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(mapper.writeValueAsString(id)))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
            .andExpect(view().name("product"))
            .andExpect(model().attribute("isNew", is(false)))
            .andExpect(model().attribute("product",hasProperty("idProduct",is(id))))
            .andExpect(model().attribute("product",hasProperty("nameProduct",is(product.getNameProduct()))))
            .andExpect(model().attribute("product",hasProperty("price",is(product.getPrice()))))
            .andExpect(model().attribute("product",hasProperty("deliveryTime",is((product.getDeliveryTime())))))
            .andExpect(model().attribute("product",hasProperty("parentDepartmentName",is(product.getParentDepartmentName()))));


mockServer.verify();

}


    @Test
    void shouldUpdateProduct() throws Exception {


logger.debug("shouldUpdateProduct()");

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_PRODUCTS)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1")
                        ));

        mockMvc.perform(MockMvcRequestBuilders.post("/product/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("idProduct","3")
                .param("nameProduct","updateName")
                .param("price","600")
                        .param("parentDepartmentName","updateParentName")
                        .param("deliveryTime","2021-12-31")).andExpect(status().isFound())
                .andExpect(view().name("redirect:/products"))
                .andExpect(redirectedUrl("/products"));

        mockServer.verify();
    }


    @Test
    void shouldDeleteProduct() throws Exception {
        Integer id = 3;
        logger.debug("shouldDeleteProduct()");
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_PRODUCTS + "/" + id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)
                        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}/delete",id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(mapper.writeValueAsString(id)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/products"))
                .andExpect(redirectedUrl("/products"));


                mockServer.verify();
    }

    @Test
   void shouldFailDateFormatOrDateIsEmpty() throws Exception {
        ProductDto product1 = createProductDto(1);
        ProductDto product2 = createProductDto(2);
        String from = "text";
        LocalDate to = LocalDate.of(2021, 10, 20);
        String URI = URL_PRODUCTS_SORT+ "?" +  "from=" +from +"&" +"to=" + to;
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_PRODUCTS)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(product1,product2)))
                );

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("from","")
                        .param("to","2021-10-20")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("error",true))
                .andExpect(view().name("products"));
        mockServer.verify();
    }
//



    Product createProduct(Integer id){
        Product product = new Product();
        product.setIpDepartment(id);
        product.setNameProduct("Test" + id);
        product.setIdProduct(id);
        product.setPrice(100);
        product.setDeliveryTime("2021-10-10");
        product.setParentDepartmentName("Test Parent" + id);
        return product;
    }
    ProductDto createProductDto(Integer id){
        ProductDto product = new ProductDto();
        product.setNameProduct("Test" + id);
        product.setIdProduct(id);
        product.setPrice(100);
        product.setDeliveryTime(LocalDate.parse("2021-10-10"));
        product.setParentDepartmentName("Test Parent" + id);
        return product;
    }

}