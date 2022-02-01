package com.epam.brest.restservice;

import com.epam.brest.Product;
import com.epam.brest.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductRestService implements ProductService {

    public ProductRestService() {
    }

    private final Logger logger = LogManager.getLogger(ProductRestService.class);

    private String url;

    private RestTemplate restTemplate;

    public ProductRestService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public List<Product> findAllProduct() {
        logger.debug("findAllProduct()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url,List.class);
        return (List<Product>) responseEntity.getBody();
    }



    @Override
    public Integer createProduct(Product product) {
        logger.debug("createProduct({})", product);

        ResponseEntity responseEntity = restTemplate.postForEntity(url,product,Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer updateProduct(Product product) {
        logger.debug("updateProduct({})", product);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(product,httpHeaders);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer deleteProduct(Integer idProduct) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url +"/" + idProduct, HttpMethod.DELETE, entity, Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Product getProductById(Integer id) {
        logger.debug("getProductById({})",id);
        ResponseEntity responseEntity = restTemplate.getForEntity(url+ "/" +id, Product.class);
        return (Product) responseEntity.getBody();
    }

}
