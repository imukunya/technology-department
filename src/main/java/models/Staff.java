package models;

import java.util.Objects;

public class Staff {
    public int id;
    public String names;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id &&
                Objects.equals(names, staff.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, names);
    }

    public Staff(String name) {
        this.names = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return names;
    }

    public void setName(String name) {
        this.names = name;
    }


}
