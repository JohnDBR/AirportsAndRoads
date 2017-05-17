/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportsandroads;

import java.util.Arrays;
import airportsandroads.linkedlist.LinkedList;

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
    private int[][] minimumRoadsCost;
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
        minimumRoadsCost = new int[order][order];
        for (int[] nodes : minimumRoadsCost) {
            Arrays.fill(nodes, -1);
        }
        totalCosts = new int[order];
        selectedRoads = new int[order];
        Arrays.fill(selectedRoads, -1);
    }
    private void setTotalCosts() {
            totalCost = totalCost + airportCost;
            for (int i = 0; i < roadsCosts.size(); i++) {
                totalCost = totalCost + roadsCosts.get(i);
            }
            //for (Integer roadCost : roadsCosts) {
            //    totalCost = totalCost + roadCost;
            //}
        }

    public void addNode(int number) {
        nodes.add(new Node(number));
    }

    public void minimumNetworkCost() {
        searchingNetworkConnection(getLowerNode().number);
        for (int i = 0; i < nodes.size(); i++) {
            if (getLowerNode().number != i) {
                searchingNetworkConnection(i);
            }
        }
        //searchingNetworkConnection(getLowerNode().number);
        cleanAirports();
        for (int i = 0; i < airports.size(); i++) {
            minimumCosts[airports.get(i)][airports.get(i)] = get(airports.get(i)).getAirportCost();
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
        for (int i = 0; i < get(node).minimumRoads.size(); i++) {
            int cost = get(node).minimumRoads.get(i).cost;
            if (cost < lowerCost && cost < get(node).airportCost) {
                lowerCost = cost;
                selectedRoad = i;
            }
        }
        if (selectedRoad != -1) {
            get(node).selectedRoad = get(node).minimumRoads.get(selectedRoad);
        } else {
            bellmanFord(node);
            for (int i = 0; i < get(node).minimumRoads.size(); i++) { //selectedRoad can't be null
                int cost = get(node).minimumRoads.get(i).cost;
                if (cost < lowerCost && cost < get(node).airportCost) {
                    lowerCost = cost;
                    selectedRoad = i;
                }
            }
            get(node).selectedRoad = get(node).minimumRoads.get(selectedRoad);
            airports.add(node);
        }
        //for (int i = 0; i < get(node).roadsCosts.size(); i++) {
        //    if (get(node).roadsCosts.get(i) != -1) {
        //        searchingNetworkConnection(i);
        //    }
        //}
    }

    public void cleanAirports() {
        for (int i = 0; i < airports.size(); i++) {
            boolean someRoadToAirport = false;
            for (int j = 0; j < nodes.size(); j++) {
                if (get(j).selectedRoad != null) {
                    if (get(j).selectedRoad.lastNode == airports.get(i)) {
                        someRoadToAirport = true;
                        break;
                        //Should break here, but I'm scared... or make a while but dont!
                    }
                }
            }
            if (!someRoadToAirport) {
                int lowerCost = 9999, selectedRoad = -1;
                for (int j = 0; j < get(airports.get(i)).minimumRoads.size(); j++) {
                    int cost = get(airports.get(i)).minimumRoads.get(i).cost;
                    if (cost < lowerCost && cost < get(airports.get(i)).airportCost) {
                        lowerCost = cost;
                        selectedRoad = j;
                    }
                }
                if (selectedRoad != -1) {
                    get(airports.get(i)).selectedRoad = get(airports.get(i)).minimumRoads.get(selectedRoad);
                    airports.remove(i);
                    i--;
                }
            }
        }
    }

    public Node get(int node) {
        return nodes.get(node);
    }

    public Node getLowerNode() {
        if (lowerNode == -1) {
            setLowerNode();
        }
        return nodes.get(lowerNode);
    }

    private void setLowerNode() {
        int lowerCost = 9999;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getTotalCost() < lowerCost) {
                lowerCost = nodes.get(i).getTotalCost();
                lowerNode = nodes.get(i).number;
            }
        }
        //for (Node node : nodes) {
        //    if (node.getTotalCost() < lowerCost) {
        //        lowerCost = node.getTotalCost();
        //        lowerNode = node.number;
        //    }
        //}
    }

    public void bellmanFord(int toNode) {
        int noChange = 0;
        get(toNode).minimumRoads.add(new Road(toNode, -1, 0));
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
        /*if (jump < jumpRestriction) {
            for (int i = 0; i < get(node).roadsCosts.size(); i++) {
                if (addMinimumRoad(i, toNode, node, cost + get(node).roadsCosts.get(i))) {
                    result = true;
                }
                if (searchingMinimumRoads(toNode, i, cost + get(node).roadsCosts.get(i), jump + 1, jumpRestriction)) {
                    result = true;
                }
            }
        }*/
        if (jump == jumpRestriction - 1) {
            for (int i = 0; i < get(node).roadsCosts.size(); i++) {
                if (get(node).roadsCosts.get(i) != -1) {
                    if (addMinimumRoad(i, toNode, node, cost + get(node).roadsCosts.get(i))) {
                        result = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < get(node).roadsCosts.size(); i++) {
                if (get(node).roadsCosts.get(i) != -1) {
                    if (searchingMinimumRoads(toNode, i, cost + get(node).roadsCosts.get(i), jump + 1, jumpRestriction)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public boolean addMinimumRoad(int node, int lastNode, int minimumRoad, int cost) {
        boolean result = false;
        if (get(node).number != get(lastNode).number) {
            int roadNumber = -1;
            for (int i = 0; i < get(node).minimumRoads.size(); i++) {
                if (get(node).minimumRoads.get(i).lastNode == get(lastNode).number) {
                    roadNumber = i;
                    break;
                }
            }
            if (roadNumber != -1) {
                if (get(node).minimumRoads.get(roadNumber).cost > cost) {
                    get(node).minimumRoads.get(roadNumber).minimumRoad = minimumRoad;
                    get(node).minimumRoads.get(roadNumber).cost = cost;
                    result = true;
                }
            } else {
                get(node).minimumRoads.add(new Road(lastNode, minimumRoad, cost));
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
