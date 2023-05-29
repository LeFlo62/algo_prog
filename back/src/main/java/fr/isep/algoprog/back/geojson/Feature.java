package fr.isep.algoprog.back.geojson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Feature {

    private final String type = "Feature";
    private Geometry geometry;
    private Properties properties;

}
