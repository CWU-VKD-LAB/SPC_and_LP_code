package vectAddGraph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.gl2.GLUT; // for drawing GLUT objects (such as the teapot)

/**
 * A template for a basic JOGL application with support for animation, and for
 * keyboard and mouse event handling, and for a menu. To enable the support,
 * uncomment the appropriate lines in main(), in the constructor, and in the
 * init() method. See all the lines that are marked with "TODO".
 * 
 * See the JOGL documentation at http://jogamp.org/jogl/www/ Note that this
 * program is based on JOGL 2.3, which has some differences from earlier
 * versions; in particular, some of the package names have changed.
 */
public class DrawSC extends JPanel
		implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {

	public static void main(String[] args) {
		JFrame window = new JFrame("JOGL");
		DrawSC panel = new DrawSC();
		window.setContentPane(panel);
		/* TODO: If you want to have a menu, uncomment the following line. */
		window.setJMenuBar(panel.createMenuBar());
		window.pack();
		window.setLocation(50, 20);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private GLJPanel display;
	private Timer animationTimer;

	private int frameNumber = 0; // The current frame number for an animation.

	private double[][] yCoords = null;
	private ValHolder stuff;
	private int screen = 0;
	private double[][] tableau = {
			{ 1, 1, 0, 1, 0, 0, 4 },
			{ 1, 3, 0, 0, 1, 0, 6 },
			{ 0, 1, 1, 0, 0, 1, 5 },
			{ -3, -5, -2, 0, 0, 0, 0 }
	};

	private GLUT glut = new GLUT(); // TODO: For drawing GLUT objects, otherwise, not needed.

	public DrawSC() {
		GLCapabilities caps = new GLCapabilities(null);
		display = new GLJPanel(caps);
		display.setPreferredSize(new Dimension(600, 600)); // TODO: set display size here
		display.addGLEventListener(this);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		// TODO: Other components could be added to the main panel.

		// TODO: Uncomment the next two lines to enable keyboard event handling
		// requestFocusInWindow();
		// addKeyListener(this);

		// TODO: Uncomment the next one or two lines to enable mouse event handling
		display.addMouseListener(this);
		display.addMouseMotionListener(this);

		// TODO: Uncomment the following line to start the animation
		//startAnimation();

	}

	// --------------- Methods of the GLEventListener interface -----------

	/**
	 * This method is called when the OpenGL display needs to be redrawn.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		// called when the panel needs to be drawn

		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // TODO? Omit depth buffer for 2D.

		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION); // TODO: Set up a better projection?
		gl.glLoadIdentity();
		gl.glOrtho(-0.1, 3.2, -0.1, 3.2, -1, 1);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

		gl.glLoadIdentity(); // Set up modelview transform.

		// TODO: add drawing code here!!

		// draw the vectors
		//System.out.println(screen);
		if (screen == 0) {
			stuff = CDraw.render(gl, tableau, yCoords);
			yCoords = stuff.getYCoords();
		}
		else if (screen == 1) {
			CDraw.drawConstraints(gl, tableau);
		}
		// draw the axes
		gl.glColor3f(1.0f, 1.0f, 1.0f);// white

		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(-99, 0);
		gl.glVertex2d(99, 0);
		gl.glEnd();

		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(0, -99); // vector start
		gl.glVertex2d(0, 99); // vector end
		gl.glEnd();

	}

	/**
	 * This is called when the GLJPanel is first created. It can be used to
	 * initialize the OpenGL drawing context.
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		// called when the panel is created
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.3F, 0.3F, 0.3F, 1.0F); // TODO: Set background color

		// gl.glEnable(GL2.GL_DEPTH_TEST); // TODO: Required for 3D drawing, not usually
		// for 2D.

		// TODO: Uncomment the following 4 lines to do some typical initialization for
		// lighting and materials.

		gl.glEnable(GLLightingFunc.GL_LIGHTING); // Enable lighting.
		gl.glEnable(GLLightingFunc.GL_LIGHT0); // Turn on a light. By default, shines from direction of viewer.
		gl.glEnable(GLLightingFunc.GL_NORMALIZE); // OpenGL will make all normal vectors into unit normals
		gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL); // Material ambient and diffuse colors can be set by glColor*
	}

	/**
	 * Called when the size of the GLJPanel changes. Note:
	 * glViewport(x,y,width,height) has already been called before this method is
	 * called!
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO: Add any code required to respond to the size of the display area.
		// (Not usually needed.)
	}

	/**
	 * This is called before the GLJPanel is destroyed. It can be used to release
	 * OpenGL resources.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	// ------------ Support for a menu -----------------------

	public JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();

		MenuHandler menuHandler = new MenuHandler(); // An object to respond to menu commands.

		JMenu menu = new JMenu("Save"); // Create a menu and add it to the menu bar
		menubar.add(menu);
		
		JMenu menu2 = new JMenu("Screen"); // Create a menu and add it to the menu bar
		menubar.add(menu2);

		JMenuItem item = new JMenuItem("Save Y Vector to File"); // Create a menu command.
		item.addActionListener(menuHandler); // Set up handling for this command.
		menu.add(item); // Add the command to the menu.
		
		JMenuItem item2 = new JMenuItem("Objective Function"); // Create a menu command.
		item2.addActionListener(menuHandler); // Set up handling for this command.
		menu2.add(item2); // Add the command to the menu.
		
		JMenuItem item3 = new JMenuItem("Constraints"); // Create a menu command.
		item3.addActionListener(menuHandler); // Set up handling for this command.
		menu2.add(item3); // Add the command to the menu.
		
		// TODO: Add additional menu commands and menus.

		return menubar;
	}

	/**
	 * A class to define the ActionListener object that will respond to menu
	 * commands.
	 */
	private class MenuHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			String command = evt.getActionCommand(); // The text of the command.

			// saves the Y vector to a file
			if (command.equals("Save Y Vector to File")) {
				try (FileOutputStream fileOut = new FileOutputStream("yVect.ser");
						ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
					out.writeObject(yCoords);
					System.out.println("Y Vector has been saved to File");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (command.equals("Objective Function")) {
				screen = 0;
			}
			else if (command.equals("Constraints")) {
				screen = 1;
			}
			// TODO: Implement any additional menu commands.
			display.repaint();
		}
	}

	// ------------ Support for keyboard handling ------------

	/**
	 * Called when the user presses any key on the keyboard, including special keys
	 * like the arrow keys, the function keys, and the shift key. Note that the
	 * value of key will be one of the constants from the KeyEvent class that
	 * identify keys such as KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
	 * and KeyEvent.VK_DOWN for the arrow keys, KeyEvent.VK_SHIFT for the shift key,
	 * and KeyEvent.VK_F1 for a function key.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		e.getKeyCode();
		// TODO: Add code to respond to key presses.
		display.repaint(); // Causes the display() function to be called.
	}

	/**
	 * Called when the user types a character. This function is called in addition
	 * to one or more calls to keyPressed and keyTyped. Note that ch is an actual
	 * character such as 'A' or '@'.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		e.getKeyChar();
		// TODO: Add code to respond to the character being typed.
		display.repaint(); // Causes the display() function to be called.
	}

	/**
	 * Called when the user releases any key.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	// --------------------------- animation support ---------------------------

	/*
	 * You can call startAnimation() to run an animation. A frame will be drawn
	 * every 30 milliseconds (can be changed in the call to glutTimerFunc. The
	 * global frameNumber variable will be incremented for each frame. Call
	 * pauseAnimation() to stop animating.
	 */

	private boolean animating; // True if animation is running. Do not set directly.
								// This is set by startAnimation() and pauseAnimation().

	private void updateFrame() {
		frameNumber++;
		// TODO: add any other updating required for the next frame.
	}

	public void startAnimation() {
		if (!animating) {
			if (animationTimer == null) {
				animationTimer = new Timer(30, this);
			}
			animationTimer.start();
			animating = true;
		}
	}

	public void pauseAnimation() {
		if (animating) {
			animationTimer.stop();
			animating = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		updateFrame();
		display.repaint();
	}

	// ---------------------- support for mouse events ----------------------

	private boolean dragging; // is a drag operation in progress?

	private int startX, startY; // starting location of mouse during drag
	private int prevX, prevY; // previous location of mouse during drag

	/**
	 * Called when the user presses a mouse button on the display.
	 */
	@Override
	public void mousePressed(MouseEvent evt) {
		if (dragging) {
			return; // don't start a new drag while one is already in progress
		}
		int x = evt.getX(); // mouse location in pixel coordinates.
		int y = evt.getY();
		/*
		 * After the mouse click, the yCoords get re-calculated
		 */
		if (screen == 0) {
			double[] adjustedMouse = CDraw.screenToWorld2D(x, y, 600, 600, -0.1, 3.2, -0.1, 3.2);
			yCoords = CDraw.calcNewYCoordsBasedOnClick(stuff.getXCoords(), yCoords, adjustedMouse[0], adjustedMouse[1]);
			// printing for debugging purposes
			// System.out.println(adjustedMouse[0]+"\n"+x+"\n"+y);
		}
		dragging = true; // might not always be correct!
		prevX = startX = x;
		prevY = startY = y;
		display.repaint(); // only needed if display should change
	}

	/**
	 * Called when the user releases a mouse button after pressing it on the
	 * display.
	 */
	@Override
	public void mouseReleased(MouseEvent evt) {
		if (!dragging) {
			return;
		}
		dragging = false;
		// TODO: finish drag (generally nothing to do here)
	}

	/**
	 * Called during a drag operation when the user drags the mouse on the display/
	 */
	@Override
	public void mouseDragged(MouseEvent evt) {
		if (!dragging) {
			return;
		}
		int x = evt.getX(); // mouse location in pixel coordinates.
		int y = evt.getY();
		// TODO: respond to mouse drag to new point (x,y)
		prevX = x;
		prevY = y;
		display.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
	} // Other methods required for MouseListener, MouseMotionListener.

	@Override
	public void mouseClicked(MouseEvent evt) {
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
	}

	@Override
	public void mouseExited(MouseEvent evt) {
	}

}
