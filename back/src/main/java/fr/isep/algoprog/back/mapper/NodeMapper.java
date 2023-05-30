package fr.isep.algoprog.back.mapper;

import fr.isep.algoprog.back.dto.NodeDTO;
import fr.isep.algoprog.back.entities.Node;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NodeMapper {

    public NodeDTO toDTO(Node entity){
        return NodeDTO.builder()
                .id(entity.getId())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .type(entity.getType())
                .description(entity.getDescription())
                .build();
    }

    public List<NodeDTO> toDTO(List<Node> entities){
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
