package com.epam.brest;

public class Department {
private Integer idDepartment;
private String nameDepartment;
private Integer assortment;
private String responsible;


 public Department(){

 }

    public Department(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public Department(Integer idDepartment, String nameDepartment) {
        this.idDepartment = idDepartment;
        this.nameDepartment = nameDepartment;
    }

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
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

    @Override
    public String toString() {
        return "Department{" +
                "idDepartment=" + idDepartment +
                ", nameDepartment='" + nameDepartment + '\'' +
                ", assortment=" + assortment +
                ", responsible='" + responsible + '\'' +
                '}';
    }
}
