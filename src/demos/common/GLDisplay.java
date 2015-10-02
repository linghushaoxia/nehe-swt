package demos.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.swt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * @author Pepijn Van Eeckhoudt
 */
public class GLDisplay {
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;

	private static final int DONT_CARE = -1;

	private Shell shell;
	private GLCanvas glCanvas;
	private FPSAnimator animator;
	private boolean fullscreen;
	private int width;
	private int height;

	private MyHelpOverlayGLEventListener helpOverlayGLEventListener = new MyHelpOverlayGLEventListener();

	// private MyExceptionHandler exceptionHandler = new MyExceptionHandler();

	/**
	 * @param display
	 * @param title
	 * @return
	 */
	public static GLDisplay createGLDisplay(Display display, String title) {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		final GLCapabilities capabilities = new GLCapabilities(profile);
		return createGLDisplay(display, title, capabilities);
	}

	/**
	 * @param display
	 * @param title
	 * @param capabilities
	 * @return
	 */
	public static GLDisplay createGLDisplay(Display display, String title, GLCapabilities capabilities) {
		boolean fullscreen = false;
		return new GLDisplay(display, title, DEFAULT_WIDTH, DEFAULT_HEIGHT, fullscreen, capabilities);
	}

	/**
	 * @param title
	 * @param width
	 * @param height
	 * @param fullscreen
	 * @param capabilities
	 */
	private GLDisplay(Display display, String title, int width, int height, boolean fullscreen, GLCapabilities capabilities) {
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setFullScreen(fullscreen);
		glCanvas = GLCanvas.create(shell, SWT.NONE, capabilities, null);
		glCanvas.addGLEventListener(helpOverlayGLEventListener);

		addKeyListener(new MyKeyAdapter());

		shell.addDisposeListener(new MyDisposeAdapter());

		this.fullscreen = fullscreen;
		this.width = width;
		this.height = height;

		animator = new FPSAnimator(glCanvas, 60);
	}

	public void start() {
		try {
			glCanvas.forceFocus();
			shell.open();
			animator.start();
		} catch (Exception e) {
			// exceptionHandler.handleException(e);
		}
	}

	public void stop() {
		try {
			animator.stop();
			shell.dispose();
		} catch (Exception e) {
			// exceptionHandler.handleException(e);
		} finally {
			System.exit(0);
		}
	}

	/**
	 * @param displayModes
	 * @param requestedWidth
	 * @param requestedHeight
	 * @param requestedDepth
	 * @param requestedRefreshRate
	 * @return
	 */
	// private DisplayMode findDisplayMode(DisplayMode[] displayModes, int requestedWidth, int requestedHeight, int
	// requestedDepth, int requestedRefreshRate) {
	// // Try to find an exact match
	// DisplayMode displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, requestedDepth,
	// requestedRefreshRate);
	//
	// // Try again, ignoring the requested bit depth
	// if (displayMode == null)
	// displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, DONT_CARE, DONT_CARE);
	//
	// // Try again, and again ignoring the requested bit depth and height
	// if (displayMode == null)
	// displayMode = findDisplayModeInternal(displayModes, requestedWidth, DONT_CARE, DONT_CARE, DONT_CARE);
	//
	// // If all else fails try to get any display mode
	// if (displayMode == null)
	// displayMode = findDisplayModeInternal(displayModes, DONT_CARE, DONT_CARE, DONT_CARE, DONT_CARE);
	//
	// return displayMode;
	// }

	/**
	 * @param displayModes
	 * @param requestedWidth
	 * @param requestedHeight
	 * @param requestedDepth
	 * @param requestedRefreshRate
	 * @return
	 */
	// private DisplayMode findDisplayModeInternal(DisplayMode[] displayModes, int requestedWidth, int requestedHeight,
	// int requestedDepth, int
	// requestedRefreshRate) {
	// DisplayMode displayModeToUse = null;
	// for (int i = 0; i < displayModes.length; i++) {
	// DisplayMode displayMode = displayModes[i];
	// if ((requestedWidth == DONT_CARE || displayMode.getWidth() == requestedWidth) && (requestedHeight == DONT_CARE ||
	// displayMode.getHeight() ==
	// requestedHeight)
	// && (requestedHeight == DONT_CARE || displayMode.getRefreshRate() == requestedRefreshRate) && (requestedDepth ==
	// DONT_CARE || displayMode.getBitDepth() ==
	// requestedDepth))
	// displayModeToUse = displayMode;
	// }
	//
	// return displayModeToUse;
	// }

	/**
	 * @param listener
	 */
	public void addGLEventListener(GLEventListener listener) {
		this.helpOverlayGLEventListener.addGLEventListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeGLEventListener(GLEventListener listener) {
		this.helpOverlayGLEventListener.removeGLEventListener(listener);
	}

	/**
	 * @param listener
	 */
	public void addKeyListener(KeyListener listener) {
		glCanvas.addKeyListener(listener);
	}

	/**
	 * @param listener
	 */
	public void addMouseListener(MouseListener listener) {
		glCanvas.addMouseListener(listener);
	}

	/**
	 * @param listener
	 */
	public void addMouseMoveListener(MouseMoveListener listener) {
		glCanvas.addMouseMoveListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeKeyListener(KeyListener listener) {
		glCanvas.removeKeyListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeMouseListener(MouseListener listener) {
		glCanvas.removeMouseListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeMouseMoveListener(MouseMoveListener listener) {
		glCanvas.removeMouseMoveListener(listener);
	}

	/**
	 * @param keyStroke
	 * @param description
	 */
	public void registerKeyStrokeForHelp(KeyStroke keyStroke, String description) {
		helpOverlayGLEventListener.registerKeyStroke(keyStroke, description);
	}

	/**
	 * @param id
	 * @param modifiers
	 * @param description
	 */
	public void registerMouseEventForHelp(int id, int modifiers, String description) {
		helpOverlayGLEventListener.registerMouseEvent(id, modifiers, description);
	}

	public String getTitle() {
		return shell.getText();
	}

	public void setTitle(String title) {
		shell.setText(title);
	}

	/**
	 * key listener
	 */
	private class MyKeyAdapter extends KeyAdapter {
		public MyKeyAdapter() {
			registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.F1), "Show/hide help message");
			registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.ESC), "Quit demo");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.keyCode) {
			case SWT.ESC:
				stop();
				break;
			case SWT.F1:
				helpOverlayGLEventListener.toggleHelp();
				break;
			}
		}
	}

	private class MyDisposeAdapter implements DisposeListener {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			stop();
		}
	}

	//
	// private class MyExceptionHandler implements ExceptionHandler {
	// public void handleException(final Exception e) {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// StringWriter stringWriter = new StringWriter();
	// PrintWriter printWriter = new PrintWriter(stringWriter);
	// e.printStackTrace(printWriter);
	// JOptionPane.showMessageDialog(frame, stringWriter.toString(), "Exception occurred", JOptionPane.ERROR_MESSAGE);
	// stop();
	// }
	// });
	// }
	// }

	private static class MyHelpOverlayGLEventListener implements GLEventListener {
		private java.util.List eventListeners = new ArrayList();
		private HelpOverlay helpOverlay = new HelpOverlay();
		private boolean showHelp = false;

		public void toggleHelp() {
			showHelp = !showHelp;
		}

		public void registerKeyStroke(KeyStroke keyStroke, String description) {
			helpOverlay.registerKeyStroke(keyStroke, description);
		}

		public void registerMouseEvent(int id, int modifiers, String description) {
			helpOverlay.registerMouseEvent(id, modifiers, description);
		}

		public void addGLEventListener(GLEventListener glEventListener) {
			eventListeners.add(glEventListener);
		}

		public void removeGLEventListener(GLEventListener glEventListener) {
			eventListeners.remove(glEventListener);
		}

		public void display(GLAutoDrawable drawable) {
			for (int i = 0; i < eventListeners.size(); i++) {
				((GLEventListener) eventListeners.get(i)).display(drawable);
			}
			if (showHelp)
				helpOverlay.display(drawable);
		}

		public void init(GLAutoDrawable drawable) {
			for (int i = 0; i < eventListeners.size(); i++) {
				((GLEventListener) eventListeners.get(i)).init(drawable);
			}
		}

		public void reshape(GLAutoDrawable drawable, int i0, int i1, int i2, int i3) {
			for (int i = 0; i < eventListeners.size(); i++) {
				((GLEventListener) eventListeners.get(i)).reshape(drawable, i0, i1, i2, i3);
			}
		}

		@Override
		public void dispose(GLAutoDrawable drawable) {
			for (int i = 0; i < eventListeners.size(); i++) {
				((GLEventListener) eventListeners.get(i)).dispose(drawable);
			}
		}
	}
}
