package demos.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * @author Pepijn Van Eeckhoudt
 */
public class HelpOverlay implements GLEventListener {
	private List<String> keyboardEntries = new ArrayList<String>();
	private List<String> mouseEntries = new ArrayList<String>();
	private boolean visible = false;
	private GLUT glut = new GLUT();
	private GLU glu = new GLU();
	private static final int CHAR_HEIGHT = 12;
	private static final int OFFSET = 15;
	private static final int INDENT = 3;
	private static final String KEYBOARD_CONTROLS = "Keyboard controls";
	private static final String MOUSE_CONTROLS = "Mouse controls";

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void display(GLAutoDrawable glDrawable) {
		GL2 gl = glDrawable.getGL().getGL2();

		// Store old matrices
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();

		gl.glViewport(0, 0, glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight());

		// Store enabled state and disable lighting, texture mapping and the depth buffer
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glDisable(GL2.GL_DEPTH_TEST);

		// Retrieve the current viewport and switch to orthographic mode
		// IntBuffer viewPort = BufferUtil.newIntBuffer(4);
		// gl.glGetIntegerv(GL2.GL_VIEWPORT, viewPort);
		// glu.gluOrtho2D(0, viewPort.get(2), viewPort.get(3), 0);

		// Render the text
		gl.glColor3f(1, 1, 1);

		int x = OFFSET;
		int maxx = 0;
		int y = OFFSET + CHAR_HEIGHT;

		if (keyboardEntries.size() > 0) {
			gl.glRasterPos2i(x, y);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, KEYBOARD_CONTROLS);
			maxx = Math.max(maxx, OFFSET + glut.glutBitmapLength(GLUT.BITMAP_HELVETICA_12, KEYBOARD_CONTROLS));

			y += OFFSET;
			x += INDENT;
			for (int i = 0; i < keyboardEntries.size(); i++) {
				gl.glRasterPos2f(x, y);
				String text = keyboardEntries.get(i);
				glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, text);
				maxx = Math.max(maxx, OFFSET + glut.glutBitmapLength(GLUT.BITMAP_HELVETICA_12, text));
				y += OFFSET;
			}
		}

		if (mouseEntries.size() > 0) {
			x = maxx + OFFSET;
			y = OFFSET + CHAR_HEIGHT;
			gl.glRasterPos2i(x, y);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, MOUSE_CONTROLS);

			y += OFFSET;
			x += INDENT;
			for (int i = 0; i < mouseEntries.size(); i++) {
				gl.glRasterPos2f(x, y);
				glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, mouseEntries.get(i));
				y += OFFSET;
			}
		}

		// Restore enabled state
		gl.glPopAttrib();

		// Restore old matrices
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPopMatrix();
	}

	public void init(GLAutoDrawable glDrawable) {
	}

	public void reshape(GLAutoDrawable glDrawable, int i, int i1, int i2, int i3) {
	}

	/**
	 * @param keyStroke
	 * @param description
	 */
	public void registerKeyStroke(KeyStroke keyStroke, String description) {
		// String modifiersText = KeyEvent.getKeyModifiersText(keyStroke.getModifiers());
		// String keyText = KeyEvent.getKeyText(e.keyCode);
		keyboardEntries.add(keyStroke.format() + ": " + description);
	}

	/**
	 * @param id
	 * @param modifiers
	 * @param description
	 */
	public void registerMouseEvent(int id, int modifiers, String description) {
		String mouseText = null;
		switch (id) {
		case SWT.MouseDoubleClick:
			// case MouseEvent.MOUSE_CLICKED:
			mouseText = "Mouse DoubleClicked ";
			// mouseText = "Clicked " + MouseEvent.getModifiersExText(modifiers);
			break;
		case SWT.DRAG:
			// case MouseEvent.MOUSE_DRAGGED:
			mouseText = "Mouse Dragged ";
			// mouseText = "Dragged " + MouseEvent.getModifiersExText(modifiers);
			break;
		case SWT.CR:
			// case MouseEvent.MOUSE_ENTERED:
			mouseText = "Mouse enters";
			break;
		case SWT.ESC:
			// case MouseEvent.MOUSE_EXITED:
			mouseText = "Mouse exits";
			break;
		case SWT.Move:
			// case MouseEvent.MOUSE_MOVED:
			mouseText = "Mouse moves";
			break;
		case SWT.MouseDown:
			// case MouseEvent.MOUSE_PRESSED:
			mouseText = "Mouse Pressed";
			// mouseText = "Pressed " + MouseEvent.getModifiersExText(modifiers);
			break;
		case SWT.MouseUp:
			// case MouseEvent.MOUSE_RELEASED:
			mouseText = "Mouse Released";
			// mouseText = "Released " + MouseEvent.getModifiersExText(modifiers);
			break;
		}
		mouseEntries.add(mouseText + ": " + description);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}
