package ca.spykill.gameengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

import ca.spykill.gameengine.scenes.TestScene;

public class Main extends JPanel
{
	private JFrame window;
	private Input input;

	private int windowWidth;
	private int windowHeight;

	private int desiredUpdatesPerSecond;

	private Game game;

	private Main()
	{
		windowWidth = 800;
		windowHeight = 600;
		desiredUpdatesPerSecond = 30;
		
		window = new JFrame("This is a title");
		window.setVisible(true);
		window.setSize(windowWidth,  windowHeight);
		window.setContentPane(this);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setPreferredSize(new Dimension(windowWidth, windowHeight));
		//window.pack();
		window.setResizable(false);
		//setDoubleBuffered(true);
		
		input = new Input();
		input.setupInput(window, 256, 3, this.getX(), this.getBounds().y);

        game = new Game(desiredUpdatesPerSecond, input, this);

        game.startScene(new TestScene());

        game.gameLoop();
	}

    @Override
    public void paint(Graphics g)
    {
        //super.paint(g);
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, windowWidth, windowHeight);
        //game.render(g);
    }

	public int getWindowWidth()
	{
		return windowWidth;
	}

	public int getWindowHeight()
	{
		return windowHeight;
	}

	public static void main(String[] args)
	{
		new Main();
	}

}
