package vectAddGraph;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;  // for drawing GLUT objects (such as the teapot)
import java.util.List;

/**
 * A template for a basic JOGL application with support for animation, and for
 * keyboard and mouse event handling, and for a menu.  To enable the support, 
 * uncomment the appropriate lines in main(), in the constructor, and in the
 * init() method.  See all the lines that are marked with "TODO".
 * 
 * See the JOGL documentation at http://jogamp.org/jogl/www/
 * Note that this program is based on JOGL 2.3, which has some differences
 * from earlier versions; in particular, some of the package names have changed.
 */

//the main loop is in the display method
public class DrawSPC extends JPanel implements 
                   GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {

    public static void main(String[] args) {
        JFrame window = new JFrame("JOGL");
        DrawSPC panel = new DrawSPC();
        window.setContentPane(panel);
        /* TODO: If you want to have a menu, comment out the following line. */
        //window.setJMenuBar(panel.createMenuBar());
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    private GLJPanel display;
    private Timer animationTimer;

    private int frameNumber = 0;  // The current frame number for an animation.
    private int highlight = -1;
    private int h2 = 0;
    private int hrect = 0;
    private int hpair = 0;
    
    private GLUT glut = new GLUT();  // TODO: For drawing GLUT objects, otherwise, not needed.

    public DrawSPC() {
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(600,600) );  // TODO: set display size here
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display,BorderLayout.CENTER);
        // TODO:  Other components could be added to the main panel.
        
        // TODO:  Uncomment the next two lines to enable keyboard event handling
        display.setFocusable(true);
        display.requestFocusInWindow();
        display.addKeyListener(this);

        // TODO:  Uncomment the next one or two lines to enable mouse event handling
        //display.addMouseListener(this);
        //display.addMouseMotionListener(this);
        
        //TODO:  Uncomment the following line to start the animation
        //startAnimation();

    }

    // ---------------  Methods of the GLEventListener interface -----------
    
    /**
     * This method is called when the OpenGL display needs to be redrawn.
     */
    public void display(GLAutoDrawable drawable) {    
            // called when the panel needs to be drawn
    	
    	//reads data from the excel sheet
    	ReadCancer data = new ReadCancer();
    	
    	//re-orders the dimensions
    	List<double[]> sb = data.scrambleBenign		(5, 1, 4, 3, 9, 8, 5, 2, 6, 7);
    	List<double[]> sm = data.scrambleMalignant	(5, 1, 4, 3, 9, 8, 5, 2, 6, 7);
    	List<double[]> sl = data.scrambleBigList	(5, 1, 4, 3, 9, 8, 5, 2, 6, 7);
    	
    	//the anchor is set to the average of the malignant class currently
    	double[] anchor = ReadCancer.getAvrg(sm);
    	//find the max value
    	double max = 0;
    	for (int i = 0; i < anchor.length; i++) {
    		if (anchor[i] > max) {
    			max = anchor[i];
    		}
    	}
    	
    	//openGL stuff
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0,0,0,0);
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT ); // TODO? Omit depth buffer for 2D.

        gl.glMatrixMode(GL2.GL_PROJECTION);  // TODO: Set up a better projection?
        gl.glLoadIdentity();
        gl.glOrtho(-max-1,max+1,-max-1,max+1,-2,2);//this is why 0 is at the center
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glLoadIdentity();             // Set up modelview transform. 
        
        /*
         * This is where the rectangles are defined
         * the array xr defines the x coordinates of the rectangles
         * yr defines the y coordinates of the rectangles
         * wr defines the widths of the rectangles
         * hr defines the heights (or lengths) of the rectangles
         * 
         * It is important to note that these rectangles are considered to be pure (only one class is contained within a given rectangle)
         */
        
        
        
        /*
         * This first section is old data
         * I haven't deleted it because I'm not sure if it will be needed later
         */
        
        //values for benign
        //double[] xs = 		{1};
        //double[] ys = 		{1};
        //double[] sizes = 	{1.5};
        
        //valies for malignant
        //double[] xs = 		{0};
        //double[] ys = 		{0};
        //double[] sizes = 	{3};
        
        //rects for benign
        //double[] xr = {-2.04366,	-0.8,		2.04366,	1,			2.95};
        //double[] yr = {-1.5,		2.04366,	-1.5,		2.04366,	2.95};
        //double[] wr = {4.08732,		1.2,		6.16,		1.5,		1.1};
        //double[] hr = {3.54366,		1.7,		3.16,		.955,		1.1};
        
        //rects for malignant
        //double[] xr = {-7.7178,	.81,		-3.1926,	-6.5,		1.8,	-1.5};
        //double[] yr = {-2.5726,	-4.3326,	-3.1926,	-3.1926,	-5.3,	-6.6};
        //double[] wr = {15.44,	1,			3.3,		2.6,		3,		6.5};
        //double[] hr = {10.29,	1.79,		.62,		.62,		2.75,	1.3};
        
        /*
         * The rectangles in this next section was intended for use when the dimensions were kept in the same order as they were in the csv file
         * these haven't been used very much since it was decided to switch the order around
         */
        
        //m2p,x1,x2
        //double[] xr = {3.5,	5.5};
        //double[] yr = {3.5,	-1};
        //double[] wr = {4,	2};
        //double[] hr = {6,	10};
        
        //m2p,x3,x4
        //double[] xr = {3.3,	6.3,	7.3,	3.3};
        //double[] yr = {.3,	.2,		-1,		2.3};
        //double[] wr = {6,	3,		2,		2};
        //double[] hr = {1,	9,		10,		2};
        
        //mep,x5,x6
        //double[] xr = {-1.5,	1.5,	1.5,	2.5};
        //double[] yr = {3.5,		7.5,	0,		-1};
        //double[] wr = {3,		3,		3,		2};
        //double[] hr = {5,		2,		3,		4};
        
        //m2p,x9,x9
        //double[] xr = {2.5,	8.5};
        //double[] yr = {2.5,	8.5};
        //double[] wr = {3,	1};
        //double[] hr = {3,	1};
        
        /*
         * These rectangles are the ones currently in use, meant for the way dimensions are currently scrambled
         * Uncomment the set that you want to use, and comment out the ones you are not currently using
         * Each line has two variations of the same set:
         * 	The set on the left is ordered from most number of cases covered to least number of cases covered, and is intended to be the one used
         * 	The set on the right is in an arbitrary order, and is leftover from before being sorted
         */
        
        //m2ps,x5,x1
        //double[] xr = {-4, 0, 1, 3, -3};//{-4,	0,	-3,	1,	3};
        //double[] yr = {1, -1, -2, -3, -1};//{1,	-1,	-1,	-2,	-3};
        //double[] wr = {9, 5, 4, 2, 8};//{9,	5,	8,	4,	2};
        //double[] hr = {2, 4, 5, 6, 1};//{2,	4,	1,	5,	6};
        
        //m2ps,x4,x3
        //double[] xr = {-4, 0, -5, 4, 0, -4, -2, 0, -4, -2, 1};//{-5,	-4,	-4,	-4,	-2,	-2,	0,	0,	0,	1,	4};
        //double[] yr = {1, 0, 2, -4, -1, 0, -2, -4, -2, -2, -5};//{2,	1,	0,	-2,	-2,	-2,	0,	-1,	-4,	-5,	-4};
        //double[] wr = {9, 5, 10, 1, 3, 3, 1, 5, 1, 2, 3};//{10,	9,	3,	1,	1,	2,	5,	3,	5,	3,	1};
        //double[] hr = {3, 4, 2, 8, 5, 4, 6, 2, 6, 2, 4};//{2,	3,	4,	6,	6,	2,	4,	5,	2,	4,	8};
        
        //m2ps,x9,x8
        double[] xr = {-2, 0, 1, -1, 0, 7, 1, -1, -1};//{-2,	-1,	0,	0,	1,	-1,	-1,	1,	7};
        double[] yr = {4, 0, -4, 3, -3, -5, -5, 0, -2};//{4,	3,	0,	-3,	-4,	0,	-2,	-5,	-5};
        double[] wr = {10, 8, 7, 9, 8, 1, 1, 9, 1};//{10,	9,	8,	8,	7,	9,	1,	1,	1};
        double[] hr = {1, 5, 9, 2, 2, 10, 10, 2, 4};//{1,	2,	5,	2,	9,	2,	4,	10,	10};
        
        //m2ps,x5,x2
        //double[] xr = {0, -4, 2, -4, 3, -2, -4, -2, -4, 0, -4};//{-4,	-4,	-4,	-4,	-4,	-2,	-2,	0,	2,	3,	0};
        //double[] yr = {0, 3, -2, -2, -4, -2, -2, 1, 2, -5, -2};//{3,	2,	-2,	-2,	-2,	-2,	1,	0,	-2,	-4,	-5};
        //double[] wr = {5, 9, 3, 4, 2, 1, 9, 7, 3, 5, 1};//{9,	3,	4,	1,	9,	1,	7,	5,	3,	2,	5};
        //double[] hr = {4, 1, 6, 2, 8, 6, 1, 1, 2, 2, 6};//{1,	2,	2,	6,	1,	6,	1,	4,	6,	8,	2};
        
        //m2ps,x6,x7
        //double[] xr = {1, 0, -7, -5, -4, 1, -2, -2, -2};//{-7,	-5,		-4,		0,		1,		1,		-2,		-2,		-2};
        //double[] yr = {-2.5, -1.5, 1.5, -1.5, -2.5, -3.5, -5.5, -5.5, -5.5};//{1.5,	-1.5,	-2.5,	-1.5,	-2.5,	-3.5,	-5.5,	-5.5,	-5.5};
        //double[] wr = {2, 3, 10, 2, 1, 1, 2, 5, 1};//{10,	2,		1,		3,		2,		1,		2,		1,		5};
        //double[] hr = {7, 6, 3, 6, 7, 7, 3, 2, 7};//{3,	6,		7,		6,		7,		7,		3,		7,		2};
        
        /*
         * These are the "grids"
         * They are primarily meant for when you want to have the rectangles of every pair passed to a method
         * Similar to the rectangles, they are divided based on what attribute they specify
         * xgrid is for the x position
         * ygrid is for the y position
         * wgrid is for the widths
         * hgrid is for the heights (lengths)
         * 
         * In each grid, the first row is for the first pair, the second row is for the second pair, and so on
         */
        
        /*
         * These current grids were an attempt to reduce the number of rectangles while keeping the same data
         * Due to human error, these grids are not accurate
         */
        double[][] xgrid = {
        		{-4, 0, -3},
        		{-4, 0, 4, -4, -2, 0, -4},
        		{-2, 0, 1, -1},
        		{0, -4, 2, -4, -4, -2},
        		{1, 0, -7, -4, 1, -2}
        };
        double[][] ygrid = {
        		{1, -1, -3},
        		{1, 0, -4, 0, -2, -4, -2},
        		{4, 0, -4, 3},
        		{0, 3, -2, -2, -2, 1},
        		{-2.5, -1.5, 1.5, -2.5, -3.5, -5.5}
        };
        double[][] wgrid = {
        		{9, 5, 8},
        		{9, 5, 1, 3, 1, 5, 1},
        		{10, 8, 7, 9},
        		{5, 9, 3, 4, 9, 7},
        		{2, 3, 10, 1, 1, 2}
        };
        double[][] hgrid = {
        		{2, 4, 1},
        		{3, 4, 8, 4, 6, 2, 6},
        		{1, 5, 9, 2},
        		{4, 1, 6, 2, 1, 1},
        		{7, 6, 3, 7, 7, 3}
        };
        
        /*
         * This is the section where you call the desired functions based on what you want to appear on screen
         * The functions that you aren't using can be commented out if you want to keep easy access to them for later
         * 
         * You should always call realSCP, as otherwise you would have no visual baseline to orient yourself to
         */
        
        SPCHelper.drawManyRects(gl, xr, yr, wr, hr);
        
        SPCHelper.realSCP(gl,anchor);
        
        SPCHelper.drawNthPairs(gl, anchor, sb, 0, 1, 0, 2);
        SPCHelper.drawNthPairs(gl, anchor, sm, .7f, 0, .1f, 2);
        
        //SPCHelper.drawR2H(gl, anchor, sm, xgrid, ygrid, wgrid, hgrid, hpair, hrect, .7f, 0, .1f);
        //System.out.println(SPCHelper.drawAllR2(gl, anchor, sm, xgrid, ygrid, wgrid, hgrid, .7f, 0, .1f));
        
        //System.out.println(SPCHelper.drawAllR2(gl, anchor, sm, xgrid, ygrid, wgrid, hgrid, .7f, 0, .1f));
        
        //System.out.println(SPCHelper.drawAllR2(gl, anchor, sm, xgrid, ygrid, wgrid, hgrid, .7f, 0, .1f));
        //System.out.println(SPCHelper.drawNoneInR2(gl, anchor, sm, xgrid, ygrid, wgrid, hgrid, .7f, 0, .1f));
        //SPCHelper.drawR2Full(gl, anchor, sm, xgrid, ygrid, wgrid, hgrid, .7f, 0, .1f, h2);
        //SPCHelper.drawRound2(gl, anchor, sb, xr, yr, wr, hr, 0, 1, 0);
        //SPCHelper.drawRound2(gl, anchor, sm, xr, yr, wr, hr, .7f, 0, .1f);
        
        //SPCHelper.drawRound2(gl, anchor, ReadCancer.getOutsideB(), xr, yr, wr, hr, 0,1,0);
        //SPCHelper.r2Outside(gl, anchor, ReadCancer.getOutsideB(), xr, yr, wr, hr, 0,1,0);
    }

    /**
     * This is called when the GLJPanel is first created.  It can be used to initialize
     * the OpenGL drawing context.
     */
    public void init(GLAutoDrawable drawable) {
            // called when the panel is created
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.3F, 0.3F, 0.3F, 1.0F);  // TODO: Set background color

        gl.glEnable(GL2.GL_DEPTH_TEST);  // TODO: Required for 3D drawing, not usually for 2D.
        
        // TODO: Uncomment the following 4 lines to do some typical initialization for 
        // lighting and materials.

        gl.glEnable(GL2.GL_LIGHTING);        // Enable lighting.
        gl.glEnable(GL2.GL_LIGHT0);          // Turn on a light.  By default, shines from direction of viewer.
        gl.glEnable(GL2.GL_NORMALIZE);       // OpenGL will make all normal vectors into unit normals
        gl.glEnable(GL2.GL_COLOR_MATERIAL);  // Material ambient and diffuse colors can be set by glColor*
    }

    /**
     * Called when the size of the GLJPanel changes.  Note:  glViewport(x,y,width,height)
     * has already been called before this method is called!
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // TODO: Add any code required to respond to the size of the display area.
        //             (Not usually needed.)
    }

    /**
     * This is called before the GLJPanel is destroyed.  It can be used to release OpenGL resources.
     */
    public void dispose(GLAutoDrawable drawable) {
    }
    
    
    // ------------ Support for a menu -----------------------
    
    public JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        
        MenuHandler menuHandler = new MenuHandler(); // An object to respond to menu commands.
        
        JMenu menu = new JMenu("Menu"); // Create a menu and add it to the menu bar
        menubar.add(menu);
        
        JMenuItem item = new JMenuItem("Quit");  // Create a menu command.
        item.addActionListener(menuHandler);  // Set up handling for this command.
        menu.add(item);  // Add the command to the menu.
        
        // TODO:  Add additional menu commands and menus.
        
        return menubar;
    }
    
    /**
     * A class to define the ActionListener object that will respond to menu commands.
     */
    private class MenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String command = evt.getActionCommand();  // The text of the command.
            if (command.equals("Quit")) {
                System.exit(0);
            }
            // TODO: Implement any additional menu commands.
        }
    }

    
    // ------------ Support for keyboard handling  ------------

    /**
     * Called when the user presses any key on the keyboard, including
     * special keys like the arrow keys, the function keys, and the shift key.
     * Note that the value of key will be one of the constants from
     * the KeyEvent class that identify keys such as KeyEvent.VK_LEFT,
     * KeyEvent.VK_RIGHT, KeyEvent.VK_UP, and KeyEvent.VK_DOWN for the arrow
     * keys, KeyEvent.VK_SHIFT for the shift key, and KeyEvent.VK_F1 for a
     * function key.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        // TODO:  Add code to respond to key presses.
        if (key == KeyEvent.VK_RIGHT) {
        	highlight++;
        }
        else if (key == KeyEvent.VK_LEFT && highlight != -1) {
        	highlight--;
        }
        else if (key == KeyEvent.VK_UP) {
        	h2++;
        }
        else if (key == KeyEvent.VK_DOWN && h2 > 0) {
        	h2--;
        }
        else if (key == KeyEvent.VK_D) {
        	hrect++;
        }
        else if (key == KeyEvent.VK_A && hrect > 0) {
        	hrect--;
        }
        else if (key == KeyEvent.VK_W && hpair < 4) {
        	hpair++;
        	hrect = 0;
        }
        else if (key == KeyEvent.VK_S && hpair > 0) {
        	hpair--;
        	hrect = 0;
        }
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user types a character.  This function is called in
     * addition to one or more calls to keyPressed and keyTyped. Note that ch is an
     * actual character such as 'A' or '@'.
     */
    public void keyTyped(KeyEvent e) { 
        char ch = e.getKeyChar();  // Which character was typed.
        // TODO:  Add code to respond to the character being typed.
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user releases any key.
     */
    public void keyReleased(KeyEvent e) { 
    }
    
    
    // --------------------------- animation support ---------------------------
    
    /* You can call startAnimation() to run an animation.  A frame will be drawn every
     * 30 milliseconds (can be changed in the call to glutTimerFunc.  The global frameNumber
     * variable will be incremented for each frame.  Call pauseAnimation() to stop animating.
     */
    
    private boolean animating;  // True if animation is running.  Do not set directly.
                                // This is set by startAnimation() and pauseAnimation().
    
    private void updateFrame() {
        frameNumber++;
        // TODO:  add any other updating required for the next frame.
    }
    
    public void startAnimation() {
        if ( ! animating ) {
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
    
    public void actionPerformed(ActionEvent evt) {
        updateFrame();
        display.repaint();
    }

    
    
    // ---------------------- support for mouse events ----------------------
    
    private boolean dragging;  // is a drag operation in progress?
    
    private int startX, startY;  // starting location of mouse during drag
    private int prevX, prevY;    // previous location of mouse during drag
    
    /**
     * Called when the user presses a mouse button on the display.
     */
    public void mousePressed(MouseEvent evt) {
        if (dragging) {
            return;  // don't start a new drag while one is already in progress
        }
        int x = evt.getX();  // mouse location in pixel coordinates.
        int y = evt.getY();
        // TODO: respond to mouse click at (x,y)
        dragging = true;  // might not always be correct!
        prevX = startX = x;
        prevY = startY = y;
        display.repaint();    //  only needed if display should change
    }

    /**
     * Called when the user releases a mouse button after pressing it on the display.
     */
    public void mouseReleased(MouseEvent evt) {
        if (! dragging) {
            return;
        }
        dragging = false;
        // TODO:  finish drag (generally nothing to do here)
    }

    /**
     * Called during a drag operation when the user drags the mouse on the display/
     */
    public void mouseDragged(MouseEvent evt) {
        if (! dragging) {
            return;
        }
        int x = evt.getX();  // mouse location in pixel coordinates.
        int y = evt.getY();
        // TODO:  respond to mouse drag to new point (x,y)
        prevX = x;
        prevY = y;
        display.repaint();
    }

    public void mouseMoved(MouseEvent evt) { }    // Other methods required for MouseListener, MouseMotionListener.
    public void mouseClicked(MouseEvent evt) { }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }



}
