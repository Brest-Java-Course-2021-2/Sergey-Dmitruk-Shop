package com.epam.brest;

import java.time.LocalDate;

public class Product {
    private Integer idProduct;
    private String nameProduct;
    private String parentDepartment;
    private LocalDate DeliveryTime;
    private LocalDate Expiration;
    private Integer ipDepartment;

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

    public String getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(String parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    public LocalDate getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public LocalDate getExpiration() {
        return Expiration;
    }

    public void setExpiration(LocalDate expiration) {
        Expiration = expiration;
    }
}
