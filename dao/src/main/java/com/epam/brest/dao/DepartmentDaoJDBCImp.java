package com.epam.brest.dao;

import com.epam.brest.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Objects;

@Component
public class DepartmentDaoJDBCImp implements DepartmentDao {


    private static final Logger LOGGER = LogManager.getLogger(DepartmentDaoJDBCImp.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_Select_All_Departments}")
    private String sqlGetAllDepartment;

    @Value("${SQL_Create_Department}")
    private String sqlCreateDepartment;

    @Value("${SQL_Check_Unique_name_Department}")
    private String sqlUniqueNameDepartment;

    @Value("${SQL_Count_Records_Department}")
    private String sqlCountDepartment;

    @Value("${SQL_Update_name_Department}")
    private String sqlUpdateDepartment;

    @Value("${SQL_Select_Department_By_Id}")
    private String sqlDepartmentById;

    @Value("${SQL_Delete_Department}")
    private String sqlDeleteDepartment;

    @Value("${SQL_Update_Name_Parent_Department}")
    private String sqlUpdateNameParentDepartment;


    public DepartmentDaoJDBCImp(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        LOGGER.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(sqlGetAllDepartment, new DepartmentRowMapper());
    }

    @Override
    public Integer create(Department department) {
        LOGGER.debug("Start: create({})", department);

        if (!isDepartmentUnique(department.getNameDepartment()) || department.getNameDepartment() == null) {
            LOGGER.warn("Department with this name already exists", department.getNameDepartment());
            System.out.println("Exception");
            throw new IllegalArgumentException("Department with this name already exists");
        }

        KeyHolder retKeyDep = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("nameDepartment", department.getNameDepartment().toUpperCase())
                .addValue("responsible", department.getResponsible());

        namedParameterJdbcTemplate.update(sqlCreateDepartment, sqlParameterSource, retKeyDep);
        return (Integer) retKeyDep.getKey();

    }

    private boolean isDepartmentUnique(String name_Department) {
        LOGGER.debug("Check name_Department: {} on unique ", name_Department);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Department", name_Department);
        return namedParameterJdbcTemplate.queryForObject(sqlUniqueNameDepartment, sqlParameterSource, Integer.class) == 0;
    }


    @Override
    public Integer update(Department department) {
        LOGGER.debug("Update department: {}", department);
        if (!isDepartmentUnique(department.getNameDepartment()) &
                !Objects.equals(getDepartmentById(department.getIdDepartment()).getNameDepartment(), department.getNameDepartment()))  //Check department on unique
            throw new IllegalArgumentException("Department with this name already exists");                                  // and if name not changed, exception not thrown.
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("nameDepartment", department.getNameDepartment())
                .addValue("idDepartment", department.getIdDepartment())
                .addValue("responsible", department.getResponsible());
        Integer count = namedParameterJdbcTemplate.update(sqlUpdateDepartment, sqlParameterSource);
        namedParameterJdbcTemplate.update(sqlUpdateNameParentDepartment, sqlParameterSource);
        return count;
    }

    @Override
    public Integer delete(Integer idDepartment) {
        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDepartment", idDepartment);
            return namedParameterJdbcTemplate.update(sqlDeleteDepartment, sqlParameterSource);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return 0;
        }

    }

    @Override
    public Integer count() {
        LOGGER.debug("count()");
        return namedParameterJdbcTemplate.queryForObject(sqlCountDepartment, new MapSqlParameterSource(), Integer.class);
    }

    @Override
    public Department getDepartmentById(Integer idDepartment) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("idDepartment", idDepartment);
        return namedParameterJdbcTemplate.queryForObject(sqlDepartmentById, sqlParameterSource, new DepartmentRowMapper());
    }


    public class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setIdDepartment(resultSet.getInt("id_Department"));
            department.setNameDepartment(resultSet.getString("name_Department"));
            department.setResponsible(resultSet.getString("responsible"));


            return department;
        }
    }

}
