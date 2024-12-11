package Services;


import Repository.Repository;
import models.ConnectionGroup;
import models.Node;
import models.VirtualNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionGroupService {
    @Autowired
    private Repository repository;
    @Autowired
    private VirtualNodeService virtualNodeService;

    public ConnectionGroup createConnectionGroup(ConnectionGroup connectionGroup) {
        // ... (existing logic for creating the hierarchy)
        repository.save(connectionGroup);
        return connectionGroup;
    }

    public ConnectionGroup findConnectionGroupByName(String groupName) {
        return repository.findByName(groupName);
    }

    public void deleteConnectionGroup(String groupName) {
        repository.deleteConnectionGroup(groupName);
    }

    public boolean addConnection(ConnectionGroup group, Node parentNode, Node childNode) {
        if (group.getConnectionNodes().contains(parentNode)) {
            if (group.getConnectionNodes().contains(childNode)){
                childNode.setParent(parentNode);
                parentNode.getChildren().add(childNode);
            }
            else{
                for (ConnectionGroup connectionGroup:repository.getAllConnectionGroups().values()){
                    if (connectionGroup.getConnectionNodes().contains(childNode)){
                        return false;
                    }
                }
                parentNode.addChild(childNode);
                group.getConnectionNodes().add(childNode);
            }
        }

        return true;

    }

    public boolean removeNodeFromConnection(ConnectionGroup group, Node node) {
        if (group.getConnectionNodes().contains(node)) {
           virtualNodeService.deleteNode(node.getName());
            return true;
        }
        return false;
    }

    public ConnectionGroup findConnectionGroupByNodeName(String nodeName) {
           Node node= virtualNodeService.findNode(nodeName);
           return repository.findConnectionGroupByNodeName(node);
    }
}
