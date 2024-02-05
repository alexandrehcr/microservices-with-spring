--CREATE DATABASE IF NOT EXISTS jg_ms_department;
--USE jg_ms_department;

CREATE TABLE IF NOT EXISTS departments (
   id INT PRIMARY KEY,
   name VARCHAR(255),
   description VARCHAR(255),
   code VARCHAR(255) NOT NULL UNIQUE);

INSERT INTO departments (id, name, description, code)
VALUES (8, 'IT', 'Information Technology', 'IT001');

--

--CREATE DATABASE IF NOT EXISTS jg_ms_organization;
--USE jg_ms_organization;

CREATE TABLE IF NOT EXISTS organizations (
   id INT PRIMARY KEY,
   name VARCHAR(255),
   description VARCHAR(255),
   code VARCHAR(255) NOT NULL UNIQUE);

INSERT INTO organizations (id, name, description, code)
VALUES (5, 'OrganizationName', 'Organization Description', 'OC001');

--

--CREATE DATABASE IF NOT EXISTS jg_ms_employee;
--USE jg_ms_employee;

CREATE TABLE IF NOT EXISTS employees (
   id INT PRIMARY KEY,
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255) NOT NULL UNIQUE,
   department_code VARCHAR(255),
   organization_code VARCHAR(255),
   FOREIGN KEY (department_code) REFERENCES departments(id),
   FOREIGN KEY (organization_code) REFERENCES organizations(id));

INSERT INTO employees(id, first_name, last_name, email, department_code, organization_code)
VALUES (1995, 'James', 'Gosling', 'jamesgosling@gmail.com', 'IT001', 'OC001');
