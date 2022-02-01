package com.epam.brest.web_app;

import com.epam.brest.Department;
import com.epam.brest.dto.DepartmentDTO;
import com.epam.brest.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static com.epam.brest.constants.DepartmentConstants.Department_Name_Size;
import static com.epam.brest.constants.DepartmentConstants.Department_Responsible_Size;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Disabled
class DepartmentControllerIT {
    private ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LogManager.getLogger(DepartmentControllerIT.class);

    private final String URL_DEPARTMENTS_DTO = "http://localhost:8088/departments_dto";
    private final   String URL_DEPARTMENTS = "http://localhost:8088/departments";

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnDepartmentsPage() throws Exception {

        DepartmentDTO department1 = createDto(1,"department1");
        DepartmentDTO department2 = createDto(2, "department2");

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_DEPARTMENTS_DTO)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(department1,department2)))
                );

        logger.debug("shouldReturnDepartmentsPage()");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/departments")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("departments"));
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("id_Department", is(1)),
//                                hasProperty("name_Department", is("department1")),
//                                hasProperty("assortment", is(123)),
//                                hasProperty("responsible", is("TestResponsible")),
//                                hasProperty("total_Cost", is(null))
//
//                        )
//
//                )));
//          .andExpect(model().attribute("departments", hasItem(
//                  allOf(
//        hasProperty("id_Department", is(2)),
//                hasProperty("name_Department", is("department2")),
//                hasProperty("assortment", is(123)),
//                hasProperty("responsible", is("TestResponsible")),
//                hasProperty("total_Cost", is(null))
//
//
//                  )
//                )));
        mockServer.verify();
    }
    @Test
    void shouldAddDepartment() throws Exception {
logger.debug("shouldAddDepartment()");
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_DEPARTMENTS)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1")));

        Department department = new Department("Fish","testFish");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("nameDepartment", department.getNameDepartment())
                                .param("responsible",department.getResponsible())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

mockServer.verify();

    }
    @Test
    void shouldFailDepartmentOnEmptyName() throws Exception {


        Department department = new Department("");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("nameDepartment", department.getNameDepartment())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("department"))
                .andExpect(
                        model().attributeHasFieldErrors("department", "nameDepartment")
                );


    }
    @Test
    void shouldDeleteDepartmentWithProduct() throws Exception {
        logger.debug("shouldDeleteDepartment()");
        Integer id = 0;

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_DEPARTMENTS+"/" +id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)));


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/department/0/delete")
                ).andExpect(status().isFound())

                .andExpect(view().name("redirect:/departments"))
                .andExpect(flash().attribute("errorDelete",true))
                .andExpect(redirectedUrl("/departments"));

    }






    @Test
    void shouldFailDepartmentOnEmptyResponsible() throws Exception {
        logger.debug("shouldFailDepartmentOnEmptyResponsible()");
        Department department = new Department("Test","");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("nameDepartment", department.getNameDepartment())
                                .param("responsible", department.getResponsible())

                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("department"))
                .andExpect(
                        model().attributeHasFieldErrors("department", "responsible")
                );


    }
    @Test
    public void shouldEditDepartmentPageById() throws Exception {

        logger.debug("shouldEditDepartmentPageById()");

        Integer id = 1;
        Department department =
                create(id,"Test_Department");


        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_DEPARTMENTS+"/" + id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(department))
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/department/" + id)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("department", hasProperty("idDepartment", is(id))))
                .andExpect(model().attribute("department", hasProperty("nameDepartment", is("Test_Department"))));


    }
    @Test
    public void shouldUpdateDepartment() throws Exception {
        logger.debug("shouldUpdateDepartment()");
        String testName = RandomStringUtils.randomAlphabetic(Department_Name_Size);
        String testResponsible = RandomStringUtils.randomAlphabetic(Department_Responsible_Size);

        logger.debug("shouldUpdateDepartment()");

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_DEPARTMENTS)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1")));


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/department/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("idDepartment", "3")
                                .param("nameDepartment", testName)
                                .param("responsible", testResponsible)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));


      mockServer.verify();
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {
        logger.debug("shouldDeleteDepartment()");
        Integer id = 3;

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(new URI(URL_DEPARTMENTS+"/" +id)))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(id)));


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/department/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

    }
    private DepartmentDTO createDto(int id, String name){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId_Department(id);
        departmentDTO.setName_Department(name);
        departmentDTO.setAssortment(123);
        departmentDTO.setResponsible("TestResponsible");
        return departmentDTO;
    }
    private Department create(int id, String name){
        Department department = new Department();
        department.setIdDepartment(id);
        department.setNameDepartment(name);
        department.setResponsible("TestResponsible");
        return department;
    }
    }
