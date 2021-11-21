package com.epam.brest.service.impl;

import com.epam.brest.Department;
import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentServiceImpl.class);

    private DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
    @Override
    @Transactional
    public Integer create(Department department) {
        LOGGER.debug("create({})",department);
        return this.departmentDao.create(department);
    }
    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        LOGGER.debug("count()");
       return departmentDao.count();
    }

    @Override
    public Integer update(Department department) {
        LOGGER.debug("update({})",department);
        return this.departmentDao.update(department);
    }

    @Override
    public Integer delete(Integer idDepartment) {
        LOGGER.debug("delete({})",idDepartment);
        return this.departmentDao.delete(idDepartment);
    }

    @Override
    public Department getDepartmentById(Integer idDepartment) {
        LOGGER.debug("Get department by id({})",idDepartment);
        return this.departmentDao.getDepartmentById(idDepartment);
    }
}
