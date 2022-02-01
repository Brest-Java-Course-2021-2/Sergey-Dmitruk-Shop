package com.epam.brest.restservice;

import com.epam.brest.dto.DepartmentDTO;
import com.epam.brest.service.DepartmentDTOService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartmentDtoRestService implements DepartmentDTOService {

    private final Logger logger = LogManager.getLogger(DepartmentDtoRestService.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentDtoRestService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public DepartmentDtoRestService() {
    }

    public List<DepartmentDTO> findAllDepartments() {
        logger.debug("findAllDepartments()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<DepartmentDTO>) responseEntity.getBody();

    }


}
