package models;

import java.util.HashSet;
import java.util.Set;


public class VirtualNode implements Node {
    private String name;
    private Node parent;
    private Set<Node> children = new HashSet<>();


    @Override
    public String getName() {
        return "";
    }

    @Override
    public Set<Node> getChildren() {
        return children;
    }

    @Override
    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public void setChildren(Set<Node> children) {
        this.children = children;
    }

    @Override
    public Node getParent() {
        return parent;
    }
}
