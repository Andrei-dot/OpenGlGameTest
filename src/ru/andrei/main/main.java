package ru.andrei.main;

import ru.andrei.render.renderEngine;

public class main {

	public static void main(String[] args)
	{
		renderEngine.createFrame("3D LWGJL Test Game", 1280, 720);
		while(!renderEngine.isClosed())
		{
			renderEngine.updateFrame();
		}
	}
	
}
