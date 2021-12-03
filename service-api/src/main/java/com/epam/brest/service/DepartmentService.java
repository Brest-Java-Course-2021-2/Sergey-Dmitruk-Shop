package com.epam.brest.service;

import com.epam.brest.Department;

import java.util.Collection;

public interface DepartmentService {
    Integer create(Department department);
    Integer count();
    Integer update(Department department);
    Integer delete(Integer idDepartment);
    Department getDepartmentById(Integer idDepartment);


}
