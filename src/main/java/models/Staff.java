package models;

import java.util.Objects;

public class Staff {
    public int id;
    public String name;

    public Staff(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id &&
                Objects.equals(name, staff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
