package vectAddGraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.jogamp.opengl.GL2;

// this class is meant to handle stuff for the linear programming program (DrawSC)
// some methods may be a better fit for other classes
public class CDraw {

	// this method does the bulk of the drawing work
	// the (currently) harcoded tableau represents all the constraints, and what the
	// simplex method performs on
	// yCoords is for the Y vector (the yellow one)
	// returns a ValHolder, to return multiple values
	public static ValHolder render(GL2 gl, double[][] tableau, double[][] yCoords) {
		// get tableau, hardcode for now
		/*
		 * double[][] tableau = {
		 * 	{1, 1, 1, 0, 4},
		 * 	{1, 3, 0, 1, 6},
		 * 	{-3, -5, 0, 0, 0}
		 * };
		 */

		/*
		 * double[][] tableau = {
		 * 	{2,3,1,1,0,0,6},
		 * 	{3,2,2,0,1,0,5},
		 * 	{1,4,3,0,0,1,4},
		 * 	{-7,-12,-5,0,0,0,0}
		 * };
		 */
		
		/*
		double[][] tableau = {
				{ 1, 1, 0, 1, 0, 0, 4 },
				{ 1, 3, 0, 0, 1, 0, 6 },
				{ 0, 1, 1, 0, 0, 1, 5 },
				{ -3, -5, -2, 0, 0, 0, 0 }
		};
		*/
		
		int pLength = getPLength(tableau);
		// do simplex
		double[] result = Simplex.executeSimplex(tableau.clone());
		// trim the results
		double[] p = trim(result,pLength);

		double[] original = getOriginalCoefs(tableau, p.length);
		double[] kvals = normalize(original);
		ValHolder stuff = vectStuff.doStuff(gl, p, kvals);
		
		//read from file
		if (yCoords == null) {
			try (FileInputStream fileIn = new FileInputStream("yVect.ser");
					ObjectInputStream in = new ObjectInputStream(fileIn)) {
				yCoords = (double[][]) in.readObject();
				System.out.println("Y Vector has been loaded from file");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//draw the Y vector
		drawMB2Click(gl, stuff.getXCoords(), yCoords);

		double total = 0;
		for (int i = 0; i < p.length; i++) {
			total += (p[i] * original[i]);
		}
		
		//print x total
		gl.glColor3f(1,0,0);
		double xPrintLoc = stuff.getXCoords()[stuff.getXCoords().length - 1][0];
		TextRenderer.draw(gl, String.format("%.2f", total), xPrintLoc, 0);
		
		//y vector stuff
		double yTotal = 0;
		gl.glColor3f(1,1,0);
		for (int i = 1; i < yCoords.length; i++) {
			vectStuff.drawPoint(gl, yCoords[i][0], yCoords[i][1], 7);
			double myVal = vectStuff.calculateDistance(stuff.getXCoords()[i - 1], yCoords[i]);
			double unVal =  unnorm(myVal, original, i - 1);
			TextRenderer.draw(gl, String.format("%.2f", unVal), yCoords[i][0], yCoords[i][1]);
			yTotal += (unVal * original[i-1]);
		}
		
		//test
		TextRenderer.draw(gl, String.format("%.2f", yTotal), 0, 0);
		
		stuff.setYCoords(yCoords);
		return stuff;
	}
	
	//draws the constraints
	//this version is incorrect, see drawC2
	public static void drawConstraints (GL2 gl, double[][] tableau, double right) {
		int pLength = getPLength(tableau);
		double[] original = getOriginalCoefs(tableau, (tableau[0].length/2));
		double[] kVals = normalize(original);
		//find the max constant
		double maxC = 0;
		for (int i = 0; i < tableau.length-1; i++) {
			if (tableau[i][tableau[i].length-1] > maxC) {
				maxC = tableau[i][tableau[i].length-1];
			}
		}
		//iterate through the constraints
		for (int i = 0; i < tableau.length -1 ; i++) {
			double[] constr = tableau[i].clone();
			double constant = constr[constr.length-1];
			//trim
			double[] cTrim = trim(constr,pLength);
			//hardcode target for now
			int target = 1;
			double kTarget = kVals[target];
			double cTarget = cTrim[target];
			double[] kNoT = removeTarget(kVals, target);
			double[] cNoT = removeTarget(cTrim, target);
			
			//negate vars and divide by target
			double[] normed = cNoT.clone();
			double[] kNeg = kNoT.clone();
			for (int j = 0; j < cNoT.length; j++) {
				kNeg[j] *= -1;
				normed[j] *= -1;
				normed[j] = normed[j] / cTarget;
			}
			//draw
			vectStuff.doStuff(gl, normed, kNeg, ((constant / cTarget)/maxC)*right, 0, 0,1,0);
		}
	}
	
	//draws the constraints
	//the inputs are the gl object and the tableau
	public static void drawC2(GL2 gl, double[][] tableau) {
		//hardcode the target for now
		int target = 1;
		
		int pLength = getPLength(tableau);
		// do simplex
		double[] result = Simplex.executeSimplex(tableau.clone());
		// trim the results
		double[] p = trim(result,pLength);
		//get xVals
		double[] xVals = removeTarget(p,target);
		double xTarget = p[target];
		
		//iterate through the constraints
		for (int i = 0; i < tableau.length - 1 ; i++) {
			double[] trimmed = trim(tableau[i], pLength);
			double kTarget = trimmed[target];
			double[] kDiv = trimmed.clone();
			for (int j = 0; j < kDiv.length; j++) {
				kDiv[j] = kDiv[j] / kTarget;
			}
			double[] kVals = normalize(kDiv);
			double[] kTrim = removeTarget(kVals, target);
			double[] negated = kTrim.clone();
			double[] xMod = xVals.clone();
			for (int j = 0; j < negated.length; j++) {
				System.out.println(negated.length);
				System.out.println(xMod.length);
				negated[j] *= -1;
				//negated[j] = negated[j] / kTarget;
				xMod[j] *= -1;
			}
			//calculate the length
			double[] angles = vectStuff.getAngles(negated);
			double[][] coords = vectStuff.getCoords(negated, angles, xMod, 0,0);
			double length = coords[coords.length-1][0];
			
			vectStuff.doStuff(gl, xMod, negated, (xTarget-length),0, 0,1,0);
		}
	}
	
	//trims the contstraint
	public static double[] trim (double[] arr, int pLength) {
		double[] foo = new double[pLength];
		for (int i = 0; i < foo.length; i++) {
			foo[i] = arr[i];
		}
		return foo;
	}
	
	//removes the target
	public static double[] removeTarget (double[] r, int target) {
		double[] foo = new double[r.length-1];
		for (int i = 0; i < r.length; i++) {
			if (i < target) {
				foo[i] = r[i];
			}
			else if (i > target) {
				foo[i-1] = r[i];
			}
		}
		return foo;
	}

	// undos normalization of a particular value
	// value is the value to unnorm
	// coefs is the array of coefficients, and index determines which one
	// corresponds to the value
	public static double unnorm(double value, double[] coefs, int index) {
		// find the largest
		double max = 0;
		for (double coef : coefs) {
			if (coef > max) {
				max = coef;
			}
		}
		return value * (max / coefs[index]);
	}

	// normalizes the input to a range of 0 to 1
	// current version assumes positive numbers
	public static double[] normalize(double[] d) {
		// find the largest
		double max = 0;
		for (double element : d) {
			if (element > max) {
				max = element;
			}
		}
		// normalize
		double[] kvals = new double[d.length];
		for (int i = 0; i < d.length; i++) {
			kvals[i] = d[i] / max;
		}

		return kvals;
	}

	// using the original tableau as input, gets the coefficients of the original
	// equation
	// size is how many coefficients there are
	private static double[] getOriginalCoefs(double[][] tab, int size) {
		double[] eq = new double[size];
		for (int i = 0; i < size; i++) {
			eq[i] = -(tab[tab.length - 1][i]);
		}

		return eq;
	}

	// takes the output of the simplex and removes the filler stuff, leaving only
	// the data relevant to the coordinates of the optimal point
	public static double[] getPoint(double[] result) {
		int size = (result.length - 1) / 2;
		double[] p = new double[size];
		for (int i = 0; i < size; i++) {
			p[i] = result[i];
		}

		return p;
	}
	
	public static int getPLength(double[][] tableau) {
		int l = 0;
		for (int i = 0; i < tableau[tableau.length - 1].length; i++) {
			if (tableau[tableau.length - 1][i] == 0) {
				return l;
			}
			else {
				l++;
			}
		}
		return l;
	}

	// similar to how the x vector is drawn, but with bezier curves instead of lines
	// currently not used for anything, but may have a use later
	public static void drawMultiBez(GL2 gl, double[][] coords) {
		// take coords and do stuff
		for (int i = 0; i < coords.length - 1; i++) {
			double[] midpoint = vectStuff.getMidPoint(coords[i], coords[i + 1]);
			double[] control = Bez.calcControlPoint(coords[i], coords[i + 1]);
			Bez.draw(gl, coords[i], control, coords[i + 1]);
			vectStuff.drawVect(gl, midpoint[0], midpoint[1], control[0], control[1]);
		}
	}

	// draws the y vector to the screen
	// the inputs are the gl object, and the coordinates of the x and y vectors
	public static void drawMB2Click(GL2 gl, double[][] xcoords, double[][] ycoords) {
		// draw first curve like normal
		double[] control = Bez.calcControlPoint(ycoords[0], ycoords[1]);
		Bez.draw(gl, ycoords[0], control, ycoords[1]);
		//vectStuff.drawVect(gl, midpoint[0], midpoint[1], control[0], control[1]);
		// now for the looop
		for (int i = 2; i < ycoords.length; i++) {
			double xdiff = 0;
			double ydiff = 0;
			double[] adjusted = new double[] { ycoords[i][0] + xdiff, ycoords[i][1] + ydiff };
			control = Bez.calcControlPoint(xcoords[i - 1], adjusted);
			Bez.draw(gl, xcoords[i - 1], control, adjusted);
		}
	}

	// calculates the new coordinates of the y vector based on the location of the click
	// the inputs are the coordinates of the x and y vectors, and the coodinates of
	// the mouse click (in world coordinates)
	// the output is the new coordinates of the y vector
	public static double[][] calcNewYCoordsBasedOnClick(double[][] xCoords, double[][] yCoords, double mouseX,
			double mouseY) {
		// first find the xcoord that is to the direct left of the click
		// this is to know which line it is part of
		int xToLeft = -1;
		for (int i = 0; i < xCoords.length - 1; i++) {
			if (mouseX > xCoords[i][0]) {
				xToLeft = i;
			}
			System.out.println(SPCHelper.NDString(xCoords[i]));
		}
		// if not found, just return unchanged y coords
		if (xToLeft == -1) {
			return yCoords;
		}

		double[][] newYCoords = yCoords.clone();

		// find slope m
		double m = (xCoords[xToLeft + 1][1] - xCoords[xToLeft][1]) / (xCoords[xToLeft + 1][0] - xCoords[xToLeft][0]);
		// find b
		double b = xCoords[xToLeft][1] - (m * xCoords[xToLeft][0]);
		double newYPos = (m * mouseX) + b;

		newYCoords[xToLeft + 1][0] = mouseX;
		newYCoords[xToLeft + 1][1] = newYPos;

		return newYCoords;
	}

	// this method converts pixel coordinates to world coordinates
	public static double[] screenToWorld2D(int mouseX, int mouseY, int windowWidth, int windowHeight, double left,
			double right, double bottom, double top) {
		double[] worldCoords = new double[2];
		// x
		worldCoords[0] = left + (right - left) * ((double) mouseX / windowWidth);
		// y
		worldCoords[1] = bottom + (top - bottom) * ((double) (windowHeight - mouseY) / windowHeight);
		return worldCoords;
	}

}
