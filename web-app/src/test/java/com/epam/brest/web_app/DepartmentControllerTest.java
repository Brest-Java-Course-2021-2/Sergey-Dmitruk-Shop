package com.epam.brest.web_app;

import com.epam.brest.Department;
import com.epam.brest.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

import static com.epam.brest.constants.DepartmentConstants.Department_Name_Size;
import static com.epam.brest.constants.DepartmentConstants.Department_Responsible_Size;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context_test.xml"})
@Transactional
class DepartmentControllerIT {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldReturnDepartmentsPage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/departments")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id_Department", is(1)),
                                hasProperty("name_Department", is("Dairy")),
                                hasProperty("assortment", is(0)),
                                hasProperty("responsible", is("Anastasia Alexandrovna"))
                        )

                )))
          .andExpect(model().attribute("departments", hasItem(
                  allOf(



        hasProperty("id_Department", is(2)),
                hasProperty("name_Department", is("Butcher")),
                hasProperty("assortment", is(0)),
                hasProperty("responsible", is("Inna Vladimirovna"))

                                )
                )));

    }
    @Test
    void shouldAddDepartment() throws Exception {
        // WHEN
        assertNotNull(departmentService);
        Integer departmentsSizeBefore = departmentService.count();
        assertNotNull(departmentsSizeBefore);
        Department department = new Department("Fish");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/department")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("nameDepartment", department.getNameDepartment())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

        assertEquals(departmentsSizeBefore, departmentService.count() - 1);
    }
    @Test
    public void shouldEditDepartmentPageById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/department/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("department", hasProperty("idDepartment", is(1))))
                .andExpect(model().attribute("department", hasProperty("nameDepartment", is("Dairy"))));
    }
    @Test
    public void shouldUpdateDepartment() throws Exception {

        String testName = RandomStringUtils.randomAlphabetic(Department_Name_Size);
        String testResponsible = RandomStringUtils.randomAlphabetic(Department_Responsible_Size);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/department/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("idDepartment", "1")
                                .param("nameDepartment", testName)
                                .param("responsible",testResponsible)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));

        Department department = departmentService.getDepartmentById(1);
        assertNotNull(department);
        assertEquals(testName, department.getNameDepartment());
        assertEquals(testResponsible, department.getResponsible());
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {
        Integer countBefore = departmentService.count();
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/department/1/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
        Integer countAfter = departmentService.count();
        Assertions.assertEquals(countBefore - 1, countAfter);
    }

    }
