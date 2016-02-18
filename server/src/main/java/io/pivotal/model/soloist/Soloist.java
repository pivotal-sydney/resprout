package io.pivotal.model.soloist;

import java.util.List;

public class Soloist {

    private List<String> recipes;
    private NodeAttributes node_attributes;

    public List<String> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public void setNode_attributes(NodeAttributes node_attributes) {
        this.node_attributes = node_attributes;
    }

    public NodeAttributes getNode_attributes() {
        return node_attributes;
    }
}
