package fr.isep.algoprog.back.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("nodes")
public class Node {

    private String id;
    private double lon;
    private double lat;
    private String type;
    private String name;
    private String description;

}
