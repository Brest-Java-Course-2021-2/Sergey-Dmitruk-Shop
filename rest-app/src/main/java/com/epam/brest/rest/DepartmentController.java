package com.epam.brest.rest;



import com.epam.brest.Department;
import com.epam.brest.dto.DepartmentDTO;
import com.epam.brest.service.DepartmentDTOService;
import com.epam.brest.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class DepartmentController {
Logger logger =  LogManager.getLogger(DepartmentController.class);

    private DepartmentService departmentService;

    private DepartmentDTOService departmentDTOService;

    public DepartmentController(DepartmentDTOService departmentDTOService, DepartmentService departmentService) {
        this.departmentDTOService = departmentDTOService;
        this.departmentService = departmentService;
    }


    @GetMapping(value = "/departments_dto")
    public final Collection<DepartmentDTO> departments(){
        logger.debug("departments()");
        return departmentDTOService.findAllDepartments();
    }
    @GetMapping(value = "/departments/{id}")
    public final Department getDepartmentById(@PathVariable Integer id) {

        logger.debug("getDepartmentById({})", id);
        Department department = departmentService.getDepartmentById(id);
        return department;
    }

    @PostMapping(path = "/departments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createDepartment(@RequestBody Department department) {

        logger.debug("createDepartment({})", department);
        Integer id = departmentService.create(department);
        return new ResponseEntity(id, HttpStatus.OK);
    }
    @PutMapping(value = "/departments", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Integer> updateDepartment(@RequestBody Department department) {
        logger.debug("updateDepartment({})", department);
        int result = departmentService.update(department);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/departments/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteDepartment(@PathVariable Integer id) {

        int result = departmentService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    }

