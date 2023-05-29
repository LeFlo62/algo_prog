package fr.isep.algoprog.back.repositories;

import fr.isep.algoprog.back.entities.Node;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends MongoRepository<Node, String> {

    @Query("{\"tags.artwork_type\": ?0}")
    List<Node> getNodesWithArtworkStyle(String artworkType);
}
