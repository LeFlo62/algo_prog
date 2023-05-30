package fr.isep.algoprog.back.controller;

import fr.isep.algoprog.back.services.NodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/list")
@RestController
@AllArgsConstructor
public class ListController {

    private NodeService nodeService;

    @GetMapping("/types")
    public List<String> getArtworks(){
        return nodeService.getTypes();
    }

}
