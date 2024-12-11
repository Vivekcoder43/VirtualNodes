package Services;

import Repository.Repository;
import models.Node;
import models.VirtualNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualNodeService {

    @Autowired
    private Repository repository;

    public void deleteNode(String nodeName) {
        Node node=repository.findNodeByName(nodeName);
        Node pNode=node.getParent();
        for(Node child:node.getChildren()){
                child.setParent(pNode);
            }

        repository.deleteNode(nodeName);
    }

    public VirtualNode findNode(String parentNodeName) {
        return repository.findNodeByName(parentNodeName);
    }

    public void appendNode(VirtualNode virtualNode) {
        repository.addNode(virtualNode);
    }

    public VirtualNode updateChildrenForNode(String parentNode, String childNode) {
        Node pNode=repository.findNodeByName(parentNode);
        if (pNode == null) {
            return null;
        }
        VirtualNode cNode=repository.findNodeByName(childNode);
        if (cNode == null) {
            cNode = new VirtualNode();
            cNode.setName(childNode);
        }

        repository.addNode(cNode);
        pNode.addChild(cNode);
        return cNode;
    }

    public List<VirtualNode> findNodes() {
        return repository.findAllNodes();
    }

    public void deleteAllNodes() {
        repository.resetConnectionGroups();
        repository.deleteNodes();
    }
}
