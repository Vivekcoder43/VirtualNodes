package Repository;

import models.ConnectionGroup;
import models.Node;
import models.VirtualNode;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface Repository {
    ConnectionGroup save(ConnectionGroup connectionGroup);
    ConnectionGroup findByName(String name);
    VirtualNode findNodeByName(String name);
    void addNode(VirtualNode node);
    void deleteConnectionGroup(String groupName);

    void deleteNode(String nodeName);

    List<VirtualNode> findAllNodes();

    void deleteNodes();

    void resetConnectionGroups();

    Map<String,ConnectionGroup> getAllConnectionGroups();

    ConnectionGroup findConnectionGroupByNodeName(Node node);

    // Other methods as needed, e.g., findAll, delete
}
