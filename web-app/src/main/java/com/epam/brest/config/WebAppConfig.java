package com.epam.brest.config;


import com.epam.brest.restservice.DepartmentDtoRestService;
import com.epam.brest.restservice.DepartmentRestService;
import com.epam.brest.restservice.ProductDtoRestService;
import com.epam.brest.restservice.ProductRestService;
import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.ProductDTOService;
import com.epam.brest.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@ComponentScan
@Configuration
public class WebAppConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    ProductDTOService productDTOService(){
        String url = String.format("%s://%s:%d/products_sort",protocol,host,port);
        return new ProductDtoRestService(url,restTemplate());
    }

    @Bean
    ProductService productService(){
        String url = String.format("%s://%s:%d/products",protocol,host,port);
        return new ProductRestService(url,restTemplate());
    }

    @Bean
    DepartmentDTOService departmentDTOService() {
        String url = String.format("%s://%s:%d/departments_dto", protocol, host, port);
        return new DepartmentDtoRestService(url, restTemplate());
    }

    ;

    @Bean
    DepartmentService departmentService() {
        String url = String.format("%s://%s:%d/departments", protocol, host, port);
        return new DepartmentRestService(url, restTemplate());
    }
}
