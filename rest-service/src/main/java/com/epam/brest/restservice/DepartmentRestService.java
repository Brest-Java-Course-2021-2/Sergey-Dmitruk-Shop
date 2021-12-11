package com.epam.brest.restservice;


import com.epam.brest.Department;
import com.epam.brest.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DepartmentRestService implements DepartmentService {

    private final Logger logger = LogManager.getLogger(DepartmentRestService.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentRestService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }
public DepartmentRestService(){

}


    @Override
    public Integer create(Department department) {
        logger.debug("create({})", department);

        ResponseEntity responseEntity = restTemplate.postForEntity(url,department,Integer.class);
        return (Integer) responseEntity.getBody();

    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url+"/"+"count",Integer.class);
        return(Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Department department) {
        logger.debug("update({})",department);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<Department>(department,httpHeaders);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer delete(Integer idDepartment) {
        logger.debug("delete({})",idDepartment);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(url +"/" + idDepartment, HttpMethod.DELETE, entity, Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public Department getDepartmentById(Integer idDepartment) {
        logger.debug("getDepartmentById({})",idDepartment);
        ResponseEntity responseEntity = restTemplate.getForEntity(url +"/" + idDepartment,Department.class);
        return (Department) responseEntity.getBody();
    }

    @Override
    public List<Department> departmentsFindAll() {
        logger.debug("findAllDepartments()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url,List.class);
        return (List<Department>) responseEntity.getBody();
    }
}
