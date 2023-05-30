package fr.isep.algoprog.back.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class NodeDTO {

    private String id;
    private double lon;
    private double lat;
    private String type;
    private String description;

}
