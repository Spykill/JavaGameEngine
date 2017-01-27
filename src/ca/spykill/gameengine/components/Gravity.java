package ca.spykill.gameengine.components;

import ca.spykill.gameengine.Component;
import ca.spykill.gameengine.PhysicsBody;
import ca.spykill.gameengine.PhysicsComponent;
import ca.spykill.gameengine.math.Vector3;

/**
 * Created by Andriy on 2017-01-27.
 */
public class Gravity extends PhysicsComponent
{
    public Vector3 acceleration;

    @Override
    public void update(float deltaTime)
    {
        ownerPhysicsBody.accelerate(acceleration, deltaTime);
    }
}
