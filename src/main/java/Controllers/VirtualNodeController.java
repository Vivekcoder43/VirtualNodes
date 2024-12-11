package Controllers;

import Services.VirtualNodeService;
import models.VirtualNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController("/api/virtualNode")
public class VirtualNodeController {

    @Autowired
    private VirtualNodeService virtualNodeService;

    @PostMapping("/{nodeName}")
    public ResponseEntity<VirtualNode> addNode(@PathVariable String nodeName) {
        VirtualNode node= new VirtualNode();
        node.setName(nodeName);
        virtualNodeService.appendNode(node);
        return ResponseEntity.ok(node);
    }

    @GetMapping
    public ResponseEntity<List<VirtualNode>> getAllNodes() {
        List<VirtualNode> virtualNodes=new ArrayList<>();
        virtualNodes.addAll(virtualNodeService.findNodes());
        return ResponseEntity.ok(virtualNodes);
    }

    @GetMapping("/{nodeName}")
    public ResponseEntity<VirtualNode> getNode(@PathVariable String nodeName) {
        return ResponseEntity.ok(virtualNodeService.findNode(nodeName));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNodes() {
        virtualNodeService.deleteAllNodes();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{nodeName}")
    public ResponseEntity<Void> deleteNode(@PathVariable String nodeName) {
        virtualNodeService.deleteNode(nodeName);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        System.err.println("Exception: " + ex.getMessage());
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }


}
