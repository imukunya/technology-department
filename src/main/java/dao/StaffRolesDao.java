package dao;

import models.StaffRoles;

import java.util.List;

public interface StaffRolesDao {
    // LIST
    List<StaffRoles> getAll();

    // CREATE
    void add(StaffRoles staff);

    // READ
    StaffRoles findById(int id);

    //UPDATE
    void update(int id, String name);

    //add existing role to user
    void addRoleToUser(int userID,int roleID);

    //DELETE
    void deleteById(int id);
    void clearAllStaff();
}
