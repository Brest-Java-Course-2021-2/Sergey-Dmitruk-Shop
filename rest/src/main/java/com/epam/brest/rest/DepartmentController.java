package com.epam.brest.rest;



import com.epam.brest.dto.DepartmentDTO;
import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DepartmentController {
Logger logger =  LogManager.getLogger(DepartmentController.class);

    private DepartmentService departmentService;

    private DepartmentDTOService departmentDTOService;

    public DepartmentController(DepartmentDTOService departmentDTOService) {
        this.departmentDTOService = departmentDTOService;
    }

    @GetMapping(value = "/departmentsDto")
    public final Collection<DepartmentDTO> departments(){
        logger.debug("departments()");
        return departmentDTOService.findAllDepartments();
    }

    }

