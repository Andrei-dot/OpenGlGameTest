package ru.andrei.main.render;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

import ru.andrei.main.math.Vector3f;

public class RenderManager {

	// Vertex Args : glVertex3f(x,hauteur,y);
	
	// Correspond au nbr de textures pouvant �tres mises en longueur et en largeur sur le spritesheet
	
	private static float env_w = 4f;
	private static float env_h = 4f;

	// offset
	
	private static float offs = 0.0f;
	
	public static void setFloor(float x, float z, Vector3f color, int texture)
	{
		glColor3f(color.getX(),color.getY(),color.getZ());
		
		// xOffset, permet de "naviguer" sur le spritesheet
		
		float xo = texture % (int)env_w;
		float yo = texture / (int)env_h;

		/*
		 * Permet de draw le carré au sol
		 */
		
		glTexCoord2f((0 + xo) / env_w + offs ,(0 + yo) / env_h + offs); glVertex3f(x + 1, 0, z);
		glTexCoord2f((1 + xo) / env_w - offs ,(0 + yo) / env_h + offs); glVertex3f(x, 0, z);
		glTexCoord2f((1 + xo) / env_w - offs ,(1 + yo) / env_h - offs); glVertex3f(x, 0, z + 1);
		glTexCoord2f((0 + xo) / env_w + offs ,(1 + yo) / env_h - offs); glVertex3f(x + 1, 0, z + 1);
	}
	
	public static void setCeiling(float x, float z, Vector3f color, int texture)
	{ 
		glColor3f(color.getX(),color.getY(),color.getZ());
		
		float xo = texture % (int)env_w;
		float yo = texture /(int)env_h;
		
		/*
		 * Permet de draw le carré au plafond 
		 */

		glTexCoord2f((0 + xo) / env_w + offs ,(0 + yo) / env_h + offs); glVertex3f(x, 3, z);
		glTexCoord2f((1 + xo) /  env_w - offs,(0 + yo) / env_h + offs); glVertex3f(x + 1, 3, z);
		glTexCoord2f((1 + xo) / env_w - offs ,(1 + yo) / env_h - offs); glVertex3f(x + 1, 3, z + 1);
		glTexCoord2f((0 + xo) / env_w + offs ,(1 + yo) / env_h - offs); glVertex3f(x, 3, z + 1);
	}
	
	public static void setWall(float x0, float z0, float x1, float z1, Vector3f color, int texture)
	{
		glColor3f(color.getX(), color.getY(), color.getZ());
		
		float xo = texture % (int)env_w;
		float yo = texture / (int)env_h;
		
		glTexCoord2f((0 + xo) / env_w + offs ,(0 + yo) / env_h + offs); glVertex3f(x0,0,z0);
		glTexCoord2f((1 + xo) / env_w + offs ,(0 + yo) / env_h + offs); glVertex3f(x1,0,z1);
		glTexCoord2f((1 + xo) / env_w - offs ,(1 + yo) / env_h - offs); glVertex3f(x1,3,z1);
		glTexCoord2f((0 + xo) / env_w - offs ,(1 + yo) / env_h - offs); glVertex3f(x0,3,z0);
		
		/*
		 *  3 dans les deux derniers vertex en 2nd argument est la hauteur
		 */
	}
	
	public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3)
	{
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(1F);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		drawGradientRect(x, y, x2, y2, col2, col3);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
	
	public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) 
	{
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		float f4 = (float)(col2 >> 24 & 0xFF) / 255F;
		float f5 = (float)(col2 >> 16 & 0xFF) / 255F;
		float f6 = (float)(col2 >> 8 & 0xFF) / 255F;
		float f7 = (float)(col2 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}
