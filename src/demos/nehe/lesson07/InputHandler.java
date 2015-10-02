package demos.nehe.lesson07;


import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import demos.common.GLDisplay;

class InputHandler extends KeyAdapter {
	private Renderer renderer;

	public InputHandler(Renderer renderer, GLDisplay glDisplay) {
		this.renderer = renderer;
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance('l'), "Toggle lighting");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance('f'), "Switch texture filter");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.PAGE_UP), "Zoom in");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.PAGE_DOWN), "Zoom out");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.ARROW_DOWN), "Rotate slower along X-axis");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.ARROW_UP), "Rotate faster along X-axis");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.ARROW_LEFT), "Rotate slower along Y-axis");
		glDisplay.registerKeyStrokeForHelp(KeyStroke.getInstance(SWT.ARROW_RIGHT), "Rotate faster along Y-axis");
	}

	public void keyPressed(KeyEvent e) {
		processKeyEvent(e, true);
	}

	public void keyReleased(KeyEvent e) {
		switch (e.character) {
		case 'l':
			renderer.toggleLighting();
			break;
		case 'f':
			renderer.switchFilter();
			break;
		default:
			processKeyEvent(e, false);
		}
	}

	private void processKeyEvent(KeyEvent e, boolean pressed) {
		switch (e.keyCode) {
		case SWT.PAGE_UP:
			renderer.zoomIn(pressed);
			break;
		case SWT.PAGE_DOWN:
			renderer.zoomOut(pressed);
			break;
		case SWT.ARROW_UP:
			renderer.increaseXspeed(pressed);
			break;
		case SWT.ARROW_DOWN:
			renderer.decreaseXspeed(pressed);
			break;
		case SWT.ARROW_RIGHT:
			renderer.increaseYspeed(pressed);
			break;
		case SWT.ARROW_LEFT:
			renderer.decreaseYspeed(pressed);
			break;
		}
	}
}
