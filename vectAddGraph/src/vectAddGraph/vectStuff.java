package vectAddGraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

//this class contains general methods for drawing vectors and points
public class vectStuff {

	// this method computes the angles
	// the input is the k values
	// the output is an array anf angle values
	public static double[] getAngles(double[] kVals) {
		double[] angles = new double[kVals.length];
		for (int i = 0; i < kVals.length; i++) {
			angles[i] = Math.acos(Math.abs(kVals[i]));
			// flipping angles will be done later
		}
		return angles;
	}

	// keep it so older implementations of getCoords still works
	public static double[][] getCoords(double[] kVals, double[] angles, double[] xVals) {
		return getCoords(kVals, angles, xVals, 0, 0);
	}

	// calculates the coordinates of the vectors
	// the input includes the k values, the angles, the x values, and the starting
	// coordinates (usually (0,0))
	public static double[][] getCoords(double[] kVals, double[] angles, double[] xVals, double startX, double startY) {
		// init coord array (+1 for starting point)
		double[][] coords = new double[xVals.length + 1][2];

		// start at origin
		double currentX = startX;
		double currentY = startY;
		coords[0][0] = currentX;
		coords[0][1] = currentY;

		for (int i = 0; i < xVals.length; i++) {
			// flip if k is negative
			double angle = kVals[i] >= 0 ? angles[i] : Math.PI - angles[i];

			// get destination x and y
			// kvals now affect length
			double deltaX = (xVals[i] * Math.cos(angle) * kVals[i]);
			double deltaY = (xVals[i] * Math.sin(angle) * kVals[i]);

			// update current position
			currentX += deltaX;
			currentY += deltaY;

			// put into array
			coords[i + 1][0] = currentX;
			coords[i + 1][1] = currentY;
		}
		return coords;
	}

	// draws multiple connected vectors to the screen
	// the input is the gl object and the coordinates of each point
	public static void drawVectors(GL2 gl, double[][] coords) {

		for (int i = 0; i < coords.length - 1; i++) {
			drawVect(gl, coords[i][0], coords[i][1], coords[i + 1][0], coords[i + 1][1]);
		}
	}

	// draws a single vect
	// the input is the gl object, and the coordinates for the start and end points
	public static void drawVect(GL2 gl, double startX, double startY, double endX, double endY) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(startX, startY); // vector start
		gl.glVertex2d(endX, endY); // vector end
		gl.glEnd();
	}

	// draws a single point
	// the input is the gl object, the coordinates, and the size
	public static void drawPoint(GL2 gl, double x, double y, float size) {
		gl.glPointSize(size);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2d(x, y);
		gl.glEnd();
	}

	// compatibility for older doStuff
	public static ValHolder doStuff(GL2 gl, double[] xVals, double[] kVals) {
		return doStuff(gl, xVals, kVals, 0, 0);
	}

	// compatibility for older doStuff
	public static ValHolder doStuff(GL2 gl, double[] xVals, double[] kVals, double startX, double startY) {
		return doStuff(gl, xVals, kVals, startX, startY, 1f, 0f, 0f);// red
	}

	// this function does all the x vect stuff in one place
	// input includes the gl object, the values for angles/distnace, ht startiing
	// point (usually (0,0)), and the RGB color values
	// returns an object containing multiple things
	public static ValHolder doStuff(GL2 gl, double[] xVals, double[] kVals, double startX, double startY, float red,
			float green, float blue) {
		double[] angles = getAngles(kVals);
		double[][] coords = getCoords(kVals, angles, xVals, startX, startY);
		gl.glColor3f(red, green, blue);
		drawVectors(gl, coords);

		// output values
		for (int i = 0; i < xVals.length; i++) {
			double[] mid = getMidPoint(coords[i], coords[i + 1]);
			TextRenderer.draw(gl, String.format("%.2f", xVals[i]), mid[0], mid[1]);
		}

		// draw other stuff
		gl.glColor3f(0.5f, 0.5f, 0.5f); // grey

		// Enable line stipple
		gl.glEnable(GL2.GL_LINE_STIPPLE);
		// Set the stipple pattern
		gl.glLineStipple(1, (short) 0x00FF); // Dashed line: 0x00FF means 0000000011111111

		// combined vect
		drawVect(gl, startX, startY, coords[coords.length - 1][0], coords[coords.length - 1][1]);

		// line to make it easier to see where it is on the x axis
		drawVect(gl, coords[coords.length - 1][0], 0, coords[coords.length - 1][0], coords[coords.length - 1][1]);

		gl.glDisable(GL2.GL_LINE_STIPPLE);

		// points
		gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow

		// where it falls on the x axis
		drawPoint(gl, coords[coords.length - 1][0], 0, 7.0f);

		// each individual point along the vectors, including origin
		for (double[] coord : coords) {
			drawPoint(gl, coord[0], coord[1], 7.0f);
		}

		// sets the valholder stuff so the Y vector can be drawn
		ValHolder stuff = new ValHolder();
		stuff.setAngles(angles);
		stuff.setKVals(kVals);
		stuff.setStartX(startX);
		stuff.setStartY(startY);
		stuff.setXCoords(coords);
		return stuff;
	}

	// gets midpoint between two points
	// each point is a double containing x and y coordinates
	// the output is the same format as the input
	public static double[] getMidPoint(double[] p1, double[] p2) {
		double x = (p1[0] + p2[0]) / 2;
		double y = (p1[1] + p2[1]) / 2;
		return new double[] { x, y };
	}

	// calculates the distance between two points
	// each point is a double containing x and y coordinates
	// the output is the length between the two
	public static double calculateDistance(double[] P0, double[] P1) {
		double dx = P1[0] - P0[0];
		double dy = P1[1] - P0[1];
		return Math.sqrt(dx * dx + dy * dy);
	}
}
