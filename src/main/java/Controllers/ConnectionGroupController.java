package Controllers;

import DTO.ConnectionGroupDTO;
import Services.ConnectionGroupService;
import Services.VirtualNodeService;
import models.ConnectionGroup;
import models.Node;
import models.VirtualNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@RequestMapping("/api/connection-groups")
public class ConnectionGroupController {

    @Autowired
    private ConnectionGroupService connectionGroupService;
    private VirtualNodeService virtualNodeService;


    @PostMapping
    public ResponseEntity<ConnectionGroup> createConnectionGroup(@RequestBody ConnectionGroupDTO connectionGroupDTO) {
        // Create the root node
        VirtualNode rootNode = new VirtualNode();
        rootNode.setName(connectionGroupDTO.getRootNodeName());
        ConnectionGroup connectionGroup = new ConnectionGroup(connectionGroupDTO.getName(),rootNode);
        ConnectionGroup createdGroup = connectionGroupService.createConnectionGroup(connectionGroup);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping("/group/{groupName}")
    public ResponseEntity<ConnectionGroup> getConnectionGroup(@PathVariable String groupName) {
        ConnectionGroup group = connectionGroupService.findConnectionGroupByName(groupName);
        if (group != null) {
            return ResponseEntity.ok(group);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{nodeName}")
    public ResponseEntity<ConnectionGroup> getConnectionGroupByNodeName(@PathVariable String nodeName) {

        ConnectionGroup connectionGroup= connectionGroupService.findConnectionGroupByNodeName(nodeName);
        if (connectionGroup != null) {
            return ResponseEntity.ok(connectionGroup);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{groupName}/{parentNodeName}/{childNodename}")
    public ResponseEntity<String> addConnection(@PathVariable String groupName, @PathVariable String parentNodeName, @PathVariable String childNodename) {
        ConnectionGroup group = connectionGroupService.findConnectionGroupByName(groupName);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }

        VirtualNode parentNode = virtualNodeService.findNode(parentNodeName);
        if (parentNode == null) {
            return ResponseEntity.notFound().build();
        }

        VirtualNode childNode = virtualNodeService.findNode(childNodename);
        if (childNode == null) {
            return ResponseEntity.notFound().build();
        }

        if (connectionGroupService.addConnection(group,parentNode,childNode)){
            return ResponseEntity.ok("Child node added successfully");
        };

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteConnection(@PathVariable String groupName, @PathVariable String nodeName) {
        ConnectionGroup group = connectionGroupService.findConnectionGroupByName(groupName);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        Node node=virtualNodeService.findNode(nodeName);
        if (node==null) {
            return ResponseEntity.notFound().build();
        }

        if (connectionGroupService.removeNodeFromConnection(group,node)){
            return ResponseEntity.ok("Child node removed successfully");
        }else{
            return ResponseEntity.notFound().build();
        }


    }

    @DeleteMapping("/{groupName}")
    public ResponseEntity<Void> deleteConnectionGroup(@PathVariable String groupName) {
        connectionGroupService.deleteConnectionGroup(groupName);
        return ResponseEntity.noContent().build();
    }

    // Other endpoints for creating, updating, and deleting connection groups and nodes

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        System.err.println("Exception: " + ex.getMessage());
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }
}