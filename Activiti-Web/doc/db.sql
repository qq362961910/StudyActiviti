--1.
CREATE USER 'activiti-test'@'localhost' IDENTIFIED BY 'activiti-test';
CREATE USER 'activiti-test'@'%' IDENTIFIED BY 'activiti-test';

--2.
grant all privileges on *.* to 'activiti-test'@'localhost' identified by 'activiti-test';
grant all privileges on *.* to 'activiti-test'@'%' identified by 'activiti-test';

--3.
flush privileges;

--4.
create database activiti;



