package fr.isep.algoprog.back.repositories;

import fr.isep.algoprog.back.entities.Node;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends MongoRepository<Node, String> {

    @Query("{\"type\": ?0}")
    List<Node> getNodesWithType(String artworkType);

    @Aggregation(pipeline = {
            "{$group: {_id: '$type'}}"
    })
    List<String> getTypes();
}
