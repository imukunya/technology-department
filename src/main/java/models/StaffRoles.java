package models;

import java.util.Objects;

public class StaffRoles {

    int staff_id;
    int role_id;

    public StaffRoles(int staff_id, int role_id) {
        this.staff_id = staff_id;
        this.role_id = role_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffRoles that = (StaffRoles) o;
        return staff_id == that.staff_id &&
                role_id == that.role_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(staff_id, role_id);
    }
}
