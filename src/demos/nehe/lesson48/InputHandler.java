package demos.nehe.lesson48;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;

import demos.common.GLDisplay;

class InputHandler implements MouseListener, MouseMoveListener {
	private Renderer renderer;
	private boolean right;

	public InputHandler(Renderer renderer, GLDisplay glDisplay) {
		this.renderer = renderer;
		glDisplay.registerMouseEventForHelp(SWT.MouseDoubleClick, SWT.BUTTON1, "Toggle display mode");
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (right) {
			renderer.drag(new Point(e.x, e.y));
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		if (e.button == 1) {
			renderer.reset();
		}
	}

	@Override
	public void mouseDown(MouseEvent e) {
		if (e.button == 1) {
			right = true;
			renderer.startDrag(new Point(e.x, e.y));
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		right = false;
	}
}
