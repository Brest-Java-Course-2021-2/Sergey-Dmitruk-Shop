package com.epam.brest.restservice;

import com.epam.brest.config.RestServiceConfigTest;
import com.epam.brest.dto.DepartmentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@Import({RestServiceConfigTest.class})
class DepartmentDtoRestServiceTest {
    private final Logger logger = LogManager.getLogger(DepartmentDtoRestServiceTest.class);
    @Autowired
    RestTemplate restTemplate;
    DepartmentDtoRestService departmentDtoRestService;
    private String url = "http://localhost:8080/departments_dto";
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        departmentDtoRestService = new DepartmentDtoRestService(url, restTemplate);
    }

    @Test
    void shouldFindAllDepartments() throws URISyntaxException, JsonProcessingException {
        logger.debug("shouldFindAllDepartments()");
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(url)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );


        List<DepartmentDTO> departments = departmentDtoRestService.findAllDepartments();

        mockServer.verify();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);

    }

    private DepartmentDTO create(int id) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId_Department(id);
        departmentDTO.setName_Department("Test" + " id" + id);
        departmentDTO.setAssortment(123);
        departmentDTO.setResponsible("TestResponsible");
        return departmentDTO;
    }
}