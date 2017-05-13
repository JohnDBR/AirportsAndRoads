/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportsandroads;

import java.util.LinkedList;

/**
 *
 * @author djbarbosa
 */
public class Graph {

    private int order;
    private int size;
    private int lowerNode = -1;
    private LinkedList<Node> nodes = new LinkedList<>();

    public Graph(int order, int size) {
        this.order = order;
        this.size = size;
    }

    public void addNode(int number) {
        nodes.add(new Node(number));
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
        int lowerCost = 999;
        for (Node node : nodes) {
            if (node.totalCost < lowerCost) {
                lowerCost = node.totalCost;
                lowerNode = node.number;
            }
        }

    }

    public void bellmanFord(int toNode) {
        int noChange = 0;
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
                if (addMinimumRoad(i, toNode, node, cost + get(node).roadsCosts.get(i))) {
                    result = true;
                }
            }
        } else {
            for (int i = 0; i < get(node).roadsCosts.size(); i++) {
                if (searchingMinimumRoads(toNode, i, cost + get(node).roadsCosts.get(i), jump + 1, jumpRestriction)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean addMinimumRoad(int node, int lastNode, int minimumRoad, int cost) {
        return get(node).addMinimumRoad(get(lastNode), get(minimumRoad), cost);
    }

    public class Node {

        int number;
        int totalCost = -1;
        int airportCost;

        LinkedList<Integer> roadsCosts = new LinkedList<>();

        LinkedList<Road> minimumRoads = new LinkedList<>();

        public Node(int number) {
            this.number = number;
            this.airportCost = airportCost;
        }

        public int getNumber() {
            return number;
        }

        public void setAirportCost(int airportCost) {
            this.airportCost = airportCost;
        }

        public int getAirportCost() {
            return airportCost;
        }

        public void addRoadCost(int cost) {
            roadsCosts.add(cost);
        }

        public int getRoadCost(int road) {
            return roadsCosts.get(road);
        }

        public int getTotalCost() {
            if (totalCost == -1) {
                setTotalCost();
            }
            return totalCost;
        }

        private void setTotalCost() {
            totalCost = totalCost + airportCost;
            for (Integer roadCost : roadsCosts) {
                totalCost = totalCost + roadCost;
            }
        }

        public boolean addMinimumRoad(Node lastNode, Node minimumRoad, int cost) {
            boolean result = false;
            if (number != lastNode.number) {
                int roadNumber = -1;
                for (int i = 0; i < minimumRoads.size(); i++) {
                    if (minimumRoads.get(i).lastNode.number == lastNode.number) {
                        roadNumber = i;
                        break;
                    }
                }
                if (roadNumber != -1) {
                    if (minimumRoads.get(roadNumber).cost > cost) {
                        minimumRoads.get(roadNumber).minimumRoad = minimumRoad;
                        minimumRoads.get(roadNumber).cost = cost;
                        result = true;
                    }
                } else {
                    minimumRoads.add(new Road(lastNode, minimumRoad, cost));
                    result = true;
                }
            }
            return result;
        }

    }

    public class Road {

        int cost;
        Node lastNode;
        Node minimumRoad;

        public Road(Node lastNode, Node minimumRoad, int cost) {
            this.lastNode = lastNode;
            this.minimumRoad = minimumRoad;
            this.cost = cost;
        }

    }

}
