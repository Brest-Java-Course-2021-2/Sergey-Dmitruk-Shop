package com.epam.brest.restservice;

import com.epam.brest.Department;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context_test.xml"})
class DepartmentRestServiceTest {
    Logger logger = LogManager.getLogger(DepartmentRestServiceTest.class);
    @Autowired
    RestTemplate restTemplate;
    private String URL = "http://localhost:8088/departments";
    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    private DepartmentRestService departmentRestService;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        departmentRestService = new DepartmentRestService(URL, restTemplate);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldCreate() throws JsonProcessingException, URISyntaxException {
        logger.debug("shouldCreate()");

        Integer idCreateDepartment = 7;
        Department departmentCreated =
                create(idCreateDepartment);

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(idCreateDepartment)));

        Integer idResult = departmentRestService.create(departmentCreated);

        mockServer.verify();
        assertNotNull(idResult);
        assertEquals(idCreateDepartment, idResult);

    }

    @Test
    void shouldReturnCount() throws URISyntaxException, JsonProcessingException {
        logger.debug("shouldReturnCount()");
        Integer countDepartments = 3;

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL + "/" + "count")))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(countDepartments)));

        Integer count = departmentRestService.count();

        mockServer.verify();
        assertNotNull(count);
        assertEquals(count, countDepartments);


    }

    @Test
    void shouldUpdate() throws URISyntaxException, JsonProcessingException {
        logger.debug("shouldUpdate()");
        Integer id = 6;
        Department department = create(id);
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("6")));


        Integer updateId = departmentRestService.update(department);


        mockServer.verify();
        assertNotNull(updateId);
        assertEquals(id, updateId);


    }

    @Test
    void shouldDelete() throws URISyntaxException, JsonProcessingException {
        logger.debug("shouldDelete()");
        Integer id = 3;

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL + "/" + id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("3")));


        Integer resultId = departmentRestService.delete(id);
        mockServer.verify();
        assertNotNull(resultId);
        assertEquals(id, resultId);


    }

    @Test
    void shouldGetDepartmentById() throws URISyntaxException, JsonProcessingException {
        logger.debug("shouldGetDepartmentById()");
        Integer id = 10;
        Department department =
                create(id);


        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL + "/" + id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(department))
                );


        Department resultDepartment = departmentRestService.getDepartmentById(id);


        mockServer.verify();
        assertNotNull(resultDepartment);
        assertEquals(resultDepartment.getIdDepartment(), id);
        assertEquals(resultDepartment.getNameDepartment(), department.getNameDepartment());

    }

    @Test
    void shouldDepartmentsFindAll() throws URISyntaxException, JsonProcessingException {
        logger.debug("shouldDepartmentsFindAll()");


        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );


        List<Department> departments = departmentRestService.departmentsFindAll();

        mockServer.verify();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    private Department create(int id) {
        Department department = new Department();
        department.setIdDepartment(id);
        department.setNameDepartment("Test" + " id" + id);
        department.setResponsible("TestResponsible");
        return department;
    }
}