package vectAddGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//this class reads the csv file and processes the data
public class ReadCancer {
	// a list of all benign points
	private List<double[]> benign;
	// a list of all malignant points
	private List<double[]> malignant;
	// a list containing all points, regardless of class
	private List<double[]> bigList;
	// the "average" benign point
	private double[] bAvrg;
	// the "average" malignant point
	private double[] mAvrg;

	// reads and inits the data
	// automatically calls the read function
	public ReadCancer() {
		read();
	}

	// this method lets you change the order of the dimensions to whaterver you want
	// it currently assumes that there are 10, including the duplicate
	// 1 indexed, so the first dimension is reffered to as "x1", the second as "x2"
	// and so on
	// this returns a list containing all the points, regardless of class, with the
	// dimensions re-ordered to whatever was specified
	public List<double[]> scrambleBigList(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int x5,
			int y5) {
		List<double[]> nlist = new ArrayList<>();
		double[] np;
		for (double[] p : bigList) {
			np = new double[] { p[x1 - 1], p[y1 - 1], p[x2 - 1], p[y2 - 1], p[x3 - 1], p[y3 - 1], p[x4 - 1], p[y4 - 1],
					p[x5 - 1], p[y5 - 1] };
			nlist.add(np);
		}
		return nlist;
	}

	// this method lets you change the order of the dimensions to whaterver you want
	// it currently assumes that there are 10, including the duplicate
	// 1 indexed, so the first dimension is reffered to as "x1", the second as "x2"
	// and so on
	// this returns a list containing the benign points, with the dimensions
	// re-ordered to whatever was specified
	public List<double[]> scrambleBenign(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int x5,
			int y5) {
		List<double[]> nlist = new ArrayList<>();
		double[] np;
		for (double[] p : benign) {
			np = new double[] { p[x1 - 1], p[y1 - 1], p[x2 - 1], p[y2 - 1], p[x3 - 1], p[y3 - 1], p[x4 - 1], p[y4 - 1],
					p[x5 - 1], p[y5 - 1] };
			nlist.add(np);
		}
		return nlist;
	}

	// this method lets you change the order of the dimensions to whaterver you want
	// it currently assumes that there are 10, including the duplicate
	// 1 indexed, so the first dimension is reffered to as "x1", the second as "x2"
	// and so on
	// this returns a list containing the malignant points, with the dimensions
	// re-ordered to whatever was specified
	public List<double[]> scrambleMalignant(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int x5,
			int y5) {
		List<double[]> nlist = new ArrayList<>();
		double[] np;
		for (double[] p : malignant) {
			np = new double[] { p[x1 - 1], p[y1 - 1], p[x2 - 1], p[y2 - 1], p[x3 - 1], p[y3 - 1], p[x4 - 1], p[y4 - 1],
					p[x5 - 1], p[y5 - 1] };
			nlist.add(np);
		}
		return nlist;
	}

	// this is where the code is for reading the file itself
	// it is public so the user can manually re-read the file if desired
	public void read() {
		// later generalize and take filename as input?
		String filePath = "breast-cancer-wisconsin-9f.csv";
		benign = new ArrayList<>();
		malignant = new ArrayList<>();
		bigList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// Skip the first line which is just labels
			br.readLine();

			String line;
			while ((line = br.readLine()) != null) {
				// split by commas
				String[] values = line.split(",");
				int length = values.length;

				// check if odd length
				boolean isOdd = (length - 1) % 2 != 0;
				int arrayLength = isOdd ? length : length - 1;

				double[] numbers = new double[arrayLength];
				for (int i = 0; i < length - 1; i++) {
					numbers[i] = Double.parseDouble(values[i]);
				}

				// if length is odd, duplicate last value
				if (isOdd) {
					numbers[arrayLength - 1] = Double.parseDouble(values[length - 2]);
				}
				String classLabel = values[length - 1];

				// orgaize based on class
				if (classLabel.equals("Benign")) {
					benign.add(numbers);
				} else if (classLabel.equals("Malignant")) {
					malignant.add(numbers);
				}
				bigList.add(numbers);
			}

			bAvrg = computeAverages(benign);
			mAvrg = computeAverages(malignant);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// computes the average vals of a particular class
	// this uses kahan averaging
	// the input is a list of points
	// the output is a point representing the average
	public static double[] computeAverages(List<double[]> values) {
		// failsafe if given empty input
		if (values.isEmpty()) {
			return new double[0];
		}

		int length = values.get(0).length;
		double[] sums = new double[length];
		double[] compensations = new double[length]; // Compensations for Kahan summation

		for (double[] arr : values) {
			for (int i = 0; i < length; i++) {
				double y = arr[i] - compensations[i];
				double t = sums[i] + y;
				compensations[i] = (t - sums[i]) - y;
				sums[i] = t;
			}
		}

		double[] averages = new double[length];
		for (int i = 0; i < length; i++) {
			averages[i] = sums[i] / values.size();
		}

		return averages;
	}

	// prints out the specified list
	// can be helpful for debugging
	public static void printList(List<double[]> list) {
		for (double[] arr : list) {
			for (double num : arr) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}

	// getters

	// returns a list containing the benign points
	public List<double[]> getBenign() {
		return new ArrayList<>(benign);
	}

	// returns a list containing the malignant points
	public List<double[]> getMalignant() {
		return new ArrayList<>(malignant);
	}

	// returns a list containing every point
	public List<double[]> getBigList() {
		return new ArrayList<>(bigList);
	}

	// returns the "average" of the benign class
	public double[] getBAvrg() {
		return bAvrg.clone();
	}

	// returns the "average" of the malignant class
	public double[] getMAvrg() {
		return mAvrg.clone();
	}

	// returns the "average" point in the specified list
	public static double[] getAvrg(List<double[]> list) {
		return computeAverages(list).clone();
	}

	// returns the first point in the specified list
	// usually, there isn't any special significace of the first point
	// probably used for debug purposes
	public static double[] get1st(List<double[]> list) {
		return list.get(0).clone();
	}
}
