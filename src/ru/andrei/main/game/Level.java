package ru.andrei.main.game;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.andrei.main.blocks.Block;
import ru.andrei.main.blocks.SolidBlock;
import ru.andrei.main.math.Vector3f;
import ru.andrei.main.render.RenderManager;
import ru.andrei.main.render.Texturing;

public class Level {

	int renderingList;

	Block[] blocks;
	private int width, height;

	public Level(String level) {
		loadLevel(level);
	}

	public void loadLevel(String level) {
		BufferedImage map = null;

		try {
			map = ImageIO.read(Level.class.getResource("/" + level + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		width = map.getWidth();
		height = map.getHeight();
		int[] pixels = new int[width * height];
		map.getRGB(0, 0, width, height, pixels, 0, width);

		/*
		 * Chaque blocs est égal à un pixel de l'image (img en question = map.png)
		 */
		blocks = new Block[pixels.length];
		/*
		 * Remplissage des blocs
		 */
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				// Permet de naviguer dans l'image par rapport à un x & y
				int i = x + y * width;
				if (pixels[i] == 0xFF000000) { blocks[i] = new Block(x, y); }

				if (pixels[i] == 0xFFffffff) { blocks[i] = new SolidBlock(x, y); }
			} 
		}
		loadRendering();
	}

	private void loadRendering() {
		renderingList = glGenLists(1);

		glNewList(renderingList, GL_COMPILE);
		glBegin(GL_QUADS);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Block block = getBlock(x, y);
				
				if (!block.isSolid()) {
					RenderManager.setFloor(x, y, new Vector3f(1, 1, 1), 1);
					RenderManager.setCeiling(x, y, new Vector3f(1, 1, 1), 2);
				} 
				
				Block left = getBlock(x + 1, y);
				Block down = getBlock(x, y + 1);

				if(block.isSolid())
				{
					if(!left.isSolid())
					{
						// RenderManager.setWall(x + 1, y + 1, x + 1, y, new Vector3f(0.9f,0.9f,0.9f));
						RenderManager.setWall(x + 1, y + 1, x + 1, y, new Vector3f(0.9f,0.9f,0.9f), 0);						
					}					
					if(!down.isSolid())
					{
						RenderManager.setWall(x, y + 1, x + 1, y + 1, new Vector3f(0.8f,0.8f,0.8f), 0);						
					}
				} else {
					if(left.isSolid())
					{
						RenderManager.setWall(x +1, y, x + 1, y + 1, new Vector3f(0.9f,0.9f,0.9f), 0);						
					}					
					if(down.isSolid())
					{
						RenderManager.setWall(x + 1, y + 1, x, y + 1, new Vector3f(0.8f,0.8f,0.8f), 0);						
					}
				}
			}
		}
		
        // RenderManager.drawGradientBorderedRect(10,0,200,300,0x66F0F8FF ,0x66F0F8FF ,0x66F0F8FF ,0x66F0F8FF );
		
		glEnd();
		glEndList();
		Texturing.env.bind();
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return new SolidBlock(x, y);
		}

		return blocks[x + y * width];
	}

	public void update() {

	}

	public void render() 
	{
		glCallList(renderingList);
	}
}
