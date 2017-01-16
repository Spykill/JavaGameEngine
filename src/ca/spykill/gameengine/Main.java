package ca.spykill.gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import ca.spykill.gameengine.scenes.TestScene;

public class Main extends JPanel
{
	JFrame window;
	int windowWidth;
	int windowHeight;
	
	int desiredUpdatesPerSecond;

	float updateTime;
	
	float time;
	float gameTime;
	
	float currentInterpolation;
	
	public ArrayList<GameObject> gameObjects;
	
	public Scene currentScene;
	
	public Main()
	{
		windowWidth = 800;
		windowHeight = 600;
		desiredUpdatesPerSecond = 30;
		
		updateTime = 1.0f / desiredUpdatesPerSecond;
		
		window = new JFrame("This is a title");
		window.setVisible(true);
		window.setSize(windowWidth,  windowHeight);
		window.setContentPane(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		time = System.nanoTime();
		
		gameObjects = new ArrayList<GameObject>();
		
		currentScene = new TestScene();
		currentScene.InitialiseScene(this);
	}
	
	public void gameLoop()
	{
		float newTime = System.nanoTime();
		float deltaTime = (newTime - gameTime) / 0.000001f;
		
		for(int i = 0; i < deltaTime / updateTime; i++)
		{
			deltaTime -= updateTime;
			update();
		}
		
		currentInterpolation = deltaTime / updateTime;
		repaint();
	}
	
	public void update()
	{
		doPhysicsUpdate();
		
		for(int i = 0; i < gameObjects.size(); i++)
		{
			gameObjects.get(i).update(updateTime);
		}
	}
	
	public void doPhysicsUpdate()
	{
		
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		for(int i = 0; i < gameObjects.size(); i++)
		{
			gameObjects.get(i).render(g, currentInterpolation);
		}
		
	}

	public static void main(String[] args)
	{
		new Main();
	}

}
