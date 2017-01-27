package ca.spykill.gameengine.physics;

import ca.spykill.gameengine.PhysicsBody;
import ca.spykill.gameengine.math.Vector3;

public abstract class Collider
{

	public Collider()
	{
		// TODO Auto-generated constructor stub
	}

	public abstract CollisionData testCollision(PhysicsBody us, Collider other, PhysicsBody them);

	public abstract boolean testTrigger(Collider other);

	public class CollisionData
	{
		public Vector3 normal;
		public double penetration;

		public CollisionData(Vector3 normal, double penetration)
		{
			this.normal = normal;
			this.penetration = penetration;
		}
	}
}
