package vectAddGraph;

//this class is meant to allow returning multiple values
//designed around use for the linear programming portion
public class ValHolder {
	private double[][] xCoords;
	private double[][] yCoords;
	private double[] kVals;
	private double[] angles;
	private double startX;
	private double startY;

	// this doesn't init any vars in order to allow the user to decide which ones to
	// use
	public ValHolder() {
	}

	public void setXCoords(double[][] x) {
		xCoords = x.clone();
	}

	public double[][] getXCoords() {
		return xCoords.clone();
	}

	public void setYCoords(double[][] y) {
		yCoords = y.clone();
	}

	public double[][] getYCoords() {
		return yCoords.clone();
	}

	public void setKVals(double[] k) {
		kVals = k.clone();
	}

	public double[] getKVals() {
		return kVals.clone();
	}

	public void setAngles(double[] a) {
		angles = a.clone();
	}

	public double[] getAngles() {
		return angles.clone();
	}

	public void setStartX(double x) {
		startX = x;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartY(double y) {
		startY = y;
	}

	public double getStartY() {
		return startY;
	}
}
