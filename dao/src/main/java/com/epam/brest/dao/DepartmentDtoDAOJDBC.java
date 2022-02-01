package com.epam.brest.dao;

import com.epam.brest.Department;
import com.epam.brest.dao.dto.DepartmentDtoDAO;
import com.epam.brest.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentDtoDAOJDBC implements DepartmentDtoDAO {
    @Value("${returnAllDepartments}")
    private String returnAllDepartments;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentDtoDAOJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<DepartmentDTO> retAllDepartments() {
        List<DepartmentDTO> departments = namedParameterJdbcTemplate.query(returnAllDepartments,
                BeanPropertyRowMapper.newInstance(DepartmentDTO.class));
        return departments;
    }
}
