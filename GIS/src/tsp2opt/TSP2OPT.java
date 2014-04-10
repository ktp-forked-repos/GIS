package tsp2opt;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TSP2OPT {

	//nieskonczonosc
	public static int INF = 999999999;

	// strumien wejsciowy
	Scanner inputStream;
	
	// liczba wierzcholkow
	int nodesNumber;

	// maciesz sasiedztwa
	int[][] weights;

	// minimalna dlugosc cyklu
	int minCycle = 999999999;
	
	//aktualnie analizowany cykle
	int[] currentCycle;
	
	//najlepszy dotychczas cykl
	int[] bestCycle;
	
	//aktualna dlugosc cyklu
	int currentDistance = 0;
	
	// poprzednia dlugosc cykly
	int oldDistance = 0;
	
	//ilosc iteracji
	int P = 0;
	
	// dla wartosci true program wypisze poszczegolne etapy dzialanie
	// nie usuwalismy tego na wypadek gdyvy chcial Pan sie blizej przyjrzec dzialaniu algorytmy
	public final boolean DEBUGMODE = false; 

	public static void main(String[] argv) {
		
		// domyslna ilosc iteracji jesli nie zostala podana jako argument =5
		int a = 5;
		
		if (argv.length > 0)
			a = Integer.parseInt(argv[0]);
		new TSP2OPT(a);
	}

	TSP2OPT(int a) {
		P = a;
		inputStream = new Scanner(System.in);
		if (DEBUGMODE)
			System.out.println("\n\nilosc szukan: " + P);
		this.nodesNumber = inputStream.nextInt();

		// wczytanie struktury grafu
		readGraph();

		oldDistance = INF;

		long timeStart = System.currentTimeMillis();
		for (int p = 0; p < P; p++) {
			initCycle();
			oldDistance = INF;
			currentDistance = INF - 1;
			
			// wykonujemy dopoki nie zostalo uzyskane polepszenie wyniku
			while (oldDistance > currentDistance) {
				oldDistance = currentDistance;
				if (DEBUGMODE)
					 System.out.println("\n\nNowe szukanie: old:"+oldDistance+" cur:"+currentDistance);
				
				//poszukiwanie lepszego cyklu
					searchBetter();

			}
			
			// jesli dlugosc jest lepsza podmieniamy sekwencje zapamietanych wierzcholkow
			if (currentDistance < minCycle) {
				minCycle = currentDistance;
				bestCycle = currentCycle;
			}
		}
		long timeEnd = System.currentTimeMillis();
		long time = timeEnd - timeStart;
		System.out.print("\ncycle: ");
		for (int p = 0; p < nodesNumber; p++){
			System.out.print(bestCycle[p] + " ");
		}
		System.out.print("\ndistance: " +minCycle);
		Double t = new Double((time*1.0)/1000);
		System.out.print("\ntime: " + t);

		inputStream.close();
	}


// wczytanie krawedzi grafu
	void readGraph() {
		weights = new int[nodesNumber][nodesNumber];
		for (int i = 0; i < nodesNumber; i++) {
			for (int j = 0; j < nodesNumber; j++) {
				weights[j][i] = inputStream.nextInt();
				weights[i][j] = weights[j][i];
			}
		}
	}

	// wylosowanie pierwszego rozwiazania od ktorego wyjdzie algorytm
	void initCycle() {
		currentCycle = new int[nodesNumber + 1];
		Random generator = new Random();
		ArrayList<Integer> edges = new ArrayList<Integer>();
		currentDistance = 0;
		for (int i = 0; i < nodesNumber; i++) {
			edges.add(i);
		}

		for (int i = 0; i < nodesNumber; i++) {
			int r = generator.nextInt(edges.size());

			currentCycle[i] = edges.get(r);
			edges.remove(r);
		}

		currentCycle[nodesNumber] = currentCycle[0];

	}

	// wyszukanie lepszego rozwiazania
	void searchBetter() {
		currentDistance = 0;
		currentDistance = 0;
		for (int i = 0; i < nodesNumber; i++) {
			currentDistance = currentDistance
					+ weights[currentCycle[i]][currentCycle[i + 1]];
		}
		for (int i = 0; i < nodesNumber; i++) {
			currentDistance = currentDistance
					+ weights[currentCycle[i]][currentCycle[i + 1]];
		}

		// flaga: czy znaleziono lepsze rozwiazanie
		boolean finded = false;
		
		if (DEBUGMODE)
			System.out.print("\nPierwszy cykl: " + currentDistance + " \t: ");
		if (DEBUGMODE)
			for (int n : currentCycle) {
				System.out.print(" " + n);
			}
		if (DEBUGMODE)
			System.out.print("  odlegosci:");
		if (DEBUGMODE)
			for (int n = 0; n < nodesNumber; n++) {
				System.out.print(" "
						+ weights[currentCycle[n]][currentCycle[n + 1]]);
			}

		// dla kazdej pary (i,j) krawedzi
		for (int i = 2; i < nodesNumber; i++) {
			int e1b = currentCycle[i];
			int e1e = currentCycle[i + 1];
			int old1 = weights[e1b][e1e];
			for (int j = 0; j + 1 < i && !finded; j++) {
				int e2b = currentCycle[j];
				int e2e = currentCycle[j + 1];
				int old2 = weights[e2b][e2e];
				int new1 = weights[e1b][e2b];
				int new2 = weights[e2e][e1e];
				
				// sprawdzenie czy po zamianie wynik bedzie lepszy
				if (old1 + old2 > new1 + new2) {
					finded = true;

					int swap = currentCycle[j + 1];
					currentCycle[j + 1] = currentCycle[i];
					currentCycle[i] = swap;

					currentDistance = 0;
					for (int ii = 0; ii < nodesNumber; ii++) {
						currentDistance = currentDistance
								+ weights[currentCycle[ii]][currentCycle[ii + 1]];
					}

					if (i == nodesNumber)
						currentCycle[0] = currentCycle[nodesNumber + 1];
					if (DEBUGMODE)
						System.out.print("\n aktualny cykl: " + currentDistance
								+ " \t: ");
					if (DEBUGMODE)
						for (int n : currentCycle) {
							System.out.print(" " + n);
						}
					if (DEBUGMODE) {
						System.out.print("  odlegosci:");
						for (int n = 0; n < nodesNumber; n++) {
							System.out
									.print(" "
											+ weights[currentCycle[n]][currentCycle[n + 1]]);
							int sum = 0;

						}
					}
					int sum = 0;
					if (DEBUGMODE)
						for (int n = 0; n < nodesNumber; n++) {
							sum = sum
									+ weights[currentCycle[n]][currentCycle[n + 1]];
						}
					if (DEBUGMODE)
						System.out.print(" >> " + sum);
				}
			}

		}

	}

}