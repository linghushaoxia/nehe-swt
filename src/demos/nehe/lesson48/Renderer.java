package demos.nehe.lesson48;

import org.eclipse.swt.graphics.Point;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

class Renderer implements GLEventListener {
	// User Defined Variables
	private GLUquadric quadratic; // Used For Our Quadric
	private GLU glu = new GLU();

	private Matrix4f LastRot = new Matrix4f();
	private Matrix4f ThisRot = new Matrix4f();
	private final Object matrixLock = new Object();
	private float[] matrix = new float[16];

	private ArcBall arcBall = new ArcBall(640.0f, 480.0f); // NEW: ArcBall Instance

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glViewport(0, 0, width, height); // Reset The Current Viewport
		gl.glMatrixMode(GL2.GL_PROJECTION); // Select The Projection Matrix
		gl.glLoadIdentity(); // Reset The Projection Matrix
		glu.gluPerspective(45.0f, (float) (width) / (float) (height), // Calculate The Aspect Ratio Of The Window
				1.0f, 100.0f);
		gl.glMatrixMode(GL2.GL_MODELVIEW); // Select The Modelview Matrix
		gl.glLoadIdentity(); // Reset The Modelview Matrix

		arcBall.setBounds((float) width, (float) height); // *NEW* Update mouse bounds for arcball
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		// Start Of User Initialization
		LastRot.setIdentity(); // Reset Rotation
		ThisRot.setIdentity(); // Reset Rotation
		ThisRot.get(matrix);

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glDepthFunc(GL2.GL_LEQUAL); // The Type Of Depth Testing (Less Or Equal)
		gl.glEnable(GL2.GL_DEPTH_TEST); // Enable Depth Testing
		gl.glShadeModel(GL2.GL_FLAT); // Select Flat Shading (Nice Definition Of Objects)
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // Set Perspective Calculations To Most Accurate

		quadratic = glu.gluNewQuadric(); // Create A Pointer To The Quadric Object
		glu.gluQuadricNormals(quadratic, GLU.GLU_SMOOTH); // Create Smooth Normals
		glu.gluQuadricTexture(quadratic, true); // Create Texture Coords

		gl.glEnable(GL2.GL_LIGHT0); // Enable Default Light
		gl.glEnable(GL2.GL_LIGHTING); // Enable Lighting

		gl.glEnable(GL2.GL_COLOR_MATERIAL); // Enable Color Material
	}

	void reset() {
		synchronized (matrixLock) {
			LastRot.setIdentity(); // Reset Rotation
			ThisRot.setIdentity(); // Reset Rotation
		}
	}

	void startDrag(Point MousePt) {
		synchronized (matrixLock) {
			LastRot.set(ThisRot); // Set Last Static Rotation To Last Dynamic One
		}
		arcBall.click(MousePt); // Update Start Vector And Prepare For Dragging
	}

	void drag(Point MousePt) // Perform Motion Updates Here
	{
		Quat4f ThisQuat = new Quat4f();

		arcBall.drag(MousePt, ThisQuat); // Update End Vector And Get Rotation As Quaternion
		synchronized (matrixLock) {
			ThisRot.setRotation(ThisQuat); // Convert Quaternion Into Matrix3fT
			ThisRot.mul(ThisRot, LastRot); // Accumulate Last Rotation Into This One
		}
	}

	void torus(GL2 gl, float MinorRadius, float MajorRadius) // Draw A Torus With Normals
	{
		int i, j;
		int len = 30;
		gl.glBegin(GL2.GL_TRIANGLE_STRIP); // Start A Triangle Strip
		for (i = 0; i < len; i++) // Stacks
		{
			for (j = -1; j < len; j++) // Slices
			{
				float wrapFrac = (j % len) / (float) len;
				double phi = Math.PI * 2.0 * wrapFrac;
				float sinphi = (float) (Math.sin(phi));
				float cosphi = (float) (Math.cos(phi));

				float r = MajorRadius + MinorRadius * cosphi;

				gl.glNormal3d((Math.sin(Math.PI * 2.0 * (i % len + wrapFrac) / (float) len)) * cosphi, sinphi, (Math.cos(Math.PI * 2.0 * (i % len + wrapFrac) / (float) len)) * cosphi);
				gl.glVertex3d((Math.sin(Math.PI * 2.0 * (i % len + wrapFrac) / (float) len)) * r, MinorRadius * sinphi, (Math.cos(Math.PI * 2.0 * (i % len + wrapFrac) / (float) len)) * r);

				gl.glNormal3d((Math.sin(Math.PI * 2.0 * (i + 1 % len + wrapFrac) / (float) len)) * cosphi, sinphi, (Math.cos(Math.PI * 2.0 * (i + 1 % len + wrapFrac) / (float) len)) * cosphi);
				gl.glVertex3d((Math.sin(Math.PI * 2.0 * (i + 1 % len + wrapFrac) / (float) len)) * r, MinorRadius * sinphi, (Math.cos(Math.PI * 2.0 * (i + 1 % len + wrapFrac) / (float) len)) * r);
			}
		}
		gl.glEnd(); // Done Torus
	}

	public void display(GLAutoDrawable drawable) {
		synchronized (matrixLock) {
			ThisRot.get(matrix);
		}

		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // Clear Screen And Depth Buffer
		gl.glLoadIdentity(); // Reset The Current Modelview Matrix
		gl.glTranslatef(-1.5f, 0.0f, -6.0f); // Move Left 1.5 Units And Into The Screen 6.0

		gl.glPushMatrix(); // NEW: Prepare Dynamic Transform
		gl.glMultMatrixf(matrix, 0); // NEW: Apply Dynamic Transform
		gl.glColor3f(0.75f, 0.75f, 1.0f);
		torus(gl, 0.30f, 1.00f);
		gl.glPopMatrix(); // NEW: Unapply Dynamic Transform

		gl.glLoadIdentity(); // Reset The Current Modelview Matrix
		gl.glTranslatef(1.5f, 0.0f, -6.0f); // Move Right 1.5 Units And Into The Screen 7.0

		gl.glPushMatrix(); // NEW: Prepare Dynamic Transform
		gl.glMultMatrixf(matrix, 0); // NEW: Apply Dynamic Transform
		gl.glColor3f(1.0f, 0.75f, 0.75f);
		glu.gluSphere(quadratic, 1.3f, 10, 30);
		gl.glPopMatrix(); // NEW: Unapply Dynamic Transform

		gl.glFlush(); // Flush The GL2 Rendering Pipeline
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}