package com.epam.brest.dao;

import com.epam.brest.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class DepartmentDaoJDBCImpTest {

    @InjectMocks
    private DepartmentDaoJDBCImp departmentDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
 private ArgumentCaptor<RowMapper<Department>> captorRowMapper;
    @Captor
    private ArgumentCaptor<SqlParameterSource> captorParameterSource;

    @AfterEach
    private void check(){
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);

    }

    @Test
    void findAll() {
        String sql = "test";
        ReflectionTestUtils.setField(departmentDaoJDBC, "sqlGetAllDepartment", sql);
        Department department = new Department();
        List<Department> departments = Collections.singletonList(department);
        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Department>>any())).thenReturn(departments);
        List<Department> result = departmentDaoJDBC.findAll();
        Mockito.verify(namedParameterJdbcTemplate).query(eq("test"), captorRowMapper.capture());

        RowMapper<Department> mapper = captorRowMapper.getValue();

        assertNotNull(mapper);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertSame(department,result.get(0));
    }

    @Test
    void getDepartmentById() {
        String sql = "test";
        ReflectionTestUtils.setField(departmentDaoJDBC, "sqlDepartmentById", sql);
Department department = new Department();
        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Department>>any())).thenReturn(department);

       Department result = departmentDaoJDBC.getDepartmentById(0);

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq("test"), captorParameterSource.capture(),
                captorRowMapper.capture());

        RowMapper<Department> mapper = captorRowMapper.getValue();
        SqlParameterSource parameterSource = captorParameterSource.getValue();

        assertNotNull(mapper);
        assertNotNull(parameterSource);

        assertNotNull(result);
        assertSame(department, result);
    }
}