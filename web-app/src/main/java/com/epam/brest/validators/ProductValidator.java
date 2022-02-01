package com.epam.brest.validators;

import com.epam.brest.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class ProductValidator implements Validator {
    public boolean supports(Class clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "nameProduct", "nameProduct.empty");
        ValidationUtils.rejectIfEmpty(errors, "parentDepartmentName", "parentDepartmentName.empty");
        ValidationUtils.rejectIfEmpty(errors,"price","price.empty");
        ValidationUtils.rejectIfEmpty(errors,"DeliveryTime","DeliveryTime.empty");
    Product product = (Product) target;

 if (product.getDeliveryTime() != "" & !checkValidateDate(product.getDeliveryTime()))
            errors.rejectValue("DeliveryTime","DeliveryTime.invalidFormat");
}
    public boolean checkValidateDate(String date){
        try{
            LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
