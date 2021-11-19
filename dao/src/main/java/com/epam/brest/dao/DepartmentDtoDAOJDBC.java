package com.epam.brest.dao;

import com.epam.brest.Department;
import com.epam.brest.dao.dto.DepartmentDtoDAO;
import com.epam.brest.dto.DepartmentDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class DepartmentDtoDAOJDBC implements DepartmentDtoDAO {
    private String returnAllDepartments ="select id_Department, name_Department, assortment, responsible from department d order by name_Department";
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentDtoDAOJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    public List<DepartmentDTO> retAllDepartments(){
        List<DepartmentDTO> departments = namedParameterJdbcTemplate.query(returnAllDepartments,
                BeanPropertyRowMapper.newInstance(DepartmentDTO.class));
        return departments;
    }
}
