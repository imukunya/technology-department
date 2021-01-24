package dao;

import models.Responsibilities;

import java.util.List;

public interface ResponsibilitiesDao{

    // LIST
    List<Responsibilities> getAll();

    // CREATE
    void add(Responsibilities resp);

    // READ
    Responsibilities findById(int id);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllResponsibilities();
}