package demos.nehe.lesson02;

import org.eclipse.swt.widgets.Display;

import demos.common.GLDisplay;

/**
 * @author Kevin J. Duling
 */
public class Lesson02 {
	public static void main(String[] args) {
		Display display = Display.getDefault();
		GLDisplay neheGLDisplay = GLDisplay.createGLDisplay(display, "Lesson 02: Your First Polygon");
		neheGLDisplay.addGLEventListener(new Renderer());
		neheGLDisplay.start();
		while (!display.isDisposed()) {
			if (display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
