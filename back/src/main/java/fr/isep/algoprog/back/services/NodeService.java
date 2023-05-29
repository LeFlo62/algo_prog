package fr.isep.algoprog.back.services;

import fr.isep.algoprog.back.entities.Node;
import fr.isep.algoprog.back.model.Graph;
import fr.isep.algoprog.back.repositories.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        double[][] distanceMatrix = new double[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                Node node1 = nodes.get(i);
                Node node2 = nodes.get(j);

                double distance = getDistance(node1, node2);
                distanceMatrix[i][j] = distance / transportSpeed;
                distanceMatrix[j][i] = distance / transportSpeed;
            }
        }

        //TODO prendre la node la plus proche de leur hotel
        int startNode = RANDOM.nextInt(nodes.size());

        Graph graph = new Graph(distanceMatrix);

        List<Node> path = new ArrayList<>();

        for (int i = 0; i < stayDuration; ++i) {
            List<Integer> pathAlreadyTaken = path.stream().map(nodes::indexOf).collect(Collectors.toList());

            List<Integer> shortestPath = findShortestPathOfLengthN(graph, pathAlreadyTaken, startNode, maxPOI);
            System.out.println("Shortest path: " + Arrays.toString(shortestPath.toArray()));
            List<Node> selectedNodes = shortestPath.stream().map(nodes::get).collect(Collectors.toList());

            path.addAll(createPath(selectedNodes));
        }
        System.out.println("-----------");
        System.out.println(Arrays.toString(path.stream().map(node -> node.getTags().getName()).toArray()));

        return path;
    }

    private List<Integer> findShortestPathOfLengthN(Graph graph, List<Integer> pathAlreadyTaken, int startNode, int maxPOI) {
        List<Integer> path = new ArrayList<>();

        path.add(startNode);
        for(int i = 0; i < maxPOI; ++i){
            int nextMinNode = -1;
            double nextMinDistance = Double.POSITIVE_INFINITY;
            for(int j = 0; j < graph.getVertexCount(); ++j){
                if(!path.contains(j) && !pathAlreadyTaken.contains(j)){
                    double distance = graph.getDistance(path.get(i), j);
                    if(distance < nextMinDistance){
                        nextMinDistance = distance;
                        nextMinNode = j;
                    }
                }
            }

            path.add(nextMinNode);
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

        int n = 1000;
        double pathLength = distanceForPath(toVisit, path);
        while (true) {
            double interPathLength = pathLength;
            for (int l = 0; l < n; ++l) {
                List<Integer> clone = new ArrayList<>();
                clone.addAll(path);

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
        System.out.println("Micruit: " + Arrays.toString(path.toArray()));
        Collections.rotate(path, -path.indexOf(0));
        path.add(0);
        System.out.println("Micruit2: " + Arrays.toString(path.toArray()));
        List<Node> pathNode = new ArrayList<>();
        for (int i = 0; i < path.size(); ++i) {
            pathNode.add(toVisit.get(path.get(i)));
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
