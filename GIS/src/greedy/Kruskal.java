package greedy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Kruskal {
	// drzewo krawedzi
	TreeSet<Edge> edges;
	
	//liczba wierzcholkow
	int nodesNumber = 0;
	
	//macierz sasiedztwa
	int[][] weights;

	// strumien wejsciowy
	Scanner inputStream;

	//wczytanie grafu
	void readGraph() {
		weights = new int[nodesNumber][nodesNumber];
		for (int i = 0; i < nodesNumber; i++) {
			for (int j = 0; j < nodesNumber; j++) {
				int d = inputStream.nextInt();
				weights[j][i] = d;
				weights[i][j] = weights[j][i];
				if (i != j) {
					edges.add(new Edge(i, j, d));
					edges.add(new Edge(j, i, d));
				}
				weights[i][j] = weights[j][i];
			}
		}
	}

	public static void main(String[] argv) {
		new Kruskal();

	}

	public static final Boolean DEBUGMODE = true;

	public Kruskal() {

		edges = new TreeSet<Edge>();

		inputStream = new Scanner(System.in);

		this.nodesNumber = inputStream.nextInt();
		readGraph();

		long startTime = System.currentTimeMillis();
		KruskalTree tree = new KruskalTree();

		for (Edge edge : edges) {
			tree.insertEdge(edge);
		}

		boolean[] visited = new boolean[nodesNumber];
		List<Edge> cycle = new ArrayList();

		List<Edge> edges = new ArrayList<Edge>();

		for (Edge edge : tree.getEdges()) {
			edges.add(edge);
			//System.out.println(edge);
		}

		int i = 2;
		Edge activeEdge = edges.get(0);
		edges.remove(0);
		int startVertex = activeEdge.v1;
		int activeVertex = activeEdge.v2;
		cycle.add(activeEdge);
		visited[activeEdge.v1] = visited[activeEdge.v2] = true;
		boolean finded = false;
		
		// w pierwszej kolejnosci szukamy nowego wierzcholka wsrod krawedzi drzewa rozpinajacego. jesli go nie ma - bierzemy z listy nieodwiedzonych
		
		while (i < nodesNumber) {
			 finded = false;
			for (int j = 0; j < edges.size() && !finded; j++) {
				Edge e = edges.get(j);
				if ((e.v1 == activeVertex) && !visited[e.v2]) {
					visited[e.v2] = true;
					i++;
					cycle.add(e);
					activeVertex = e.v2;
					finded = true;
					edges.remove(j);
				}

				if ((e.v2 == activeVertex) && !visited[e.v1]) {
					visited[e.v1] = true;
					i++;
					cycle.add(e);
					activeVertex = e.v1;
					finded = true;
					edges.remove(j);
				}
			}
			if (!finded) {
			for (int l = 0; l < edges.size() && !finded; l++) {
				Edge e = edges.get(l);
				if ( !visited[e.v2]) {
					Edge outiSide = new Edge(activeVertex, e.v2,
							weights[activeVertex][e.v2]);
					visited[e.v2] = true;
					i++;
					activeVertex = e.v2;
					cycle.add(outiSide);
					l = edges.size();
					finded = true;	
				}
				else if ( !visited[e.v1]) {
					Edge outiSide = new Edge(activeVertex, e.v1,
							weights[activeVertex][e.v1]);
					visited[e.v1] = true;
					i++;
					activeVertex = e.v1;
					cycle.add(outiSide);
					l = edges.size();
					finded = true;	
				}

			}
		}
		}


		Edge outiSide = new Edge(activeVertex, startVertex,
				weights[activeVertex][startVertex]);

		cycle.add(outiSide);

		long endTime = System.currentTimeMillis();
		int sum = 0;
		int last=-1;
		visited = new boolean[nodesNumber];
		System.out.print("cycle: ");
		for (int k = 0; k < nodesNumber; k++) {
			
			int a=cycle.get(k).v1; 
			if(!visited[a]){
				System.out.print(a+ " ");
				visited[a]=true;
			}
			 a=cycle.get(k).v2; 
			if(!visited[a]){
				System.out.print(a+ " ");
				visited[a]=true;
			}
			sum = sum + cycle.get(k).weight;

		}
		System.out.println("\ndistance: " + sum);
		long time = endTime - startTime;
		Double t = new Double((time * 1.0) / 1000);

		System.out.print("time: " + t);

	}
}