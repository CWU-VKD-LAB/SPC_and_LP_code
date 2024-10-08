package vectAddGraph;

import java.util.List;
import java.util.Random;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

//this class contains methods that aid with SPC
//not all of them might be in use, but they still have their uses
//here, a rectangle is defined  yb the x and y coordinates of the bottom left corner, the width, and the height
//a hypercube/square is defined by the x and y coordinates, as well as the distance from the center to one of the edges
public class SPCHelper {
	
	//this method draws all the axes and references required to be able to interpret the SPC graph
	//the input p represents the anchor
	public static void realSCP(GL2 gl, double[] p) {
		// draw the point
		gl.glColor3f(.5f, .5f, .5f); // yellow
		vectStuff.drawPoint(gl, 0, 0, 7f);

		// draw the axes
		float red = 0f;
		float blue = 0f;
		float green = 1f;
		gl.glDisable(GL2.GL_LINE_STIPPLE);
		for (int i = 0; i < p.length; i += 2) {
			gl.glColor3f(red, green, blue); // start green
			if (p[i] >= 0) {
				vectStuff.drawVect(gl, -p[i], -p[i + 1], 99, -p[i + 1]);
			} else {
				vectStuff.drawVect(gl, -p[i], -p[i + 1], -99, -p[i + 1]);
			}
			if (p[i + 1] >= 0) {
				vectStuff.drawVect(gl, -p[i], -p[i + 1], -p[i], 99);
			} else {
				vectStuff.drawVect(gl, -p[i], -p[i + 1], -p[i], -99);
			}
			red += 3.0f / p.length;
			blue += 2f / p.length;
			green -= 3f / p.length;
		}

		gl.glColor3f(0.5f, 0.5f, 0.5f); // grey
		// Enable line stipple
		gl.glEnable(GL2.GL_LINE_STIPPLE);
		// Set the stipple pattern
		gl.glLineStipple(1, (short) 0x00FF); // Dashed line: 0x00FF means 0000000011111111
		vectStuff.drawVect(gl, 0, 99, 0, -99);
		vectStuff.drawVect(gl, 99, 0, -99, 0);
		gl.glDisable(GL2.GL_LINE_STIPPLE);
	}
	
	//drawND, but with less parameters
	public static void drawND(GL2 gl, double[] anchor, double[] p, float red, float green, float blue) {
		drawND(gl, anchor, p, red, green, blue, 0);
	}

	// draws an n-D point
	// dimensions must be same as the anchor
	//the input is the gl object, the anchor, the ND point, the RGB color, and which dimension pair to highlight
	public static void drawND(GL2 gl, double[] anchor, double[] p, float red, float green, float blue, int highlight) {
		// calculate the differences
		double[] diff = new double[p.length];
		for (int i = 0; i < p.length; i++) {
			diff[i] = p[i] - anchor[i];
		}
		// draw point
		gl.glColor3f(red, green, blue);
		if (highlight < (diff.length / 2)) {
			vectStuff.drawPoint(gl, diff[highlight * 2], diff[(highlight * 2) + 1], 7f);
		}

		// draw the connecting lines
		for (int i = 0; i < diff.length - 2; i += 2) {
			vectStuff.drawVect(gl, diff[i], diff[i + 1], diff[i + 2], diff[i + 3]);
		}
	}
	
	//draws all of the nth dimensional pairs
	//the input includes the gl object, the anchor point, the list of nd points involced, the RGB color, and which pair to draw (zero indexed)
	public static void drawNthPairs(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue, int n) {
		double diffx;
		double diffy;
		gl.glColor3f(red, green, blue);
		for (double[] p : list) {
			diffx = p[n*2] - anchor[n*2];
			diffy = p[(n*2)+1] - anchor[(n*2)+1];
			vectStuff.drawPoint(gl, diffx, diffy, 6f);
		}
	}
	
	//deprecated, use drawNthPairs instead
	public static void draw1stPairs(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue) {
		double diffx;
		double diffy;
		gl.glColor3f(red, green, blue);
		for (double[] p : list) {
			diffx = p[0] - anchor[0];
			diffy = p[1] - anchor[1];
			vectStuff.drawPoint(gl, diffx, diffy, 6f);
		}
	}
	
	//depracated, use drawNthPairs instead
	public static void draw2ndPairs(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue) {
		double diffx;
		double diffy;
		gl.glColor3f(red, green, blue);
		for (double[] p : list) {
			diffx = p[2] - anchor[2];
			diffy = p[3] - anchor[3];
			vectStuff.drawPoint(gl, diffx, diffy, 6f);
		}
	}
	
	//deprecated, use drawNthPairs instead
	public static void draw3rdPairs(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue) {
		double diffx;
		double diffy;
		gl.glColor3f(red, green, blue);
		for (double[] p : list) {
			diffx = p[4] - anchor[4];
			diffy = p[5] - anchor[5];
			vectStuff.drawPoint(gl, diffx, diffy, 6f);
		}
	}
	
	//deprecated, use drawNthPairs instead
	public static void draw4thPairs(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue) {
		double diffx;
		double diffy;
		gl.glColor3f(red, green, blue);
		for (double[] p : list) {
			diffx = p[6] - anchor[6];
			diffy = p[7] - anchor[7];
			vectStuff.drawPoint(gl, diffx, diffy, 6f);
		}
	}
	
	//deprecated, use drawNthPairs instead
	public static void draw5thPairs(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue) {
		double diffx;
		double diffy;
		gl.glColor3f(red, green, blue);
		for (double[] p : list) {
			diffx = p[8] - anchor[8];
			diffy = p[9] - anchor[9];
			vectStuff.drawPoint(gl, diffx, diffy, 6f);
		}
	}

	// draws every n-D point int the list
	//the inputs are the gl object, the anchor, the list to draw, and the RGB color values
	public static void drawList(GL2 gl, double[] anchor, List<double[]> list, float red, float green, float blue) {
		for (double[] p : list) {
			drawND(gl, anchor, p, red, green, blue);
		}
	}
	
	//this method takes a list and draws the nd points that lie entirely within the specified rectangle
	//the inputs are the anchor, the list to draw,  and the specifications of the rectangle
	public static void drawInRect(GL2 gl, double[] anchor, List<double[]> list, double x, double y, double w,
			double h) {
		for (double[] p : list) {
			if (inHRect(anchor, p, x, y, w, h)) {
				drawND(gl, anchor, p, 1, 1, 1);
				printBasicRectRule(anchor, p, x, y, w, h);
			}
		}
	}

	// draws both the benign and the malignant nd points that are withing the group of rectangles
	//the inputs are the gl object, the anchor, the ReadCancer object, the lists of rectangle dimensions, and which nd point to highlight
	public static void drawBothInRects(GL2 gl, double[] anchor, ReadCancer data, double[] xs, double[] ys, double[] ws,
			double[] hs, int highlight) {
		int index = 0;
		double[] focusPoint = {};

		// start drawing benign
		List<double[]> list = data.getBenign();
		for (double[] p : list) {
			// check if in Rect, if so, draw
			if (inRectGroup(anchor, p, xs, ys, ws, hs)) {
				if (index == highlight) {
					// highlight in yellow
					drawND(gl, anchor, p, 1, 1, 0);
					focusPoint = p;
				} else {
					// draw normal color
					drawND(gl, anchor, p, .8f, .3f, 0);
				}
				index++;
			} // end if
		} // end for

		// now do malignant
		// dont reset index, so they can be highlighted
		list = data.getMalignant();
		for (double[] p : list) {
			// check if in rect, if so, draw
			if (inRectGroup(anchor, p, xs, ys, ws, hs)) {
				if (index == highlight) {
					// highlight in yellow
					drawND(gl, anchor, p, 1, 1, 0);
					focusPoint = p;
				} else {
					// draw normal color
					drawND(gl, anchor, p, 0, 0, 1);
				}
				index++;
			} // end if
		} // end for
			// draw the highlight on top for visibility
		if (highlight < index && highlight >= 0) {
			gl.glDisable(GL.GL_DEPTH_TEST); // Disable depth testing
			drawND(gl, anchor, focusPoint, 1, 1, 0);
			gl.glEnable(GL.GL_DEPTH_TEST); // Re-enable
		}
		// if you want to see how many there are
		System.out.println(index);
	}

	//this method draws the nd points that are not entirely within the rectangles specified
	//the inputs include the gl object, the anchor, the ReadCancer object, the specifications for the rectangles, which nd point to highlight, and the option
	// options:
	// 0- handle both classes
	// 1- benign class only
	// 2- malignant class only
	public static void drawOutOfRects(GL2 gl, double[] anchor, ReadCancer data, double[] xs, double[] ys, double[] ws,
			double[] hs, int highlight, int option) {
		int index = 0;
		double[] focusPoint = {};

		// start drawing benign
		List<double[]> list = data.getBenign();
		if (option != 2) {
			for (double[] p : list) {
				// check if in Rect, if so, draw
				if (!inRectGroup(anchor, p, xs, ys, ws, hs)) {
					if (index == highlight) {
						// highlight in yellow
						drawND(gl, anchor, p, 1, 1, 0);
						focusPoint = p;
					} else {
						// draw normal color
						drawND(gl, anchor, p, .8f, .3f, 0);
					}
					index++;
				} // end if
			} // end for
		}
		// now do malignant
		// dont reset index, so they can be highlighted
		list = data.getMalignant();
		if (option != 1) {
			for (double[] p : list) {
				// check if in rect, if so, draw
				if (!inRectGroup(anchor, p, xs, ys, ws, hs)) {
					if (index == highlight) {
						// highlight in yellow
						drawND(gl, anchor, p, 1, 1, 0);
						focusPoint = p;
					} else {
						// draw normal color
						drawND(gl, anchor, p, 0, 0, 1);
					}
					index++;
				} // end if
			} // end for
		}
		// draw the highlight on top for visibility
		if (highlight < index && highlight >= 0) {
			gl.glDisable(GL.GL_DEPTH_TEST); // Disable depth testing
			drawND(gl, anchor, focusPoint, 1, 1, 0);
			gl.glEnable(GL.GL_DEPTH_TEST); // Re-enable
		}
		// if you want to see how many there are
		System.out.println(index);
	}

	//this method draws the points that lie entirely within the gourp of hypercubes, while distinguishing between the two classes
	//the inputs include the gl object, the anchor, the ReadCancer object, and the specifications for the HCs
	public static void drawBothInHCs(GL2 gl, double[] anchor, ReadCancer data, double[] xs, double[] ys, double[] sizes,
			int highlight) {
		int index = 0;
		double[] focusPoint = {};

		// start drawing benign
		List<double[]> list = data.getBenign();
		for (double[] p : list) {
			// check if in HC, if so, draw
			if (inHCGroup(anchor, p, xs, ys, sizes)) {
				if (index == highlight) {
					// highlight in yellow
					drawND(gl, anchor, p, .7f, .7f, .1f);
					focusPoint = p;
				} else {
					// draw normal color
					drawND(gl, anchor, p, 0, 1, 0);
				}
				index++;
			} // end if
		} // end for

		// now do malignant
		// dont reset index, so they can be highlighted
		list = data.getMalignant();
		for (double[] p : list) {
			// check if in HC, if so, draw
			if (inHCGroup(anchor, p, xs, ys, sizes)) {
				if (index == highlight) {
					// highlight in yellow
					drawND(gl, anchor, p, .7f, .7f, .1f);
					focusPoint = p;
				} else {
					// draw normal color
					drawND(gl, anchor, p, .7f, 0, .1f);
				}
				index++;
			} // end if
		} // end for
			// draw the highlight on top for visibility
		if (highlight < index && highlight >= 0) {
			gl.glDisable(GL.GL_DEPTH_TEST); // Disable depth testing
			drawND(gl, anchor, focusPoint, 1, 1, 0);
			gl.glEnable(GL.GL_DEPTH_TEST); // Re-enable
		}
		// if you want to see how many there are
		System.out.println(index);
	}

	// draws the points that do not lie entirely within the hypercube
	//the inputs include the gl object, the anchor, the REadCancer object, the specifications of the squares, which point to highligh, and the desired option
	// options:
	// 0- include both classes
	// 1- only draw the benign class
	// 2- only draw the malignant class
	public static void drawNotInHC(GL2 gl, double[] anchor, ReadCancer data, double[] xs, double[] ys, double[] sizes,
			int highlight, int option) {
		int index = 0;
		double[] focusPoint = {};

		// start drawing benign
		List<double[]> list = data.getBenign();
		if (option != 2) {
			for (double[] p : list) {
				// check if in HC, if so, draw
				if (!inHCGroup(anchor, p, xs, ys, sizes)) {
					if (index == highlight) {
						// highlight in yellow
						drawND(gl, anchor, p, 1, 1, 0);
						focusPoint = p;
					} else {
						// draw normal color
						drawND(gl, anchor, p, .8f, .3f, 0);
					}
					index++;
				} // end if
			} // end for
		} // end if

		// now do malignant
		// dont reset index, so they can be highlighted
		if (option != 1) {
			list = data.getMalignant();
			for (double[] p : list) {
				// check if in HC, if so, draw
				if (!inHCGroup(anchor, p, xs, ys, sizes)) {
					if (index == highlight) {
						// highlight in yellow
						drawND(gl, anchor, p, 1, 1, 0);
						focusPoint = p;
					} else {
						// draw normal color
						drawND(gl, anchor, p, 0, 0, 1);
					}
					index++;
				} // end if
			} // end for
		} // end if

		// draw the highlight on top for visibility
		if (highlight < index && highlight >= 0) {
			gl.glDisable(GL.GL_DEPTH_TEST); // Disable depth testing
			drawND(gl, anchor, focusPoint, 1, 1, 0);
			gl.glEnable(GL.GL_DEPTH_TEST); // Re-enable
		}
		// if you want to see how many there are
		System.out.println(index);
	}

	//draws points within an HC at the center
	//the inputs are the gl object, the anchor, the list of points, the size of the square, and the RGB colors
	public static void drawWithinCenterHC(GL2 gl, double[] anchor, List<double[]> list, double size, float red,
			float green, float blue) {
		int total = 0;
		for (double[] p : list) {
			// check if in HC, if so, draw
			if (inCenterHC(anchor, p, size)) {
				drawND(gl, anchor, p, red, green, blue);
				total++;
			}
		}
		System.out.println(total);
	}
	
	//this method checks if a ND point lies within a group of hypercubes
	//the inputs are the anchor, the point to check, and the specifications of the rectangles
	//the output is a boolean value
	public static boolean inHCGroup(double[] anchor, double[] p, double[] xs, double[] ys, double[] sizes) {
		boolean[] in1 = new boolean[p.length / 2];

		// outer loop for every hypercube
		for (int i = 0; i < xs.length; i++) {
			// inner loop for the point
			for (int j = 0; j < p.length; j += 2) {
				double xdiff = p[j] - (anchor[j] + xs[i]);
				double ydiff = p[j + 1] - (anchor[j + 1] + ys[i]);
				if (Math.abs(xdiff) <= Math.abs(sizes[i]) && Math.abs(ydiff) <= Math.abs(sizes[i])) {
					in1[j / 2] = true;
				}
			}
		}

		boolean allin = true;
		for (boolean var : in1) {
			if (!var) {
				allin = false;
			}
		}
		return allin;
	}
	
	//this method draws every nd point whose specified dimensional pair appear somewhere inside one of the rectangles in the given rectangle group
	//the inputs include the gl object, the anchor, the list of nd points to consider, the specifications for the rectangles, the RGB color for the nd points, and which dimensional pair to focus on
	public static void drawRound2(GL2 gl, double[] anchor, List<double[]> list, double[] xs, double[] ys, double[] ws,
			double[] hs, float red, float green, float blue, int pair) {
		int[] count = new int[xs.length];
		int total = 0;
		for (double[] p : list) {
			if (pairNAnyRect(anchor, p, pair, xs, ys, ws, hs)) {
				drawND(gl, anchor, p, red, green, blue);
				total++;
				// now find out which rects it is in
				for (int i = 0; i < count.length; i++) {
					if (pairInRect(anchor, p, pair, xs[i], ys[i], ws[i], hs[i])) {
						count[i]++;
					} // end if
				} // end for
			} // end if
		} // end for

		// now highlight rects with few (<20) cases
		gl.glDisable(GL.GL_DEPTH_TEST);
		for (int i = 0; i < count.length; i++) {
			if (count[i] < 20 && count[i] > 0) {
				drawRect(gl, xs[i], ys[i], ws[i], hs[i], 1, 1, 1);
				// System.out.println(count[i]);
			} // end if

		} // end for
		
		// this for is if you want the larger ones on top
		//uncomment the line inside the if to activate
		for (int element : count) {
			if (element >= 20) {
				// drawRect(gl,xs[i],ys[i],ws[i],hs[i],1,.7f,.1f);
			}
		}
		gl.glEnable(GL.GL_DEPTH_TEST);
		
		//outupt numbers if desired
		System.out.println(NDString(count));
		System.out.println(total);
	}
	
	//this method draws all the nd points that whose specified pair appears in the specified rectangle
	//these are chosen with the WASD keys, input handled in the main loop
	public static void drawR2H(GL2 gl, double[] anchor, List<double[]> list, double[][] xgrid, double[][] ygrid,
			double[][] wgrid, double[][] hgrid, int pair, int highlight, float red, float green, float blue) {
		int count = 0;
		for (double[] p : list) {
			if (pairInRect(anchor, p, pair, xgrid[pair][highlight], ygrid[pair][highlight], wgrid[pair][highlight],
					hgrid[pair][highlight])) {
				drawND(gl, anchor, p, red, green, blue, pair);
				count++;
			}
		}
		drawRect(gl, xgrid[pair][highlight], ygrid[pair][highlight], wgrid[pair][highlight], hgrid[pair][highlight]);
		System.out.println(count);
	}
	
	//this method draws every nd point that has at least one pair appear within at least one of the specified rectangles
	//the inputs include the gl object, the anchor, the list of points, the specifications of the rectangles, the RGB color of the points, and which pair to highlight
	public static void drawR2Full(GL2 gl, double[] anchor, List<double[]> list, double[][] xgrid, double[][] ygrid,
			double[][] wgrid, double[][] hgrid, float red, float green, float blue, int highlight) {
		int count = 0;
		for (double[] p : list) {
			if (allPairsAnyRectR2(anchor, p, xgrid, ygrid, wgrid, hgrid)) {
				drawND(gl, anchor, p, red, green, blue, highlight);
				count++;
			}
		}
		System.out.println(count);
	}
	
	//this method draws the nd points whose specified pair does NOT appear in the group of rectangles
	//the inputs include the gl object, the anchor, the list of points, the specifications of the rectangles, the RGB color of the points, and the dimensional pair to focus on
	public static void r2Outside(GL2 gl, double[] anchor, List<double[]> list, double[] xs, double[] ys, double[] ws,
			double[] hs, float red, float green, float blue, int pair) {
		for (double[] p : list) {
			if (!pairNAnyRect(anchor, p, pair, xs, ys, ws, hs)) {
				drawND(gl, anchor, p, red, green, blue);
			} // end if
		} // end for
	}

	//deprecated, use pairInRect instead
	public static boolean pair1InRect(double[] anchor, double[] p, double x, double y, double w, double h) {
		double relx = p[0] - anchor[0];
		double rely = p[1] - anchor[1];
		if (relx >= x && relx <= (x + w) && rely >= y && rely <= (y + h)) {
			return true;
		}
		return false;
	}
	
	//deprecated, use pairInRect instead
	public static boolean pair2InRect(double[] anchor, double[] p, double x, double y, double w, double h) {
		double relx = p[2] - anchor[2];
		double rely = p[3] - anchor[3];
		if (relx >= x && relx <= (x + w) && rely >= y && rely <= (y + h)) {
			return true;
		}
		return false;
	}
	
	//deprecated, use pairInRect instead
	public static boolean pair3InRect(double[] anchor, double[] p, double x, double y, double w, double h) {
		double relx = p[4] - anchor[4];
		double rely = p[5] - anchor[5];
		if (relx >= x && relx <= (x + w) && rely >= y && rely <= (y + h)) {
			return true;
		}
		return false;
	}
	
	//deprecated, use pairInRect instead
	public static boolean pair4InRect(double[] anchor, double[] p, double x, double y, double w, double h) {
		double relx = p[6] - anchor[6];
		double rely = p[7] - anchor[7];
		if (relx >= x && relx <= (x + w) && rely >= y && rely <= (y + h)) {
			return true;
		}
		return false;
	}
	
	//deprecated, use pairInRect instead
	public static boolean pair5InRect(double[] anchor, double[] p, double x, double y, double w, double h) {
		double relx = p[8] - anchor[8];
		double rely = p[9] - anchor[9];
		if (relx >= x && relx <= (x + w) && rely >= y && rely <= (y + h)) {
			return true;
		}
		return false;
	}
	
	//returns true if the specified dimensional pair of the specified nd point appears in the specified rectangle
	//the inputs include the anchor, the nd pair, the dimensional pair to focus on, and the specifications for the rectangle
	public static boolean pairInRect(double[] anchor, double[] p, int pair, double x, double y, double w, double h) {
		double relx = p[pair * 2] - anchor[pair * 2];
		double rely = p[pair * 2 + 1] - anchor[pair * 2 + 1];
		if (relx >= x && relx <= (x + w) && rely >= y && rely <= (y + h)) {
			return true;
		}
		return false;
	}

	//returns true if the specified pair of the specified point appears in at least one of the specified rectangles
	//the inputs include the anchor, the nd point, the dimensional pair to focus on, and the specifications of the rectangles
	public static boolean pairNAnyRect(double[] anchor, double[] p, int pair, double[] xs, double[] ys, double[] ws, double[] hs) {
		for (int i = 0; i < xs.length; i++) {
			if (pairInRect(anchor, p, pair, xs[i], ys[i], ws[i], hs[i])) {
				return true;
			}
		}
		return false;
	}
	
	//deprecated, use pair1AnyRect instead
	public static boolean pair1AnyRect(double[] anchor, double[] p, double[] xs, double[] ys, double[] ws,
			double[] hs) {
		for (int i = 0; i < xs.length; i++) {
			if (pair1InRect(anchor, p, xs[i], ys[i], ws[i], hs[i])) {
				return true;
			}
		}
		return false;
	}
	
	//deprecated, use pair1AnyRect instead
	public static boolean pair2AnyRect(double[] anchor, double[] p, double[] xs, double[] ys, double[] ws,
			double[] hs) {
		for (int i = 0; i < xs.length; i++) {
			if (pair2InRect(anchor, p, xs[i], ys[i], ws[i], hs[i])) {
				return true;
			}
		}
		return false;
	}
	
	//deprecated, use pair1AnyRect instead
	public static boolean pair3AnyRect(double[] anchor, double[] p, double[] xs, double[] ys, double[] ws,
			double[] hs) {
		for (int i = 0; i < xs.length; i++) {
			if (pair3InRect(anchor, p, xs[i], ys[i], ws[i], hs[i])) {
				return true;
			}
		}
		return false;
	}
	
	//deprecated, use pair1AnyRect instead
	public static boolean pair4AnyRect(double[] anchor, double[] p, double[] xs, double[] ys, double[] ws,
			double[] hs) {
		for (int i = 0; i < xs.length; i++) {
			if (pair4InRect(anchor, p, xs[i], ys[i], ws[i], hs[i])) {
				return true;
			}
		}
		return false;
	}
	
	//deprecated, use pair1AnyRect instead
	public static boolean pair5AnyRect(double[] anchor, double[] p, double[] xs, double[] ys, double[] ws,
			double[] hs) {
		for (int i = 0; i < xs.length; i++) {
			if (pair5InRect(anchor, p, xs[i], ys[i], ws[i], hs[i])) {
				return true;
			}
		}
		return false;
	}
	
	//returns true if ALL pairs of the specified nd point appear in the rectangles given
	//the input includes the anchor, the nd point, and the specifications of the rectangles
	public static boolean allPairsAnyRectR2(double[] anchor, double[] p, double[][] xgrid, double[][] ygrid,
			double[][] wgrid, double[][] hgrid) {
		return (pair1AnyRect(anchor, p, xgrid[0], ygrid[0], wgrid[0], hgrid[0])
				&& pair2AnyRect(anchor, p, xgrid[1], ygrid[1], wgrid[1], hgrid[1])
				&& pair3AnyRect(anchor, p, xgrid[2], ygrid[2], wgrid[2], hgrid[2])
				&& pair4AnyRect(anchor, p, xgrid[3], ygrid[3], wgrid[3], hgrid[3])
				&& pair5AnyRect(anchor, p, xgrid[4], ygrid[4], wgrid[4], hgrid[4]));
	}

	//draws every nd point that has at least one dimensional pair appear in at least one rectangle
	//the inputs include the gl object, the anchor, the list of points, the specifications for the rectangles, and the RGB to draw the polylines in
	public static int drawAllR2(GL2 gl, double[] anchor, List<double[]> list, double[][] xgrid, double[][] ygrid,
			double[][] wgrid, double[][] hgrid, float red, float green, float blue) {
		int count = 0;
		for (double[] p : list) {
			if (pair1AnyRect(anchor, p, xgrid[0], ygrid[0], wgrid[0], hgrid[0])
					|| pair2AnyRect(anchor, p, xgrid[1], ygrid[1], wgrid[1], hgrid[1])
					|| pair3AnyRect(anchor, p, xgrid[2], ygrid[2], wgrid[2], hgrid[2])
					|| pair4AnyRect(anchor, p, xgrid[3], ygrid[3], wgrid[3], hgrid[3])
					|| pair5AnyRect(anchor, p, xgrid[4], ygrid[4], wgrid[4], hgrid[4])) {
				drawND(gl, anchor, p, red, green, blue);
				count++;
			}
		}
		return count;
	}
	
	//the opposite of drawAllR2
	//draws the nd points that doesn't have a single point appear in any rectangle
	//the inputs are the same as drawAllR2
	public static int drawNoneInR2(GL2 gl, double[] anchor, List<double[]> list, double[][] xgrid, double[][] ygrid,
			double[][] wgrid, double[][] hgrid, float red, float green, float blue) {
		int count = 0;
		for (double[] p : list) {
			if (!pair1AnyRect(anchor, p, xgrid[0], ygrid[0], wgrid[0], hgrid[0])
					&& !pair2AnyRect(anchor, p, xgrid[1], ygrid[1], wgrid[1], hgrid[1])
					&& !pair3AnyRect(anchor, p, xgrid[2], ygrid[2], wgrid[2], hgrid[2])
					&& !pair4AnyRect(anchor, p, xgrid[3], ygrid[3], wgrid[3], hgrid[3])
					&& !pair5AnyRect(anchor, p, xgrid[4], ygrid[4], wgrid[4], hgrid[4])) {
				drawND(gl, anchor, p, red, green, blue);
				count++;
			}
		}
		return count;
	}

	// returns true if every pair of the nd point lies within the group of rectangles
	//the inputs include the anchor, the nd point, and the specifications of the rectangle
	public static boolean inRectGroup(double[] anchor, double[] p, double[] xs, double[] ys, double[] ws, double[] hs) {
		boolean[] in1 = new boolean[p.length / 2];

		// outer loop for every rectangle
		for (int i = 0; i < xs.length; i++) {
			// inner loop for the point
			for (int j = 0; j < p.length; j += 2) {
				double relX = p[j] - anchor[j];
				double relY = p[j + 1] - anchor[j + 1];
				if (relX >= xs[i] && relX <= (xs[i] + ws[i]) && relY >= ys[i] && relY <= (ys[i] + hs[i])) {
					in1[j / 2] = true;
				}//end if
			}//end for
		}//end for

		// now make sure all is inside
		boolean allin = true;
		for (boolean var : in1) {
			if (!var) {
				allin = false;
			}
		}
		return allin;
	}

	//returns true if every pair of the specified nd point is within the rectangle
	//the inputs include the anchor, the nd point, and the specifications of the rectangles
	public static boolean inHRect(double[] anchor, double[] p, double x, double y, double w, double h) {
		// find p relative to the anchor, where it appears on the graph,
		double[] relP = new double[p.length];
		for (int i = 0; i < relP.length; i++) {
			relP[i] = p[i] - anchor[i];
		}

		// now, for each one, check if in the box
		boolean inside = true;
		for (int i = 0; i < relP.length; i++) {
			// for x
			if (i % 2 == 0) {
				if (relP[i] < x || relP[i] > x + w) {
					inside = false;
				}
			} else if (relP[i] < y || relP[i] > y + h) {
				inside = false;
			}
		}

		return inside;
	}
	
	//returns true if every pair in the nd point lies within the hypercube
	//the inputs include the anchor, the nd point, and the specifications of the square
	public static boolean inHC(double[] anchor, double[] p, double x, double y, double size) {
		// calculate the differences
		double[] diff = new double[p.length];
		for (int i = 0; i < p.length; i++) {
			// for x
			if (i % 2 == 0) {
				diff[i] = p[i] - (anchor[i] + x);
			}
			// y
			else {
				diff[i] = p[i] - (anchor[i] + y);
			}
		}
		boolean inside = true;
		for (double element : diff) {
			if (Math.abs(element) > Math.abs(size)) {
				inside = false;
			}
		}
		return inside;
	}
	
	//same as inHC but the location of the square is at the center
	public static boolean inCenterHC(double[] anchor, double[] p, double size) {
			return inHC(anchor, p, 0, 0, size);
	}

	//draws a list of squares to the screen
	//the inputs include the gl object and the specifications of the squares
	public static void drawManyCubes(GL2 gl, double[] xs, double[] ys, double[] sizes) {
		for (int i = 0; i < xs.length; i++) {
			drawHyperCube(gl, sizes[i], xs[i], ys[i]);
		}
	}

	//draws a list of rectangles to the screen
	//the inputs include the gl object, the specifications of the rectangles, and the RGB color
	public static void drawManyRects(GL2 gl, double[] xs, double[] ys, double[] ws, double[] hs, float red, float green,
			float blue) {
		for (int i = 0; i < xs.length; i++) {
			drawRect(gl, xs[i], ys[i], ws[i], hs[i], red, green, blue);
		}
	}
	
	//same thing, but each rectangle is in a different color, starting at blue and gradually moving towards pink
	public static void drawManyRects(GL2 gl, double[] xs, double[] ys, double[] ws, double[] hs) {
		float red = 0;
		float green = 0;
		float blue = 1;
		gl.glEnable(GL2.GL_LINE_STIPPLE);
		for (int i = 0; i < xs.length; i++) {
			drawRect(gl, xs[i], ys[i], ws[i], hs[i], red, green, blue, 4 + ((float) i));
			red += 1f / (xs.length - 1);
			green += 0.3f / (xs.length - 1);
			blue -= 0.6f / (xs.length - 1);
		}
		gl.glDisable(GL2.GL_LINE_STIPPLE);
	}

	//draws a yellow square located at the center
	//the inputs are the gl object and the size of the square, from the center to an edge
	public static void centeredHyperCube(GL2 gl, double size) {
		gl.glColor3f(.9f, .9f, 0f);
		// draw the corners
		vectStuff.drawPoint(gl, size, size, 7f);
		vectStuff.drawPoint(gl, -size, size, 7f);
		vectStuff.drawPoint(gl, size, -size, 7f);
		vectStuff.drawPoint(gl, -size, -size, 7f);
		// connect them
		gl.glEnable(GL2.GL_LINE_STIPPLE);
		vectStuff.drawVect(gl, size, size, -size, size);
		vectStuff.drawVect(gl, size, size, size, -size);
		vectStuff.drawVect(gl, -size, -size, -size, size);
		vectStuff.drawVect(gl, -size, -size, size, -size);
		gl.glDisable(GL2.GL_LINE_STIPPLE);
	}

	//draws a rectangle to the screen
	//the inputs include the gl object, the location and size, and the RGB color
	public static void drawRect(GL2 gl, double x, double y, double w, double h, float red, float green, float blue) {
		gl.glColor3f(red, green, blue);
		// corners
		vectStuff.drawPoint(gl, x, y, 7f);
		vectStuff.drawPoint(gl, x, y + h, 7f);
		vectStuff.drawPoint(gl, x + w, y, 7f);
		vectStuff.drawPoint(gl, x + w, y + h, 7f);
		// connect them
		vectStuff.drawVect(gl, x, y, x, y + h);
		vectStuff.drawVect(gl, x, y + h, x + w, y + h);
		vectStuff.drawVect(gl, x, y, x + w, y);
		vectStuff.drawVect(gl, x + w, y, x + w, y + h);
	}
	
	//same thing, but the size of the corner point can also be specified
	public static void drawRect(GL2 gl, double x, double y, double w, double h, float red, float green, float blue,
			float pSize) {
		gl.glColor3f(red, green, blue);
		// corners
		vectStuff.drawPoint(gl, x, y, pSize);
		vectStuff.drawPoint(gl, x, y + h, pSize);
		vectStuff.drawPoint(gl, x + w, y, pSize);
		vectStuff.drawPoint(gl, x + w, y + h, pSize);
		// connect them
		vectStuff.drawVect(gl, x, y, x, y + h);
		vectStuff.drawVect(gl, x, y + h, x + w, y + h);
		vectStuff.drawVect(gl, x, y, x + w, y);
		vectStuff.drawVect(gl, x + w, y, x + w, y + h);
	}
	
	//same as the first version, but the only possible color is white
	public static void drawRect(GL2 gl, double x, double y, double w, double h) {
		drawRect(gl, x, y, w, h, 1, 1, 1);
	}

	//if an nd point lies entirely within a square, this method is called to print out informarion
	public static void printBasicHCRule(double[] anchor, double[] p, double x, double y, double size) {
		// calculate what the center of the HC represents
		double[] centerND = new double[anchor.length];
		for (int i = 0; i < anchor.length; i++) {
			if (i % 2 == 0) {
				centerND[i] = anchor[i] + x;
			} else {
				centerND[i] = anchor[i] + y;
			}
		}

		// get the end points of the hc
		double[] upperND = new double[anchor.length];
		double[] lowerND = new double[anchor.length];
		for (int i = 0; i < centerND.length; i++) {
			upperND[i] = centerND[i] + size;
			lowerND[i] = centerND[i] - size;
		}

		if (inHC(anchor, p, x, y, size)) {
			System.out.println("Point " + NDString(p) + " is within the hypercube defined by " + NDString(lowerND)
					+ " and " + NDString(upperND));
		}
	}
	
	//if an nd point lies entriely within a rectangle, this method is called to print out the detauks
	public static void printBasicRectRule(double[] anchor, double[] p, double x1, double y1, double x2, double y2) {
		// get the nd points
		double[] ND1 = new double[anchor.length];
		double[] ND2 = new double[anchor.length];
		for (int i = 0; i < anchor.length; i++) {
			if (i % 2 == 0) {
				ND1[i] = anchor[i] + x1;
				ND2[i] = anchor[i] + x2;
			} else {
				ND1[i] = anchor[i] + y1;
				ND2[i] = anchor[i] + y2;
			}
		}
		System.out.println("Point " + NDString(p) + " is within the hyperblock defined by " + NDString(ND1) + " and "
				+ NDString(ND2));
	}
	
	//converts an double array to a printable string
	public static String NDString(double[] p) {
		String s = "(";
		for (int i = 0; i < p.length; i++) {
			if (i != 0) {
				s = s + ", ";
			}
			s = s + p[i];
		}
		s = s + ")";
		return s;
	}
	
	//converts an integer array to a printable string
	public static String NDString(int[] p) {
		String s = "(";
		for (int i = 0; i < p.length; i++) {
			if (i != 0) {
				s = s + ", ";
			}
			s = s + p[i];
		}
		s = s + ")";
		return s;
	}

	//draws points that lie entirely within a square, and is able to distinguish between classes
	//the inputs include the gl object, the anchor, the ReadCancer object, and the specifications of the rectangle
	public static void drawInHC(GL2 gl, double[] anchor, ReadCancer data, double x, double y, double size) {
		// benign
		List<double[]> list = data.getBenign();
		for (double[] p : list) {
			if (inHC(anchor, p, x, y, size)) {// if in hc
				// print rule
				printBasicHCRule(anchor, p, x, y, size);
				// draw
				drawND(gl, anchor, p, 0, 1, 0);
			} // end if
		} // end for

		// malignant
		list = data.getMalignant();
		for (double[] p : list) {
			if (inHC(anchor, p, x, y, size)) {// if in hc
				// print rule
				printBasicHCRule(anchor, p, x, y, size);
				// draw
				drawND(gl, anchor, p, .7f, 0, .1f);
			} // end if
		} // end for
	}

	//this method searches of potential hypercubes
	//this is an early prototype, and probably needs to be re-worked from scratch
	//the inputs include the gl object, the range of the area to search, the size of the squares, the anchor, and the ReadCancer object
	public static void scanForHCIndividual(GL2 gl, double minx, double miny, double maxx, double maxy, double size,
			double[] anchor, ReadCancer data) {
		for (double curx = minx; curx <= maxx; curx += (2 * size)) {
			for (double cury = miny; cury <= maxy; cury += (2 * size)) {
				drawHyperCube(gl, size, curx, cury);
				drawInHC(gl, anchor, data, curx, cury, size);
			} // end for
		} // end for
	}
	
	//draws a white square to the screen
	//the inputs include the gl object, the square size, and the location
	public static void drawHyperCube(GL2 gl, double size, double x, double y) {
		gl.glColor3f(1f, 1f, 1f);
		// draw the corners
		vectStuff.drawPoint(gl, x + size, y + size, 7f);
		vectStuff.drawPoint(gl, x - size, y + size, 7f);
		vectStuff.drawPoint(gl, x + size, y - size, 7f);
		vectStuff.drawPoint(gl, x - size, y - size, 7f);
		// connect them
		// gl.glEnable(GL2.GL_LINE_STIPPLE);
		vectStuff.drawVect(gl, x + size, y + size, x - size, y + size);
		vectStuff.drawVect(gl, x + size, y + size, x + size, y - size);
		vectStuff.drawVect(gl, x - size, y - size, x - size, y + size);
		vectStuff.drawVect(gl, x - size, y - size, x + size, y - size);
		gl.glDisable(GL2.GL_LINE_STIPPLE);
	}
}
