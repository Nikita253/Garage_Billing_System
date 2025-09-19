create database garage;
use garage;

create table customers(
id int auto_increment primary key,
name varchar(30),
phone varchar(15)
); 

show tables;

Create table vehicles(
id int auto_increment primary key,
customer_id int,
number_plate varchar(15),
model varchar(10),
foreign key(customer_id) references customers(id)
);

create table services(
id int auto_increment primary key,
description varchar(40),
cost double
);

insert into services(description,cost) values
('oil change',1500),
('Engine Repair',5000),
('tyre puncher',500),
('Tyre Replacement',3000),
('washing',500);

select * from services;

create table invoices(
id int auto_increment primary key,
customer_id int,
vehicle_id int,
service_id int,
date timestamp default current_timestamp,
foreign key(customer_id) references customers(id),
foreign key(vehicle_id) references vehicles(id),
foreign key(service_id) references services(id)
);

Select * from customers;
select * from vehicles;
