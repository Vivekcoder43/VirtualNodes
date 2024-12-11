package Repository;

import models.ConnectionGroup;
import models.Node;
import models.VirtualNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class InMemoryRepository implements Repository {

    private Map<String, ConnectionGroup> connectionGroupsByName = new HashMap<>();
    private Map<String, VirtualNode> virtualNodesByName = new HashMap<>();


    @Override
    public ConnectionGroup save(ConnectionGroup connectionGroup) {
        connectionGroupsByName.put(connectionGroup.getName(), connectionGroup);
        return connectionGroup;
    }

    public ConnectionGroup findByName(String name) {
        return connectionGroupsByName.get(name);
    }

    public void resetConnectionGroups() {
        for (ConnectionGroup connectionGroup : connectionGroupsByName.values()) {
            connectionGroup.setRootNode(null);
        }
    }

    @Override
    public Map<String,ConnectionGroup> getAllConnectionGroups() {
        return connectionGroupsByName;
    }

    @Override
    public ConnectionGroup findConnectionGroupByNodeName(Node node) {
        for (ConnectionGroup connectionGroup : connectionGroupsByName.values()) {
            if (connectionGroup.getConnectionNodes().contains(node)) {
                return connectionGroup;
            }
        }
        return null;
    }

    @Override
    public VirtualNode findNodeByName(String name) {
        return virtualNodesByName.get(name);
    }

    @Override
    public void deleteConnectionGroup(String groupName) {
        connectionGroupsByName.remove(groupName);
    }
    @Override
    public void addNode(VirtualNode node) {
        virtualNodesByName.put(node.getName(), node);
    }


    @Override
    public void deleteNode(String nodeName) {
        virtualNodesByName.remove(nodeName);
    }

    @Override
    public List<VirtualNode> findAllNodes() {
        return (List<VirtualNode>) virtualNodesByName.values();

    }

    @Override
    public void deleteNodes() {
        virtualNodesByName.clear();
    }
}