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
}
