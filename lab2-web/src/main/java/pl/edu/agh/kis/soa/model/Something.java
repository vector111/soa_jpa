package pl.edu.agh.kis.soa.model;

import javax.persistence.*;

@Entity(name = "Something")
@Table(name = "something")
public class Something {

    @Id
    @Column(name = "something_id")
    @GeneratedValue
    Integer SthId;
    @Column(name = "name_of_sth")
    String sthName;

    public Integer getSthId() {
        return SthId;
    }

    public String getSthName() {
        return sthName;
    }

    public void setSthName(String sthName) {
        this.sthName = sthName;
    }
}
