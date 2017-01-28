package ca.spykill.gameengine;

import java.util.ArrayList;

import ca.spykill.gameengine.math.Vector3;
import ca.spykill.gameengine.physics.Collider;

public class PhysicsBody extends GameObject
{
    public enum PhysicsType
    {
        Kinematic, Trigger, Dynamic
    }

    private PhysicsType type;

    private float mass;
    private float restitution;

    private float inv_mass;

    private Vector3 velocity;

    public ArrayList<Collider> colliders;

    public PhysicsBody(GameObject parent, Vector3 localPosition, Game game, float mass, float restitution)
    {
        super(parent, localPosition, game);
        this.mass = mass;
        if(mass == 0)
        {
            this.inv_mass = 0;
        }
        else
        {
            this.inv_mass = 1.0f / mass;
        }
        this.restitution = restitution;
        this.type = PhysicsType.Dynamic;
        this.velocity = new Vector3();

        colliders = new ArrayList<Collider>();
    }

    public void tryCollision(PhysicsBody body)
    {
        if(type == PhysicsType.Kinematic)
        {
            if (body.getPhysicsType() == PhysicsType.Dynamic)
            {
                // Do the collision here
                handleCollision(body);
            }
            else if(body.getPhysicsType() == PhysicsType.Trigger)
            {
                if(checkTrigger(body))
                {

                }
            }
        }
        else if(type == PhysicsType.Dynamic)
        {
            if(body.getPhysicsType() == PhysicsType.Trigger)
            {
                if(checkTrigger(body))
                {

                }
            }
            else
            {
                handleCollision(body);
            }
        }
        else if(type == PhysicsType.Trigger)
        {
            if (checkTrigger(body))
            {

            }
            //
        }
    }

    public void doVelocityStep(float deltaTime)
    {
        this.localPosition.m_add(velocity.scale(deltaTime));
    }

    private void handleCollision(PhysicsBody body)
    {
        if(mass == 0 && body.mass == 0)
        {
            return;
        }

        float myMass = type == PhysicsType.Kinematic ? 0 : mass;
        float theirMass = body.type == PhysicsType.Kinematic ? 0 : body.mass;

        for(Collider collider : colliders)
        {
            for(Collider other : body.colliders)
            {
                Collider.CollisionData cd = collider.testCollision(this, other, body);

                if (cd != null)
                {
                    float massTotal = mass + body.mass;
                    localPosition.m_add(cd.normal.scale(cd.penetration * myMass / massTotal));
                    body.localPosition.m_add(cd.normal.scale(-cd.penetration * theirMass / massTotal));
                    Vector3 relVel = body.velocity.sub(velocity);
                    float velAlongNormal = (float)relVel.dot(cd.normal);

                    if(velAlongNormal < 0)
                    {
                        return;
                    }

                    float e = Math.min(restitution, body.restitution);
                    float j = (1 + e) * velAlongNormal;
                    if(myMass != 0 && theirMass != 0)
                    {
                        j /= myMass * theirMass;
                    }
                    Vector3 impulse = cd.normal.scale(j);
                    velocity.m_add(impulse.scale(myMass));
                    body.velocity.m_add(impulse.scale(-theirMass));
                }
            }
        }
    }

    private boolean checkTrigger(PhysicsBody body)
    {
        for(Collider collider : colliders)
        {
            for (Collider other : body.colliders)
            {
                if(collider.testTrigger(other))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public PhysicsType getPhysicsType()
    {
        return type;
    }

    public void setPhysicsType(PhysicsType newType)
    {
        boolean r = getGame().clearPhysicsBodyFromLists(this);
        this.type = newType;
        if(r)
        {
            getGame().addPhysicsBodyToLists(this);
        }
    }

    public Vector3 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector3 velocity)
    {
        this.velocity = velocity;
    }

    public void accelerate(Vector3 acceleration)
    {
        this.velocity.m_add(acceleration);
    }

    public void accelerate(Vector3 acceleration, float deltaTime)
    {
        this.velocity.m_add(acceleration.scale(deltaTime));
    }

    public void applyForce(Vector3 force)
    {
        this.velocity.m_add(force.scale(inv_mass));
    }

    public void applyForce(Vector3 force, float deltaTime)
    {
        this.velocity.m_add(force.scale(inv_mass * deltaTime));
    }
}
