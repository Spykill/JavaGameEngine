package ca.spykill.gameengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import ca.spykill.gameengine.scenes.TestScene;

public class Main extends JPanel
{
	JFrame window;
	Input input;
	
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
		
		setPreferredSize(new Dimension(windowWidth, windowHeight));
		//window.pack();
		window.setResizable(false);
		
		input = new Input();
		System.out.print(window.getWidth());
		input.setupInput(window, 256, 3, this.getX(), this.getBounds().y);
		
		gameTime = System.nanoTime() / 1000000000.0f;
		
		gameObjects = new ArrayList<GameObject>();
		
		currentScene = new TestScene();
		currentScene.InitialiseScene(this);
		
		gameLoop();
	}
	
	public void gameLoop()
	{
		while(true)
		{
			float newTime = System.nanoTime() / 1000000000.0f;
			float deltaTime = (newTime - gameTime);
			
			for(int i = 0; i < deltaTime / updateTime; i++)
			{
				deltaTime -= updateTime;
				gameTime += updateTime;
				update();
			}
			
			currentInterpolation = deltaTime / updateTime;
			repaint();
			input.flush();
		}
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

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 0, 25));
		g.drawString(input.getMouseX() + " | ", 15, 30);
		//System.out.println("Here");
	}

	public static void main(String[] args)
	{
		new Main();
	}

}
