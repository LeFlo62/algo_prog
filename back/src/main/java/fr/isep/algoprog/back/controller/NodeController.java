package fr.isep.algoprog.back.controller;

import fr.isep.algoprog.back.dto.NodeDTO;
import fr.isep.algoprog.back.services.NodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class NodeController {

    private NodeService nodeService;

    @GetMapping("/searchPath")
    public NodeDTO[] searchPath(){
        return null;
    }

}
