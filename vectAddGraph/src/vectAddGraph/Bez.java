package vectAddGraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

//this class handles drawing the bezier curves
public class Bez {

	// calcs an individual point along the curve
	// should be called in a loop to get every point
	// t represents the steps so far as a percentage
	private static double[] calcQBezP(double t, double[] start, double[] control, double[] end) {
		double u = 1 - t;
		double tt = t * t;
		double uu = u * u;

		double x = uu * start[0] + 2 * u * t * control[0] + tt * end[0];
		double y = uu * start[1] + 2 * u * t * control[1] + tt * end[1];

		return new double[] { x, y };
	}

	// this method calculates the control point the drawing algorithm uses for
	// reference (to determine the details of the curve)
	// currently, it is along the midpoint, perpendicular to the line, and half the
	// line length
	public static double[] calcControlPoint(double[] start, double[] end) {
		double dist = vectStuff.calculateDistance(start, end) / 2;
		double[] mid = vectStuff.getMidPoint(start, end);
		// is vertical
		if (start[0] == end[0]) {
			return new double[] { (mid[0] - dist), mid[1] };
		}
		// horizontal
		else if (start[1] == end[1]) {
			return new double[] { mid[0], (mid[1] + dist) };
		}
		// normal
		else {
			double pslope = (end[0] - start[0]) / (end[1] - start[1]);
			double length = Math.sqrt(1 + pslope * pslope);
			double[] direction = { 1 / length, pslope / length };
			double[] offset = { direction[0] * dist, direction[1] * dist };
			double[] p1 = new double[] { mid[0] - offset[0], mid[1] + offset[1] };
			return p1;
		}
	}

	// draws the curve
	public static void draw(GL2 gl, double[] start, double[] control, double[] end) {
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i <= 100; i++) {
			double t = i / 100.0;
			double[] point = calcQBezP(t, start, control, end);
			gl.glVertex2d(point[0], point[1]);
		}
		gl.glEnd();
	}
}
