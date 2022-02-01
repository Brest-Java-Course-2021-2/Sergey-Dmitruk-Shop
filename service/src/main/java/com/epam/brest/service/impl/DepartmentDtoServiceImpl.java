package com.epam.brest.service.impl;


import com.epam.brest.Department;
import com.epam.brest.dao.dto.DepartmentDtoDAO;
import com.epam.brest.dto.DepartmentDTO;
import com.epam.brest.service.DepartmentDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class DepartmentDtoServiceImpl implements DepartmentDTOService {
private  DepartmentDtoDAO departmentDtoDAO;


    public DepartmentDtoServiceImpl(DepartmentDtoDAO departmentDtoDAO) {
        this.departmentDtoDAO = departmentDtoDAO;
    }



    public List<DepartmentDTO> findAllDepartments() {

        return departmentDtoDAO.retAllDepartments();
    }
}

