CREATE TABLE departments("id" INT, "name" TEXT );

CREATE TABLE staff( "staff_id", "names" TEXT, "staff_number" TEXT);

CREATE TABLE roles("role_id" INT , "role_name" );

CREATE TABLE staff_roles( "staff_id" INT, "role_id" INT)

CREATE TABLE responsibilities("resp_id" int , "resp_name" text);

CREATE TABLE staff_responsibilities( "resp_id" INT , "staff_id" INT )

SELECT roles.name,roles.id,staff_roles.id,staff_roles.staff_id FROM roles INNER JOIN staff_roles ON roles.id = staff_roles.id WHERE staff_roles.staff_id ='';
