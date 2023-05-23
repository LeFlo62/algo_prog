package fr.isep.algoprog.back.services;

import fr.isep.algoprog.back.entities.Node;
import fr.isep.algoprog.back.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NodeService {

    private static final Random RANDOM = new Random();

    private NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository){
        this.nodeRepository = nodeRepository;
    }

    public List<Node> getPath(int stayDuration, int startDay, int endDay, int timeSpent, String artworkType){
        int numberOfPOI = (endDay - startDay)/timeSpent;

        List<Node> path = new ArrayList<>();

        for(int i = 0; i < stayDuration; ++i){
            Pageable pageable = PageRequest.of(i, numberOfPOI, Sort.by("lat").ascending().and(Sort.by("lon").ascending()));

            List<Node> nodes = nodeRepository.getNodesWithArtworkStyle(artworkType, pageable);
            path.addAll(createPath(nodes));
        }

        return path;
    }

    // Utiliser Valhalla ? Docker permettant de calculer les distances
    public double getDistance(Node node1, Node node2){
        return Math.sqrt(Math.pow(node1.getLat() - node2.getLat(), 2) + Math.pow(node1.getLon() - node2.getLon(), 2));
    }

    public List<Node> createPath(List<Node> toVisit){
        List<Integer> path = new ArrayList<>();
        for(int i = 0; i < toVisit.size(); ++i){
            path.add(i);
        }
        Collections.shuffle(path);
        path.add(path.get(0));

        int n = 1000;
        double pathLength = distanceForPath(toVisit, path);
        while(true){
            double interPathLength = pathLength;
            for(int l = 0; l < n; ++l){
                List<Integer> clone = new ArrayList<>();
                Collections.copy(path, clone);

                int node1 = RANDOM.nextInt(toVisit.size());
                int node2 = RANDOM.nextInt(toVisit.size());

                while(node1 == node2){
                    node2 = RANDOM.nextInt(toVisit.size());
                }

                int p1 = Math.min(node1, node2);
                int p2 = Math.max(node1, node2);

                for(int i = 0; i <= (p2-p1)/2; ++i){
                    Collections.swap(clone, p1+i, p2-i);
                }

                double d = distanceForPath(toVisit, clone);
                if(d < interPathLength){
                    interPathLength = d;
                    path = clone;
                }
            }
            if(interPathLength >= pathLength){
                break;
            } else {
                pathLength = interPathLength;
            }
        }
        List<Node> pathNode = new ArrayList<>();
        for(int i = 0; i < toVisit.size(); ++i){
            pathNode.set(i, toVisit.get(path.get(i)));
        }
        return pathNode;
    }

    public double distanceForPath(List<Node> toVisit, List<Integer> path) {
        double distance = 0;

        for(int i = 0; i < path.size()-1; ++i){
            distance += getDistance(toVisit.get(path.get(i)), toVisit.get(path.get(i+1)));
        }

        return distance;
    }

}
