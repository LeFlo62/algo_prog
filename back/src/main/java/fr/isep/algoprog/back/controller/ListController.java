package fr.isep.algoprog.back.controller;

import fr.isep.algoprog.back.dto.CityDTO;
import fr.isep.algoprog.back.dto.SiteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/list")
@RestController
public class ListController {

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getCities(){

        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/site")
    public ResponseEntity<List<SiteDTO>> getSites(@RequestParam("city") String city){
        // http://localhost:8080/list/site?city=Paris
        return ResponseEntity.internalServerError().build();
    }

}
