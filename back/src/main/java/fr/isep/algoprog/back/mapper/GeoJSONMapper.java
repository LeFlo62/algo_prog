package fr.isep.algoprog.back.mapper;

import fr.isep.algoprog.back.entities.Node;
import fr.isep.algoprog.back.geojson.Feature;
import fr.isep.algoprog.back.geojson.FeatureCollection;
import fr.isep.algoprog.back.geojson.Geometry;
import fr.isep.algoprog.back.geojson.Properties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeoJSONMapper {

    private Feature toDTOFeature(List<Node> nodes, int day){
        double[][] coordinates = new double[nodes.size()][2];
        for(int i = 0; i < nodes.size(); ++i){
            coordinates[i][0] = nodes.get(i).getLon();
            coordinates[i][1] = nodes.get(i).getLat();
        }
        return Feature.builder()
                .geometry(Geometry.builder().coordinates(coordinates).build())
                .properties(Properties.builder().name("Day " + day).build())
                .build();
    }

    public FeatureCollection toDTO(List<Node> entities, int maxPOI){
        FeatureCollection.FeatureCollectionBuilder builder = FeatureCollection.builder();
        List<Feature> features = new ArrayList<>();
        int lastAdd = 0;
        List<Node> nodesToAdd = new ArrayList<>();
        Node previousNode = entities.get(0);
        nodesToAdd.add(previousNode);
        for(int i = 1; i < entities.size(); ++i){
            Node currentNode = entities.get(i);
            if(currentNode.equals(previousNode)){
                features.add(this.toDTOFeature(nodesToAdd, i/maxPOI-1));
                nodesToAdd.clear();
            }
            nodesToAdd.add(currentNode);
            previousNode = currentNode;
        }
        features.add(this.toDTOFeature(nodesToAdd, entities.size()/maxPOI - 1));
        builder.features(features);
        return builder.build();
    }

}
