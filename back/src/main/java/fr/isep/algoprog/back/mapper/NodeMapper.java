package fr.isep.algoprog.back.mapper;

import fr.isep.algoprog.back.dto.NodeDTO;
import fr.isep.algoprog.back.dto.NodeTagsDTO;
import fr.isep.algoprog.back.entities.Node;
import fr.isep.algoprog.back.entities.NodeTags;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NodeMapper {

    public NodeTagsDTO toDTO(NodeTags entity){
        return NodeTagsDTO.builder()
                .name(entity.getName())
                .start_date(entity.getStart_date())
                .tourism(entity.getTourism())
                .alt_name(entity.getAlt_name())
                .artwork_type(entity.getArtwork_type())
                .description(entity.getDescription())
                .historic(entity.getHistoric())
                .wikidata(entity.getWikidata())
                .build();
    }

    public NodeDTO toDTO(Node entity){
        return NodeDTO.builder()
                .id(entity.getId())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .tags(toDTO(entity.getTags()))
                .build();
    }

    public List<NodeDTO> toDTO(List<Node> entities){
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
