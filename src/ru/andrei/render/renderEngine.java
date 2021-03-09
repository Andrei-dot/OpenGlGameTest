package ru.andrei.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class renderEngine 
{

	public static void createFrame(String title, int w, int h)
	{
		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(w,h));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void updateFrame()
	{
		Display.update();
	}
	
	public static boolean isClosed()
	{
		return Display.isCloseRequested();
	}
	
	public static void dispose()
	{
		Display.destroy();
	}
	
}
