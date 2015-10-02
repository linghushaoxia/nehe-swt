package demos.nehe.lesson27;

/*--.          .-"-.
 /   o_O        / O o \
 \_  (__\       \_ v _/
 //   \\        //   \\
 ((     ))      ((     ))
 ��������������--""---""--����--""---""--��������������������������
 �                 |||            |||                             �
 �                  |              |                              �
 �                                                                �
 � Programmer:Abdul Bezrati                                       �
 � Program   :Nehe's 27th lesson port to JOGL                     �
 � Comments  :None                                                �
 �    _______                                                     �
 �  /` _____ `\;,    abezrati@hotmail.com                         �
 � (__(^===^)__)';,                                 ___           �
 �   /  :::  \   ,;                               /^   ^\         �
 �  |   :::   | ,;'                              ( �   � )        �
 ���'._______.'`��������������������������� --�oOo--(_)--oOo�--��*/

import demos.common.GLDisplay;

import javax.media.opengl.GLCapabilities;

/**
 * @author Abdul Bezrati
 */
public class Lesson27 {
	public static void main(String[] args) {
		GLCapabilities capabilities = new GLCapabilities();
		capabilities.setStencilBits(1);

		GLDisplay neheGLDisplay = GLDisplay.createGLDisplay("Lesson 27: Shadows", capabilities);
		Renderer renderer = new Renderer();
		InputHandler inputHandler = new InputHandler(renderer, neheGLDisplay);
		neheGLDisplay.addGLEventListener(renderer);
		neheGLDisplay.addKeyListener(inputHandler);
		neheGLDisplay.start();
	}
}
