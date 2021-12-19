INSERT INTO department(id_Department, name_Department, assortment, total_Cost, responsible)
VALUES (1, 'Dairy', 0, 0, 'Anastasia Alexandrovna');
INSERT INTO department(id_Department, name_Department, assortment, total_Cost, responsible)
VALUES (2, 'Butcher', 0, 0, 'Inna Vladimirovna');
INSERT INTO department(id_Department, name_Department, assortment, total_Cost, responsible)
VALUES (3, 'Bakery', 0, 0, 'Zoya Dmitrievna');

INSERT INTO product(id_Product, name_Product, parent_Department_Name, delivery_Date, price, id_Department)
VALUES(1, 'Milk', 'Dairy', '2021-10-03', 400, 1);
INSERT INTO product(id_Product, name_Product, parent_Department_Name, delivery_Date, price, id_Department)
VALUES(2, 'Sour Cream', 'Dairy', '2021-10-07', 600, 1);
INSERT INTO product(id_Product, name_Product, parent_Department_Name, delivery_Date, price, id_Department)
VALUES(3, 'Beef', 'Dairy', '2021-10-20', 1000, 2);




