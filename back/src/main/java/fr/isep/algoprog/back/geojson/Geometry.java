package fr.isep.algoprog.back.geojson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Geometry {

    private final String type = "LineString";
    private double[][] coordinates;

}
