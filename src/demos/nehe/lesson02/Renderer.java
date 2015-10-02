package demos.nehe.lesson02;

/*
 * Lesson02.java
 *
 * Created on July 10, 2003, 2:33 PM
 */

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

/**
 * Port of the NeHe OpenGL Tutorial (Lesson 2) to Java using the Jogl interface to OpenGL. Jogl can be obtained at
 * http://jogl.dev.java.net/
 * 
 * @author Kevin Duling (jattier@hotmail.com)
 */
class Renderer implements GLEventListener {
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
		gl.glLoadIdentity();
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		gl.glBegin(GL2.GL_TRIANGLES); // Drawing Using Triangles
		gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top
		gl.glVertex3f(-1.0f, -1.0f, 0.0f); // Bottom Left
		gl.glVertex3f(1.0f, -1.0f, 0.0f); // Bottom Right
		gl.glEnd(); // Finished Drawing The Triangle
		gl.glTranslatef(3.0f, 0.0f, 0.0f);
		gl.glBegin(GL2.GL_QUADS); // Draw A Quad
		gl.glVertex3f(-1.0f, 1.0f, 0.0f); // Top Left
		gl.glVertex3f(1.0f, 1.0f, 0.0f); // Top Right
		gl.glVertex3f(1.0f, -1.0f, 0.0f); // Bottom Right
		gl.glVertex3f(-1.0f, -1.0f, 0.0f); // Bottom Left
		gl.glEnd(); // Done Drawing The Quad
		gl.glFlush();
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
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL2.GL_FLAT);
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

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}
