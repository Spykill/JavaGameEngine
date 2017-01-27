package ca.spykill.gameengine;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.datatransfer.FlavorTable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.security.auth.x500.X500Principal;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import ca.spykill.gameengine.math.Vector3;

public class Input implements KeyListener, MouseWheelListener, MouseInputListener
{
	private boolean[] keys;
	private boolean[] keysDown;
	private boolean[] keysUp;
	private boolean[] mouse;
	private boolean[] mouseDown;
	private boolean[] mouseUp;
	private float mouseWheel;
	private float mouseX, mouseY;
	private float mouseDeltaX, mouseDeltaY;
	
	private int mouseOffsetX, mouseOffsetY;

	/**
	 * Sets up the input on a specified frame with a specified key and mouse
	 * buffer size
	 * 
	 * @param frame
	 *            The frame to detect inputs on
	 * @param keyBufferSize
	 *            The number of keys to be detected. It is based on ASCII values
	 * @param mouseBufferSize
	 *            The number of mouse buttons to be detected. It is based on the
	 *            id of mouse buttons
	 */
	public void setupInput(Component component, int keyBufferSize, int mouseBufferSize, int mouseOffsetX, int mouseOffsetY)
	{
		component.addKeyListener(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);

		this.mouseOffsetX = mouseOffsetX;
		this.mouseOffsetY = mouseOffsetY;

		keys = new boolean[keyBufferSize];
		keysDown = new boolean[keyBufferSize];
		keysUp = new boolean[keyBufferSize];

		mouse = new boolean[mouseBufferSize];
		mouseDown = new boolean[mouseBufferSize];
		mouseUp = new boolean[mouseBufferSize];
	}
	
	public void flush()
	{
		for(int i = 0; i < keys.length; i++)
		{
			keysDown[i] = false;
			keysUp[i] = false;
		}
		for(int i = 0; i < mouse.length; i++)
		{
			mouseDown[i] = false;
			mouseUp[i] = false;
		}
		
		mouseDeltaX = 0;
		mouseDeltaY = 0;
		mouseWheel = 0;
	}
	
	public boolean isKeyPressed(int id)
	{
		if(id < keys.length)
		{
			return keys[id];
		}
		return false;
	}
	
	public boolean isKeyDown(int id)
	{
		if(id < keys.length)
		{
			return keysDown[id];
		}
		return false;
	}
	
	public boolean isKeyUp(int id)
	{
		if(id < keys.length)
		{
			return keysUp[id];
		}
		return false;
	}
	
	public boolean isMousePressed(int id)
	{
		if(id < mouse.length)
		{
			return mouse[id];
		}
		return false;
	}
	
	public boolean isMouseDown(int id)
	{
		if(id < mouse.length)
		{
			return mouseDown[id];
		}
		return false;
	}
	
	public boolean isMouseUp(int id)
	{
		if(id < mouse.length)
		{
			return mouseUp[id];
		}
		return false;
	}

	public float getMouseWheel()
	{
		return mouseWheel;
	}

	public float getMouseX()
	{
		return mouseX;
	}

	public float getMouseY()
	{
		return mouseY;
	}

	public float getMouseDeltaX()
	{
		return mouseDeltaX;
	}

	public float getMouseDeltaY()
	{
		return mouseDeltaY;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() < mouse.length)
		{
			mouse[e.getButton()] = true;
			mouseDown[e.getButton()] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() < mouse.length)
		{
			mouse[e.getButton()] = false;
			mouseUp[e.getButton()] = true;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseDeltaX = e.getX() - mouseOffsetX - mouseX;
		mouseDeltaY = e.getY() - mouseOffsetY - mouseY;
		mouseX = e.getX() - mouseOffsetX;
		mouseY = e.getY() - mouseOffsetY;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseDeltaX = e.getX() - mouseOffsetX - mouseX;
		mouseDeltaY = e.getY() - mouseOffsetY - mouseY;
		mouseX = e.getX() - mouseOffsetX;
		mouseY = e.getY() - mouseOffsetY;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		mouseWheel += e.getWheelRotation();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() < keys.length)
		{
			keys[e.getKeyCode()] = true;
			keysDown[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() < keys.length)
		{
			keys[e.getKeyCode()] = false;
			keysUp[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}
}
