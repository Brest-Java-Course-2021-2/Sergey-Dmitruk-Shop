package com.epam.brest.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ProductDto {
    private Integer idProduct;
    private String nameProduct;
    private String parentDepartmentName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate DeliveryTime;
    private Integer price;
    private Integer ipDepartment;

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getParentDepartmentName() {
        return parentDepartmentName;
    }

    public void setParentDepartmentName(String parentDepartmentName) {
        this.parentDepartmentName = parentDepartmentName;
    }

    public LocalDate getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIpDepartment() {
        return ipDepartment;
    }

    public void setIpDepartment(Integer ipDepartment) {
        this.ipDepartment = ipDepartment;
    }
}
