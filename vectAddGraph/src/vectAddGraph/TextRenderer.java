package vectAddGraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

//the purpose of this class is to simplify drawing text to the screen
public class TextRenderer {
	private Texture texture;
	private String text;
	private int textWidth, textHeight;

	// temporarily instantiates a private class for processing
	// not meant to be created by the user
	private TextRenderer(String text) {
		this.text = text;
		createTexture();
	}

	// the user calls this method to draw text to the screen
	// the input is the gl object, the text to write, and the coordinates
	// to choose a color, use the opengl command to change color before calling this
	// method
	public static void draw(GL2 gl, String text, double x, double y) {
		gl.glPushMatrix();
		gl.glScalef(0.005f, 0.005f, 1.0f); // Scale down the text
		TextRenderer asdf = new TextRenderer(text);
		asdf.render(gl, x * 200, y * 200);// *200 to fix scaling
		gl.glPopMatrix();
	}

	// this method converts the text into a texture
	private void createTexture() {
		// use a temporary bufferedimage to measure text size
		BufferedImage tmpImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = tmpImg.createGraphics();
		g2d.setFont(new Font("Arial", Font.PLAIN, 24));
		FontMetrics fm = g2d.getFontMetrics();
		textWidth = fm.stringWidth(text);
		textHeight = fm.getHeight();
		g2d.dispose();

		// create the real bufferedimage with correct size
		BufferedImage img = new BufferedImage(textWidth, textHeight, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setFont(new Font("Arial", Font.PLAIN, 24));

		// draw
		g2d.setColor(Color.WHITE); // Change to any color you like, e.g., Color.WHITE for white text
		g2d.drawString(text, 0, fm.getAscent());
		g2d.dispose();

		// debug: check if the bufferedimage is created correctly
		if (img == null) {
			System.err.println("BufferedImage is null.");
		} else {
			// System.out.println("BufferedImage created with size: " + textWidth + "x" +
			// textHeight);
		}

		// convert bufferedimage to opengl texture
		try {
			texture = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);
		} catch (Exception e) {
			System.err.println("Error creating texture: " + e.getMessage());
		}

		// debug: check if the texture is created
		if (texture == null) {
			System.err.println("Texture is null.");
		} else {
			// System.out.println("Texture created with size: " + texture.getWidth() + "x" +
			// texture.getHeight());
		}
	}

	// this method renders the created texture
	public void render(GL2 gl, double x, double y) {
		if (texture == null) {
			System.err.println("Texture not available for rendering.");
			return;
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		texture.bind(gl);

		// debug: print texture binding
		// System.out.println("Rendering at position: (" + x + ", " + y + ")");
		// System.out.println("Texture size: " + texture.getWidth() + "x" +
		// texture.getHeight());

		gl.glBegin(GL2ES3.GL_QUADS);
		gl.glTexCoord2d(0, 1);
		gl.glVertex2d(x, y);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2d(x + textWidth, y);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2d(x + textWidth, y + textHeight);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2d(x, y + textHeight);
		gl.glEnd();

		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
}
