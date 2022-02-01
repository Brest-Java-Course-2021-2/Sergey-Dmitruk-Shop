package com.epam.brest.rest;


import com.epam.brest.Department;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {


    @InjectMocks
    private DepartmentController controller;
    @Mock
    private DepartmentDTOService dtoService;

    @Mock
    private DepartmentService departmentService;

    @Captor
    private ArgumentCaptor<Integer> captorIntID;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }


    @Test
    public void getDepartmentById() throws Exception {
        Department department = new Department();
        department.setNameDepartment("Test");
        department.setIdDepartment(2);
        Mockito.when(departmentService.getDepartmentById(anyInt())).thenReturn(department);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/departments/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDepartment", Matchers.is(department.getIdDepartment())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameDepartment", Matchers.is(department.getNameDepartment())));

        Mockito.verify(departmentService).getDepartmentById(captorIntID.capture());

        Assertions.assertEquals(captorIntID.getValue(), 2);


    }

    @Test
    public void getDepartmentByIDException() throws Exception {
        Mockito.when(departmentService.getDepartmentById(anyInt())).thenThrow(new IllegalArgumentException("message"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/departments/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"validation_error\",\"details\":[\"message\"]}"));

    }

}
