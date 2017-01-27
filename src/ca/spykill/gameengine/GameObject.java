package ca.spykill.gameengine;

import ca.spykill.gameengine.math.Vector3;

import java.awt.*;
import java.util.ArrayList;

public class GameObject
{
	private Game game;

	private GameObject parent;
	private ArrayList<GameObject> children;

	public Vector3 localPosition;

	private ArrayList<Component> components;

	public GameObject(GameObject parent, Vector3 localPosition, Game game)
	{
		if(parent != null)
		{
			parent.addChild(this);
		}
		this.localPosition = localPosition;
		this.game = game;

		components = new ArrayList<Component>();
		children = new ArrayList<GameObject>();
	}

	public void update(float deltaTime)
	{
		for(int i = 0; i < components.size(); i++)
		{
			components.get(i).update(deltaTime);
		}

		for(int i = 0; i < children.size(); i++)
		{
			children.get(i).update(deltaTime);
		}
	}

	public void render(Graphics g, Camera cam, float interpolation)
	{
		for(int i = 0; i < components.size(); i++)
		{
			components.get(i).render(g, cam, interpolation);
		}

		for(int i = 0; i < children.size(); i++)
		{
			children.get(i).render(g, cam, interpolation);
		}
	}

	public void addChild(GameObject obj)
	{
		obj.parent = this;
		children.add(obj);
	}

	public GameObject addComponent(Component component)
	{
		components.add(component);
		component.setOwner(this);
		return this;
	}

	public Game getGame()
	{
		return game;
	}

	public Vector3 getGlobalPosition()
	{
		if(this.parent == null)
		{
			return localPosition.clone();
		}
		return parent.getGlobalPosition().add(localPosition);
	}
}
