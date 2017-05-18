/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportsandroads;

import java.util.Arrays;

/**
 *
 * @author djbarbosa
 */
public class Graph {

    private int order;
    private int size;
    private int lowerNode = -1;
    private int minimumNetworkCost = 0;
    //private LinkedList<Node> nodes = new LinkedList<>();
    //private LinkedList<Integer> airports = new LinkedList<>();

    private int[][] costs;
    private int[][] minimumCosts;
    private int[][] minimumRoads;
    private int[][] minimumRoadsCosts;
    private int[] totalCosts;
    private int[] selectedRoads;

    public Graph(int order, int size, int[][] costs) {
        this.order = order;
        this.size = size;
        this.costs = costs;
        minimumCosts = new int[order][order];
        for (int[] nodes : minimumCosts) {
            Arrays.fill(nodes, -1);
        }
        minimumRoads = new int[order][order];
        for (int[] nodes : minimumRoads) {
            Arrays.fill(nodes, -1);
        }
        minimumRoadsCosts = new int[order][order];
        for (int[] nodes : minimumRoadsCosts) {
            Arrays.fill(nodes, -1);
        }
        totalCosts = new int[order];
        Arrays.fill(totalCosts, 0);
        selectedRoads = new int[order];
        Arrays.fill(selectedRoads, -1);
    }

    private void setTotalCosts() {
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                totalCosts[i] = totalCosts[0] + costs[i][j];
            }
        }
    }

    public void minimumNetworkCost() {
        searchingNetworkConnection(getLowerNode());
        for (int i = 0; i < order; i++) {
            if (getLowerNode() != i) {
                searchingNetworkConnection(i);
            }
        }
        cleanAirports();
        for (int i = 0; i < order; i++) {
            int airportCost = 0;
            minimumNetworkCost = minimumNetworkCost + get(airports.get(i)).airportCost + findRoadsCosts(airports.get(i));
        }
    }

    public int findRoadsCosts(int node) {
        int totalCost = 0, nodeCost = 0;
        if (get(node).selectedRoad != null) {
            nodeCost = get(node).selectedRoad.cost;
            if (nodeCost == -1) {
                nodeCost = 0;
            }
        } else {
            nodeCost = 0;
        }
        for (int i = 0; i < nodes.size(); i++) {
            if (get(i).selectedRoad != null) {
                if (get(i).selectedRoad.minimumRoad == node) {
                    int edgeCost = get(i).selectedRoad.cost - nodeCost;
                    if (node != i) {
                        minimumCosts[node][i] = edgeCost;
                        minimumCosts[i][node] = edgeCost;
                    }
                    totalCost = totalCost + edgeCost + findRoadsCosts(i);
                }
            }
        }

        return totalCost;

    }

    public void searchingNetworkConnection(int node) {
        int lowerCost = 9999, selectedRoad = -1;
        for (int i = 0; i < order; i++) {
            int cost = minimumRoadsCosts[i][node];
            if (cost != -1) {
                if (cost < lowerCost && cost < costs[node][node]) {
                    lowerCost = cost;
                    selectedRoad = i;
                }
            }
        }
        if (selectedRoad != -1) {
            selectedRoads[node] = selectedRoad;
        } else {
            bellmanFord(node);
            for (int i = 0; i < order; i++) { //selectedRoad can't be null
                int cost = minimumRoadsCosts[i][node];
                if (cost < lowerCost && cost < costs[node][node]) {
                    lowerCost = cost;
                    selectedRoad = i;
                }
            }
            selectedRoads[node] = selectedRoad;
            minimumCosts[node][node] = costs[node][node];
        }
    }

    public void cleanAirports() {
        for (int i = 0; i < order; i++) {
            boolean someRoadToAirport = false;
            if (minimumCosts[i][i] != -1) {
                for (int j = 0; j < order; j++) {
                    if (selectedRoads[j] != -1) {
                        if (selectedRoads[j] == i) {
                            someRoadToAirport = true;
                            break;
                        }
                    }
                }
                if (!someRoadToAirport) {
                    int lowerCost = 9999, selectedRoad = -1;
                    for (int j = 0; j < order; j++) { //get(airports.get(i)).minimumRoads.size()
                        int cost = minimumRoadsCosts[j][i];
                        if (cost < lowerCost && cost < costs[i][i]) {
                            lowerCost = cost;
                            selectedRoad = j;
                        }
                    }
                    if (selectedRoad != -1) {
                        selectedRoads[i] = selectedRoad;
                        minimumCosts[i][i] = -1;
                        i--;
                    }
                }
            }
        }
    }

    public int getLowerNode() {
        if (lowerNode == -1) {
            setLowerNode();
        }
        return lowerNode;
    }

    private void setLowerNode() {
        int lowerCost = 9999;
        for (int i = 0; i < order; i++) {
            if (totalCosts[i] < lowerCost) {
                lowerCost = totalCosts[i];
                lowerNode = i;
            }
        }
    }

    public void bellmanFord(int toNode) {
        int noChange = 0;
        minimumRoadsCosts[toNode][toNode] = 0;
        minimumRoads[toNode][toNode] = -1;
        for (int i = 1; i < size + 1; i++) { //Really it should be size, because starting in 0 is size - 1...
            if (!searchingMinimumRoads(toNode, toNode, 0, 0, i)) {
                noChange++;
            }
            if (noChange == 2) {
                break;
            }
        }
    }

    public boolean searchingMinimumRoads(int toNode, int node, int cost, int jump, int jumpRestriction) {
        boolean result = false;
        if (jump == jumpRestriction - 1) {
            for (int i = 0; i < order; i++) {
                if (costs[node][i] != -1 && node != i) {
                    if (addMinimumRoad(i, toNode, node, cost + costs[node][i])) {
                        result = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < order; i++) {
                if (costs[node][i] != -1 && node != i) {
                    if (searchingMinimumRoads(toNode, i, cost + costs[node][i], jump + 1, jumpRestriction)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public boolean addMinimumRoad(int node, int lastNode, int minimumRoad, int cost) {
        boolean result = false;
        if (node != lastNode) {
            int roadCost = minimumRoadsCosts[lastNode][node];
            if (roadCost != -1) {
                if (roadCost > cost) {
                    minimumRoadsCosts[lastNode][node] = cost;
                    minimumRoads[lastNode][node] = minimumRoad;
                    result = true;
                }
            } else {
                minimumRoadsCosts[lastNode][node] = cost;
                minimumRoads[lastNode][node] = minimumRoad;
                result = true;
            }
        }
        return result;
    }

    public int getMinimumNetworkCost() {
        return minimumNetworkCost;
    }

    public int[][] getMatrixOfMinimumNetworkCost() {
        return minimumCosts;
    }

}