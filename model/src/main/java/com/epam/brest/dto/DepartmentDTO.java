package com.epam.brest.dto;

public class DepartmentDTO {

    private Integer id_Department;
    private String name_Department;
    private Integer assortment;
    private String responsible;

    public Integer getId_Department() {
        return id_Department;
    }

    public void setId_Department(Integer id_Department) {
        this.id_Department = id_Department;
    }

    public String getName_Department() {
        return name_Department;
    }

    public void setName_Department(String name_Department) {
        this.name_Department = name_Department;
    }

    public Integer getAssortment() {
        return assortment;
    }

    public void setAssortment(Integer assortment) {
        this.assortment = assortment;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}
