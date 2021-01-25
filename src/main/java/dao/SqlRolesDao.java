package dao;

import models.Roles;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlRolesDao implements RolesDao{


    private final Sql2o sql2o;

    public SqlRolesDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Roles> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM roles") //raw sql
                    .executeAndFetch(Roles.class); //fetch a list
        }
    }

    @Override
    public void add(Roles role) {
        String sql = "INSERT INTO roles(name) VALUES(:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(role)
                    .executeUpdate()
                    .getKey();
            role.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Roles findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM roles WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Roles.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String name) {
        String sql = "UPDATE roles SET name = :name WHERE id=:id";
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
        String sql = "DELETE from roles WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAllRoles() {

    }
}
