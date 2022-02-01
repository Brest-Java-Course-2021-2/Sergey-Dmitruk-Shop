DROP TABLE IF EXISTS department;
CREATE TABLE department(
    id_Department INT NOT NULL AUTO_INCREMENT,
  name_Department VARCHAR(30) NOT NULL UNIQUE,
assortment INT,
total_Cost INT,
responsible VARCHAR(40) NOT NULL,
    PRIMARY KEY (id_Department)
);
CREATE TABLE product(
id_Product INT NOT NULL AUTO_INCREMENT,
name_Product VARCHAR(50) NOT NULL UNIQUE,
parent_Department_Name VARCHAR(50) NOT NULL,
delivery_Date DATE NOT NUll,
price INT NOT NULL,
id_Department INT NOT NULL,
 PRIMARY KEY (id_Product),
 CONSTRAINT product_department_fk FOREIGN KEY (id_Department) REFERENCES department(id_Department)
);
