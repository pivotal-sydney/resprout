package io.pivotal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@DiscriminatorValue(value = "homebrew")
public class HomebrewPackage extends Package {

    public HomebrewPackage() {
    }

    public HomebrewPackage(String name, String description) {
        super(name, description);
    }
}