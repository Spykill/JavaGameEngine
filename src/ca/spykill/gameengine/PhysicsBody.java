package ca.spykill.gameengine;

import java.util.ArrayList;

import ca.spykill.gameengine.math.Vector3;
import ca.spykill.gameengine.physics.Collider;

public abstract class PhysicsBody extends GameObject
{
    private boolean isKinematic = false;
    private boolean isTrigger = false;
    private Vector3 velocity;

    public ArrayList<Collider> colliders;

    public PhysicsBody()
    {
        // TODO Auto-generated constructor stub
    }

    public boolean isKinematic()
    {
        return isKinematic;
    }

    public void setKinematic(boolean isKinematic)
    {
        this.isKinematic = isKinematic;
    }

    public boolean isTrigger()
    {
        return isTrigger;
    }

    public void setTrigger(boolean trigger)
    {
        isTrigger = trigger;
    }
}
