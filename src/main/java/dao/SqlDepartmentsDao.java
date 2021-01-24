package dao;

import models.Departments;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlDepartmentsDao implements DepartmentDao{


    private final Sql2o sql2o;

    public SqlDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Departments> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments") //raw sql
                    .executeAndFetch(Departments.class); //fetch a list
        }
    }

    @Override
    public void add(Departments dept) {
        String sql = "INSERT INTO departments(name) VALUES(:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(dept)
                    .executeUpdate()
                    .getKey();
            dept.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Departments findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Departments.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String name) {
        String sql = "UPDATE departments SET name = :name WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllDepartments() {

    }
}
