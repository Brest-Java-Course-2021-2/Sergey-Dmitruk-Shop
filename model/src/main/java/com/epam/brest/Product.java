package com.epam.brest;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Product {
    private Integer idProduct;
    private String nameProduct;
    private String parentDepartmentName;
    private String DeliveryTime;
    private Integer price;
    private Integer ipDepartment;
private String getDate;



    public Product(String nameProduct, String parentDepartmentName, String deliveryTime, Integer price) {
        this.nameProduct = nameProduct;
        this.parentDepartmentName = parentDepartmentName;
        DeliveryTime = deliveryTime;
        this.price = price;

    }

    public Product(String nameProduct, String parentDepartmentName) {
        this.nameProduct = nameProduct;
        this.parentDepartmentName = parentDepartmentName;
    }

    public Product(){

    }

    public Product(Integer idProduct, String nameProduct, String parentDepartmentName, String deliveryTime, Integer price, Integer ipDepartment) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.parentDepartmentName = parentDepartmentName;
        DeliveryTime = deliveryTime;
        this.price = price;
        this.ipDepartment = ipDepartment;
    }

    public Integer getIpDepartment() {
        return ipDepartment;
    }

    public void setIpDepartment(Integer ipDepartment) {
        this.ipDepartment = ipDepartment;
    }

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

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
