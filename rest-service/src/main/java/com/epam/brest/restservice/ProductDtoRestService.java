package com.epam.brest.restservice;

import com.epam.brest.dto.ProductDto;
import com.epam.brest.service.ProductDTOService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDtoRestService implements ProductDTOService {

    private final Logger logger = LogManager.getLogger(ProductDtoRestService.class);

    private String url;

    private RestTemplate restTemplate;

    public ProductDtoRestService() {
    }

    public ProductDtoRestService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<ProductDto> sortedProductsByDate(LocalDate from, LocalDate to) {
        logger.debug("sortedProductsByDate({}{})", from, to);

        String URL = url + "?" + "from={from}" + "&" + "to={to}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("from", from.toString());
        uriParam.put("to", to.toString());


        ResponseEntity responseEntity = restTemplate.getForEntity(URL, List.class, uriParam);

        return (List<ProductDto>) responseEntity.getBody();
    }

}
