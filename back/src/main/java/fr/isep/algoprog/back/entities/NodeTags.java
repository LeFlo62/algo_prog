package fr.isep.algoprog.back.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NodeTags {

    private String name;
    private String start_date;
    private String tourism;
    private String alt_name;
    private String artist_name;
    private String artwork_type;
    private String description;
    private String historic;
    private String wikidata;

}
