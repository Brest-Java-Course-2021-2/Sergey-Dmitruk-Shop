package com.epam.brest.rest;

import com.epam.brest.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.isVariable;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context_test.xml"})
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
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @AfterEach
    void tearDown() {
    }





    @Test
    void shouldDepartmentsPage() throws Exception {
        mockMvc.perform(
                        get("/departmentsDto")
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
                .andDo(mvcResult -> mockMvc.perform(get("/departmentsDto"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$[*]").isArray())
                        .andExpect(jsonPath("$",hasSize(2))));

    }

    //Return 415 status

//    @Test
//    void shouldUpdateDepartment() throws Exception{
//        logger.debug("shouldUpdateDepartment()");
//        mockMvc.perform(
//                put("/departments")
//                ).andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldCreateDepartment() throws Exception{ mockMvc.perform(
//                    post("/departments")
//            ).andDo(MockMvcResultHandlers.print())
//            .andExpect(status().isOk());
//    }




}
