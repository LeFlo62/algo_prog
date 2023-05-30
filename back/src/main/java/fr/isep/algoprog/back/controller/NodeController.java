package fr.isep.algoprog.back.controller;

import fr.isep.algoprog.back.dto.NodeDTO;
import fr.isep.algoprog.back.dto.ResearchDTO;
import fr.isep.algoprog.back.geojson.FeatureCollection;
import fr.isep.algoprog.back.mapper.GeoJSONMapper;
import fr.isep.algoprog.back.mapper.NodeMapper;
import fr.isep.algoprog.back.services.NodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class NodeController {

    private NodeService nodeService;
    private NodeMapper nodeMapper;
    private GeoJSONMapper geoJSONMapper;

    @GetMapping("/searchPath")
    public List<NodeDTO> searchPath(ResearchDTO researchDTO){
        return nodeMapper.toDTO(nodeService.getPath(researchDTO.getStayDuration(), researchDTO.getTransportSpeed(), researchDTO.getStartDay(), researchDTO.getEndDay(), researchDTO.getTimeSpent(), researchDTO.getType()));
    }

    /**
     * USED IN DEV ONLY
     * @param researchDTO
     * @return
     */
    @GetMapping("/geopath")
    public FeatureCollection geoPath(ResearchDTO researchDTO){
        return geoJSONMapper.toDTO(
                nodeService.getPath(researchDTO.getStayDuration(), researchDTO.getTransportSpeed(), researchDTO.getStartDay(), researchDTO.getEndDay(), researchDTO.getTimeSpent(), researchDTO.getType())
                , (researchDTO.getEndDay() - researchDTO.getStartDay())/researchDTO.getTimeSpent()+1
        );
    }

}
