package io.pivotal.model;

import org.springframework.data.annotation.ReadOnlyProperty;
import javax.persistence.*;

@Entity
@Table(name = "packages")
@Inheritance(strategy =  InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type")
public abstract class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    @ReadOnlyProperty
    @Column(name = "package_type", insertable=false, updatable=false)
    private String packageType;

    public Package() {
    }

    public Package(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageType() {
        return packageType;
    }
}
