package fr.isep.algoprog.back.dto;

import fr.isep.algoprog.back.entities.NodeTags;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class NodeDTO {

    private String id;
    private double lon;
    private double lat;
    private NodeTagsDTO tags;

}
