package project.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "res", schema = "public", catalog = "mercury2022")
public class Res {
    private long id;
    private char values;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "values", nullable = true, length = -1)
    public char getValues() {
        return values;
    }

    public void setValues(char values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Res res = (Res) o;
        return id == res.id &&
                Objects.equals(values, res.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, values);
    }
}
