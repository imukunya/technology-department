CREATE TABLE departments("id" INT, "name" TEXT );

CREATE TABLE staff( "staff_id", "names" TEXT, "staff_number" TEXT);

CREATE TABLE roles("role_id" INT , "role_name" );

CREATE TABLE staff_roles( "staff_id" INT, "role_id" INT)

CREATE TABLE responsibilities("resp_id" int , "resp_name" text);

CREATE TABLE staff_responsibilities( "resp_id" INT , "staff_id" INT )

SELECT roles.name,roles.id,staff_roles.id,staff_roles.staff_id FROM roles INNER JOIN staff_roles ON roles.id = staff_roles.id WHERE staff_roles.staff_id ='';

    ! This database is empty. If upgrading, you can transfer
 ! data from another database with pg:copy
Created postgresql-closed-07718 as DATABASE_URL

Host
ec2-52-206-44-27.compute-1.amazonaws.com

Database
d95n5un2ca5iaj

User
zwidsrmbldecpt

Port
5432

Password
b8849a518aa2c66093ee73eb2cd8c869b63c6a9f1929c5c32be2df6a4dbcf684

URI
postgres://zwidsrmbldecpt:b8849a518aa2c66093ee73eb2cd8c869b63c6a9f1929c5c32be2df6a4dbcf684@ec2-52-206-44-27.compute-1.amazonaws.com:5432/d95n5un2ca5iaj
