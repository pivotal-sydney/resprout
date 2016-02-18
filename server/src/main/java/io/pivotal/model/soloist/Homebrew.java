package io.pivotal.model.soloist;

import java.util.List;

public class Homebrew {

    private List<String> formulas;
    private List<String> casks;

    public List<String> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<String> formulas) {
        this.formulas = formulas;
    }

    public List<String> getCasks() {
        return casks;
    }

    public void setCasks(List<String> casks) {
        this.casks = casks;
    }
}
