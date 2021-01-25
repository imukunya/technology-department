package dao;

import models.StaffRoles;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlStaffRolesDao implements StaffRolesDao {

    private final Sql2o sql2o;

    public SqlStaffRolesDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public List<StaffRoles> getAll() {
        return null;
    }

    @Override
    public void add(StaffRoles staffRole) {
        String sql = "INSERT INTO staff_roles(staff_id,role_id) VALUES(:staff_id,:role_id)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(staffRole)
                    .executeUpdate()
                    .getKey();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public StaffRoles findById(int id) {
        return null;
    }

    @Override
    public void update(int id, String name) {

    }

    @Override
    public void addRoleToUser(int userID, int roleID) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllStaff() {

    }
}
