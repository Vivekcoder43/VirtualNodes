package models;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ConnectionGroup {
    private String name;
    private Node rootNode;
    private Set<Node> connectionNodes;

    public ConnectionGroup() {
        connectionNodes = new HashSet<>();
    }

    public ConnectionGroup(String name) {
        this.name = name;
        connectionNodes = new HashSet<>();
    }

    public ConnectionGroup(String name, Node rootNode) {
        this.name = name;
        this.rootNode = rootNode;
        this.connectionNodes = new HashSet<Node>();
    }

}