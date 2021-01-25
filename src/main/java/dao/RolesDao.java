package dao;

import models.Roles;

import java.util.List;

public interface RolesDao{

    // LIST
    List<Roles> getAll();

    // CREATE
    void add(Roles role);

    // READ
    Roles findById(int id);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllRoles();
}