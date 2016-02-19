package io.pivotal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "homebrew_cask")
public class HomebrewCaskPackage extends Package {
    public HomebrewCaskPackage() {
    }

    public HomebrewCaskPackage(String name, String description) {
        super(name, description);
    }
}