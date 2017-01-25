package ca.spykill.gameengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
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

	Game game;
	
	public Main()
	{
		windowWidth = 800;
		windowHeight = 600;
		desiredUpdatesPerSecond = 30;
		
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

        game = new Game(desiredUpdatesPerSecond, input, this);

        game.StartScene(new TestScene());

        game.gameLoop();
	}

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, windowWidth, windowHeight);
        game.render(g);
    }

	public static void main(String[] args)
	{
		new Main();
	}

}
