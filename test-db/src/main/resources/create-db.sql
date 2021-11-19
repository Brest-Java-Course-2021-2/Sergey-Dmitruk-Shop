DROP TABLE IF EXISTS department;
CREATE TABLE department(
    id_Department INT NOT NULL AUTO_INCREMENT,
  name_Department VARCHAR(20) NOT NULL UNIQUE,
assortment INT,
responsible VARCHAR(40),
    PRIMARY KEY (id_Department)
);
CREATE TABLE product(
id_Product INT NOT NULL AUTO_INCREMENT,
name_Product VARCHAR(50) NOT NULL UNIQUE,
parent_Department VARCHAR(50) NOT NULL,
delivery_Time DATE NOT NUll,
expiration DATE NOT NUll,
id_Department INT NOT NULL
);
