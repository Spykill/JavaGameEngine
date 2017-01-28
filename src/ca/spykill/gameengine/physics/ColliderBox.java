package ca.spykill.gameengine.physics;

import ca.spykill.gameengine.PhysicsBody;
import ca.spykill.gameengine.math.Rectangle;
import ca.spykill.gameengine.math.Vector3;

public class ColliderBox extends Collider
{
	private Rectangle rectangle;

	public ColliderBox(Rectangle rectangle)
	{
		this.rectangle = rectangle;
	}

	@Override
	public CollisionData testCollision(PhysicsBody us, Collider other, PhysicsBody them)
	{
		if(other instanceof ColliderBox)
		{
			ColliderBox otherBox = (ColliderBox) other;
			Rectangle ourRect = rectangle.clone().translate(us.getGlobalPosition());
			Rectangle theirRect = otherBox.rectangle.clone().translate(them.getGlobalPosition());

			Vector3 ourCentre = ourRect.getCentre();
			Vector3 theirCenter = theirRect.getCentre();
			Vector3 toThemV = theirCenter.sub(ourCentre);
			double extentX = (ourRect.getW() + theirRect.getW()) * 0.5f;
			double extentY = (ourRect.getH() + theirRect.getH()) * 0.5f;

			double xPen = extentX - Math.abs(toThemV.X());
			double yPen = extentY - Math.abs(toThemV.Y());

			if(xPen < 0 || yPen < 0)
			{
				return null;
			}

			if(us == them)
			{
				System.out.println("OMG SAME");
			}

			if(xPen < yPen)
			{
				if(toThemV.X() < 0)
				{
					return new CollisionData(new Vector3(1, 0), new Vector3(), xPen);
				}
				else
				{
					return new CollisionData(new Vector3(-1, 0), new Vector3(), xPen);
				}
			}
			else
			{
				if(toThemV.Y() < 0)
				{
					return new CollisionData(new Vector3(0, 1), new Vector3(), yPen);
				}
				else
				{
					return new CollisionData(new Vector3(0, -1), new Vector3(), yPen);
				}
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean testTrigger(Collider other)
	{
		if(other instanceof ColliderBox)
		{
			ColliderBox otherBox = (ColliderBox)other;
			return otherBox.rectangle.intersects(rectangle);
		}
		else
		{
			return false;
		}
	}
}
