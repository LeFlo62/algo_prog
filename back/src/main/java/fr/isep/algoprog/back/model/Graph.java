package fr.isep.algoprog.back.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {

    private double[][] distanceMatrix;
    private double[] heuristics;

    public Graph(double[][] distanceMatrix){
        this.distanceMatrix = distanceMatrix;
        this.heuristics = new double[distanceMatrix.length];
    }

    public int getVertexCount(){
        return this.distanceMatrix.length;
    }

    public List<Integer> getNeighbors(int u){
        return IntStream.range(1, this.distanceMatrix.length)
                .filter(v -> this.distanceMatrix[u][v] != -1)
                .boxed()
                .collect(Collectors.toList());
    }

    public double getDistance(int u, int v){
        return this.distanceMatrix[u][v];
    }

    public double getHeuristic(int u){
        return this.heuristics[u];
    }

    public void setHeuristics(int u, double value){
        this.heuristics[u] = value;
    }

}
