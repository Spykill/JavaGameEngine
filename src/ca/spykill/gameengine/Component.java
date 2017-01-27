package ca.spykill.gameengine;

import java.awt.*;

/**
 * Created by Andriy on 2017-01-26.
 */
public abstract class Component
{
    private GameObject owner;

    public GameObject getOwner()
    {
        return owner;
    }

    public void setOwner(GameObject owner)
    {
        this.owner = owner;
    }

    public void update(float deltaTime){}
    public void render(Graphics g, Camera cam, float interpolation){}
}
