package com.epam.brest.rest;

import com.epam.brest.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.isVariable;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class DepartmentControllerIT {

    Logger logger = LogManager.getLogger(DepartmentControllerIT.class);

    @Autowired
    DepartmentController departmentController;

    ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
              //  .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();;
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    void shouldDepartmentsPage() throws Exception {
        mockMvc.perform(
                        get("/departments_dto")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[2].id_Department").value(1))
                .andExpect(jsonPath("$[2].name_Department").value("Dairy"))
                .andExpect(jsonPath("$[2].assortment").value(2))
                .andExpect(jsonPath("$[2].total_Cost").value(1000))
                .andExpect(jsonPath("$[2].responsible").value("Anastasia Alexandrovna"));

    }
    @Test
    void shouldDepartmentById() throws Exception{
        mockMvc.perform(
                        get("/departments/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.idDepartment").value(1))
                .andExpect(jsonPath("$.nameDepartment").value("Dairy"))
                .andExpect(jsonPath("$.responsible").value("Anastasia Alexandrovna"));

    }

    @Test
    void shouldDeleteDepartment() throws Exception{

        mockMvc.perform(
                        delete("/departments/{id}",3)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))

                .andExpect(content().string("1"));


    }


    @Test
    void shouldUpdateDepartment() throws Exception{
Department department = new Department("test","test");
department.setIdDepartment(1);

        mockMvc.perform(
                put("/departments")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(department))
                        .accept("application/json")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateDepartment() throws Exception{
        Department department = new Department("test","Test");

        String request=
        mapper.writeValueAsString(department);

        mockMvc.perform(

                    post("/departments")
                            .contentType("application/json")
                            .content(request)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());

    }


}
