package vectAddGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//this class/method simply reads a txt file and gets an array from it
public class ArrayFromTXT {
	public static double[] readDoubleArr(String doc) {
		try {
			File f = new File(doc);
			Scanner s = new Scanner(f);
			ArrayList<Double> arr = new ArrayList<>();

			while (s.hasNextDouble()) {
				arr.add(s.nextDouble());
			}
			s.close();
			// convert to array
			double[] dList = new double[arr.size()];
			for (int i = 0; i < dList.length; i++) {
				dList[i] = arr.get(i);
			}
			return dList;

		} catch (FileNotFoundException e) {
			System.out.println("Error: file not found");
			return null;
		}
	}

}
