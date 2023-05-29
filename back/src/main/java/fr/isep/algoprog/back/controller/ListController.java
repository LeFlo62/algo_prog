package fr.isep.algoprog.back.controller;

import fr.isep.algoprog.back.services.NodeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/list")
@RestController
@AllArgsConstructor
public class ListController {

    private NodeService nodeService;

    @GetMapping("/artworks")
    public List<String> getArtworks(){
        return nodeService.getArtworks();
    }

}
