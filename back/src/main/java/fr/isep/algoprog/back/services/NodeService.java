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
    private static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers

    private NodeRepository nodeRepository;


    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public List<Node> getPath(int stayDuration, double transportSpeed, int startDay, int endDay, int timeSpent, String artworkType) {
        int maxPOI = (endDay - startDay) / timeSpent;

        List<Node> nodes = nodeRepository.getNodesWithArtworkStyle(artworkType);
        double[][] dinstanceMatrix = new double[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                Node node1 = nodes.get(i);
                Node node2 = nodes.get(j);

                double distance = getDistance(node1, node2);
                dinstanceMatrix[i][j] = distance;
                dinstanceMatrix[j][i] = distance;
            }
        }


        List<Node> path = new ArrayList<>();

        for (int i = 0; i < stayDuration; ++i) {

            //double transportTime = distanceMatrix[i][j] / transportSpeed;
            path.addAll(createPath(nodes));
        }

        return path;
    }

    // Utiliser Valhalla ? Docker permettant de calculer les distances
    public double getDistance(Node node1, Node node2) {
        return calculateDistance(node1.getLat(), node1.getLon(), node2.getLat(), node2.getLon());
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double latDiff = lat2Rad - lat1Rad;
        double lonDiff = lon2Rad - lon1Rad;

        double haversineLat = Math.sin(latDiff / 2) * Math.sin(latDiff / 2);
        double haversineLon = Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double centralAngle = haversineLat + Math.cos(lat1Rad) * Math.cos(lat2Rad) * haversineLon;

        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(centralAngle));

        return distance;
    }

    public List<Node> createPath(List<Node> toVisit) {
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < toVisit.size(); ++i) {
            path.add(i);
        }
        Collections.shuffle(path);
        path.add(path.get(0));

        int n = 1000;
        double pathLength = distanceForPath(toVisit, path);
        while (true) {
            double interPathLength = pathLength;
            for (int l = 0; l < n; ++l) {
                List<Integer> clone = new ArrayList<>();
                Collections.copy(path, clone);

                int node1 = RANDOM.nextInt(toVisit.size());
                int node2 = RANDOM.nextInt(toVisit.size());

                while (node1 == node2) {
                    node2 = RANDOM.nextInt(toVisit.size());
                }

                int p1 = Math.min(node1, node2);
                int p2 = Math.max(node1, node2);

                for (int i = 0; i <= (p2 - p1) / 2; ++i) {
                    Collections.swap(clone, p1 + i, p2 - i);
                }

                double d = distanceForPath(toVisit, clone);
                if (d < interPathLength) {
                    interPathLength = d;
                    path = clone;
                }
            }
            if (interPathLength >= pathLength) {
                break;
            } else {
                pathLength = interPathLength;
            }
        }
        List<Node> pathNode = new ArrayList<>();
        for (int i = 0; i < toVisit.size(); ++i) {
            pathNode.set(i, toVisit.get(path.get(i)));
        }
        return pathNode;
    }

    public double distanceForPath(List<Node> toVisit, List<Integer> path) {
        double distance = 0;

        for (int i = 0; i < path.size() - 1; ++i) {
            distance += getDistance(toVisit.get(path.get(i)), toVisit.get(path.get(i + 1)));
        }

        return distance;
    }

}
