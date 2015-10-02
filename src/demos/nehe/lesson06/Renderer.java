package demos.nehe.lesson06;

/*
 * Lesson06.java
 *
 * Created on July 16, 2003, 11:30 AM
 */

import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import demos.common.TextureReader;

/**
 * Port of the NeHe OpenGL Tutorial (Lesson 6) to Java using the Jogl interface to OpenGL. Jogl can be obtained at
 * http://jogl.dev.java.net/
 * 
 * @author Kevin Duling (jattier@hotmail.com)
 */
class Renderer implements GLEventListener {
	private float xrot; // X Rotation ( NEW )
	private float yrot; // Y Rotation ( NEW )
	private float zrot; // Z Rotation ( NEW )
	private int texture;

	private GLU glu = new GLU();

	/**
	 * Called by the drawable to initiate OpenGL rendering by the client. After all GLEventListeners have been notified
	 * of a display event, the drawable will swap its buffers if necessary.
	 * 
	 * @param drawable
	 *            The GLAutoDrawable object.
	 */
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View
		gl.glTranslatef(0.0f, 0.0f, -5.0f);

		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		gl.glBegin(GL2.GL_QUADS);
		// Front Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		// Back Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		// Top Face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		// Bottom Face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		// Right face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		// Left Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();

		xrot += 0.3f;
		yrot += 0.2f;
		zrot += 0.4f;
	}

	/**
	 * Called by the drawable immediately after the OpenGL context is initialized for the first time. Can be used to
	 * perform one-time OpenGL initialization such as setup of lights and display lists.
	 * 
	 * @param drawable
	 *            The GLAutoDrawable object.
	 */
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glEnable(GL2.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL2.GL_LEQUAL); // The Type Of Depth Testing To Do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // Really Nice Perspective Calculations
		gl.glEnable(GL2.GL_TEXTURE_2D);
		texture = genTexture(gl);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		TextureReader.Texture texture = null;
		try {
			texture = TextureReader.readTexture("resources/demos/data/images/nehe.png");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		makeRGBTexture(gl, glu, texture, GL2.GL_TEXTURE_2D, false);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	}

	/**
	 * Called by the drawable during the first repaint after the component has been resized. The client can update the
	 * viewport and view volume of the window appropriately, for example by a call to GL2.glViewport(int, int, int,
	 * int); note that for convenience the component has already called GL2.glViewport(int, int, int, int)(x, y, width,
	 * height) when this method is called, so the client may not have to do anything in this method.
	 * 
	 * @param drawable
	 *            The GLAutoDrawable object.
	 * @param x
	 *            The X Coordinate of the viewport rectangle.
	 * @param y
	 *            The Y coordinate of the viewport rectanble.
	 * @param width
	 *            The new width of the window.
	 * @param height
	 *            The new height of the window.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		if (height <= 0) // avoid a divide by zero error!
			height = 1;
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	/**
	 * @param gl
	 * @param glu
	 * @param img
	 * @param target
	 * @param mipmapped
	 */
	private void makeRGBTexture(GL2 gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
		if (mipmapped) {
			glu.gluBuild2DMipmaps(target, GL2.GL_RGB8, img.getWidth(), img.getHeight(), GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, img.getPixels());
		} else {
			gl.glTexImage2D(target, 0, GL2.GL_RGB, img.getWidth(), img.getHeight(), 0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, img.getPixels());
		}
	}

	/**
	 * @param gl
	 * @return
	 */
	private int genTexture(GL2 gl) {
		final int[] tmp = new int[1];
		gl.glGenTextures(1, tmp, 0);
		return tmp[0];
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}
