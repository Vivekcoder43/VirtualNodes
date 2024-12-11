package models;

import java.util.Set;

public interface Node {
    String getName();
    Set<Node> getChildren();
    void addChild(Node child);

    void setChildren(Set<Node> children);

    Node getParent();

    void setName(String name);

    void setParent(Node pNode);
}