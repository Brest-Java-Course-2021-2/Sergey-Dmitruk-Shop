package com.epam.brest.validators;

import com.epam.brest.Department;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static com.epam.brest.constants.DepartmentConstants.Department_Name_Size;
import static com.epam.brest.constants.DepartmentConstants.Department_Responsible_Size;

@Component
public class DepartmentValidator implements Validator {
    public boolean supports(Class clazz) {
        return Department.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "nameDepartment", "departmentName.empty");
        ValidationUtils.rejectIfEmpty(e, "responsible", "responsibleNameSize");
        Department department = (Department) obj;



        if(StringUtils.hasText(department.getNameDepartment())
        && department.getNameDepartment().length() > Department_Name_Size){
            e.rejectValue("nameDepartment", "departmentNameSize");
        }

}


}
