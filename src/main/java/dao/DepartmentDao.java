package dao;

import models.Departments;

import java.util.List;

public interface DepartmentDao{

    // LIST
    List<Departments> getAll();

    // CREATE
    void add(Departments dept);

    // READ
    Departments findById(int id);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllDepartments();
}