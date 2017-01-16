package ca.spykill.gameengine;

import java.awt.Graphics;

import javax.swing.*;

public abstract class GameObject
{
	public GameObject()
	{
		
	}
	
	public abstract void update(float deltaTime);
	public abstract void render(Graphics g, float interpolation);
}
