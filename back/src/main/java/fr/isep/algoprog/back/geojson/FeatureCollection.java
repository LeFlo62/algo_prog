package fr.isep.algoprog.back.geojson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class FeatureCollection {

    private final String type = "FeatureCollection";
    private List<Feature> features;

}
