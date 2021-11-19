package com.epam.brest.dao;

import com.epam.brest.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;


public class DepartmentDaoJDBCImp implements DepartmentDao {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentDaoJDBCImp.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_Select_All_Departments = "select d.id_Department, d.name_Department, d.assortment, d.responsible from department d order by d.name_Department";

    private final String SQL_Create_Department = "insert into department(name_Department) values(:name_Department)";

    private final String SQL_Check_Unique_name_Department ="select count(d.name_Department) " +
            " from department d where lower(d.name_Department) = lower(:name_Department)";

    private final String SQL_Count_Records_Department = "select count(*) from department";
//    private Statement statement;
//
//    private ResultSet resultSet;


    public DepartmentDaoJDBCImp(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Deprecated
    public DepartmentDaoJDBCImp(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);



//        try{Connection connection = dataSource.getConnection();
//statement = connection.createStatement();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }

    @Override
    public List<Department> findAll() {
        LOGGER.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_Select_All_Departments, new DepartmentRowMapper());
    }

    @Override
    public Integer create(Department department) {
        LOGGER.debug("Start: create({})",department);

        if(!isDepartmentUnique(department.getNameDepartment())){
            LOGGER.warn("Department with this name already exists",department.getNameDepartment());
            System.out.println("Exception");
            throw new IllegalArgumentException("Department with this name already exists");}

//        try{resultSet = statement.executeQuery("select * from department");
//            while (resultSet.next())
//            if(department.getNameDepartment().equalsIgnoreCase(resultSet.getString("nameDepartment")))
//                throw new IllegalArgumentException();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
            KeyHolder retKeyDep = new GeneratedKeyHolder();

            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Department", department.getNameDepartment().toUpperCase());
            namedParameterJdbcTemplate.update(SQL_Create_Department, sqlParameterSource, retKeyDep);
            return (Integer) retKeyDep.getKey();

    }
private boolean isDepartmentUnique(String name_Department){
LOGGER.debug("Check name_Department: {} on unique ", name_Department);
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name_Department",name_Department);


    return namedParameterJdbcTemplate.queryForObject(SQL_Check_Unique_name_Department,sqlParameterSource,Integer.class) == 0;

}


    @Override
    public Integer update(Department department) {
        return null;
    }

    @Override
    public Integer delete(Integer idDepartment) {
        return null;
    }

    @Override
    public Integer count() {
        LOGGER.debug("Count()");
        return namedParameterJdbcTemplate.queryForObject(SQL_Count_Records_Department, new MapSqlParameterSource(),Integer.class);
    }


    public class DepartmentRowMapper implements RowMapper<Department>{

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setIdDepartment(resultSet.getInt("id_Department"));
            department.setNameDepartment(resultSet.getString("name_Department"));
            department.setAssortment(resultSet.getInt("assortment"));
            department.setResponsible(resultSet.getString("responsible"));
            return department;
        }
    }

}
