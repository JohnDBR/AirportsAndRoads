/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportsandroads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author djbarbosa
 */
public class main {

    public static void main(String[] args) {
        int cities = 0;
        int[][] costs;

        boolean problem;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //Storing and preparing data
        do {
            problem = false;
            System.out.print("ciudades: ");
            try {
                cities = Integer.valueOf(br.readLine());
            } catch (Exception ex) {
                problem = true;
            }
        } while (problem);

        costs = new int[cities][cities];
        for (int[] cost : costs) {
            Arrays.fill(cost, 0);
        }

        do {
            problem = false;
            try {
                System.out.println("\nNada mas la diagonal inferior");
                System.out.print("Formato:   ");
                for (int i = 0; i < cities; i++) {
                    System.out.print(i + ",");
                }
                System.out.println("");
                for (int i = 0; i < cities; i++) {
                    System.out.print("Vertice " + i + ": ");
                    String row = br.readLine();
                    String[] fields = row.split("\\,");
                    for (int j = 0; j < i + 1; j++) {
                        int cost = Integer.valueOf(fields[j]);
                        costs[i][j] = costs[j][i] = cost;

                    }
                }
            } catch (Exception e) {
                problem = true;
                //e.printStackTrace();
            }
        } while (problem);

        System.out.println("\nAsi quedo la matriz de adyacencia");
        for (int[] vertex : costs) {
            for (int cost : vertex) {
                System.out.print(cost + ",");
            }
            System.out.println("");
        }

        Graph completeGraph = new Graph(cities, cities * (cities - 1) / 2);
        for (int i = 0; i < cities; i++) {
            completeGraph.addNode(i);
            for (int j = 0; j < cities; j++) {
                if (i == j) {
                    completeGraph.get(i).setAirportCost(costs[i][j]);
                    completeGraph.get(i).addRoadCost(-1);
                } else {
                    completeGraph.get(i).addRoadCost(costs[i][j]);
                }
            }
        }

        //Procesing
        //System.out.println(completeGraph.getLowerNode().number);
        //completeGraph.bellmanFord(0);
        completeGraph.minimumNetworkCost();
        System.out.println("\n" + completeGraph.getMinimumNetworkCost()
        );
        String s = "";
    }

}
