package com.epam.brest.config;


import com.epam.brest.restservice.DepartmentDtoRestService;
import com.epam.brest.restservice.DepartmentRestService;
import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
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
