package dao;

import models.Responsibilities;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlResponsibilitiesDao implements ResponsibilitiesDao {

    private final Sql2o sql2o;

    public SqlResponsibilitiesDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public List<Responsibilities> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM responsibilities") //raw sql
                    .executeAndFetch(Responsibilities.class); //fetch a list
        }
    }

    @Override
    public void add(Responsibilities resp) {

        String sql = "INSERT INTO responsibilities(name) VALUES(:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(resp)
                    .executeUpdate()
                    .getKey();
            resp.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public Responsibilities findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM responsibilities WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Responsibilities.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String name) {
        String sql = "UPDATE responsibilities SET name = :name WHERE id=:id";
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

        String sql = "DELETE from responsibilities WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAllResponsibilities() {

    }
}
