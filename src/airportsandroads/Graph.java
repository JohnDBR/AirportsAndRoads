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

    public class Node {

        int number;
        int totalCost = -1;
        int airportCost;

        LinkedList<Integer> roadsCosts = new LinkedList<>();

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
    }

}
