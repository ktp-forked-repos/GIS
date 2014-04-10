package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Generator {

	public static void main(String[] argv) throws IOException {
		new Generator();
	}

	Generator() throws IOException {

		Scanner io = new Scanner(System.in);
		System.out.print("Podaj liczbe wierzcholkow: ");
		int n = io.nextInt();
		String path =  new java.io.File( "." ).getCanonicalPath();
		String name = path+"/tests/in" + Integer.toString(n);

		try {
			FileWriter outFile = new FileWriter(name);
			PrintWriter out = new PrintWriter(outFile);

			out.println(Integer.toString(n));
			Random random = new Random();
			int tab[][] = new int[n][n];
			for (int i = 0; i < n; i++) {
				tab[i][i] = 0;
				for (int j = 0; j < i; j++) {
					tab[i][j] = tab[j][i] = random.nextInt(n * 10);

				}
			}

			for (int i = 0; i < n; i++) {
				String line = "";
				for (int j = 0; j < n; j++) {
					line = line + Integer.toString(tab[i][j]) + " ";

				}
				out.println(line);
			}

			out.close();
			io.close();
			System.out.print("Wygenerowano plik: "+name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}