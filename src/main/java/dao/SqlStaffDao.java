package dao;

import models.Staff;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlStaffDao implements StaffDao {

    private final Sql2o sql2o;

    public SqlStaffDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public List<Staff> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff") //raw sql
                    .executeAndFetch(Staff.class); //fetch a list
        }
    }

    @Override
    public void add(Staff staff) {
        String sql = "INSERT INTO staff(name) VALUES(:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(staff)
                    .executeUpdate()
                    .getKey();
            staff.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public Staff findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM staff WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Staff.class); //fetch an individual item
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
