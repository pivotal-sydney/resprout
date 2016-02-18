package io.pivotal.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_selections")
public class UserSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="user_selection_package",
            joinColumns=@JoinColumn(name="user_selection_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="package_id", referencedColumnName="id"))
    private Set<Package> packages;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Package> getPackages() {
        return packages;
    }
}
