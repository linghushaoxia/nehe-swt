package demos.nehe.lesson05;

import org.eclipse.swt.widgets.Display;

import demos.common.GLDisplay;

/**
 * @author Kevin J. Duling
 */
public class Lesson05 {
    public static void main(String[] args) {
        Display display = Display.getDefault();
		GLDisplay neheGLDisplay = GLDisplay.createGLDisplay(display, "Lesson 05: 3D Shapes");
		neheGLDisplay.addGLEventListener(new Renderer());
		neheGLDisplay.start();
		while (!display.isDisposed()) {
			if (display.readAndDispatch()) {
				display.sleep();
			}
		}
    }
}
