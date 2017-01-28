package ca.spykill.gameengine.physics;

import ca.spykill.gameengine.PhysicsBody;
import ca.spykill.gameengine.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Me on 1/28/2017.
 */
public class ColliderConvexPoly extends Collider
{
    private List<Vector3> vertices;
    private List<Vector3> normals;
    private List<Float> angles;

    private Vector3 centre;

    /**
     * Creates a convex polygon from the verts provided
     * @param vertices The list of vertices that define the polygon. These MUST define a convex polygon. No checking will be done to ensure this.
     */
    public ColliderConvexPoly(List<Vector3> vertices)
    {
        this.vertices = vertices;
        normals = new ArrayList<>();
        angles = new ArrayList<>();

        this.centre = new Vector3();
        for(int i = 0; i < vertices.size(); i++)
        {
            this.centre.m_add(vertices.get(i));
        }

        float lastAngle = 0;
        for(int i = 0; i < vertices.size(); i++)
        {
            Vector3 itoip1 = vertices.get(i == vertices.size() - 1 ? 0 : i + 1).sub(vertices.get(i));
            Vector3 norm = new Vector3(-itoip1.Y(), itoip1.X()).m_normalize();
            if(norm.dot(vertices.get(i).sub(this.centre)) < 0)
            {
                norm.m_neg();
            }
            normals.add(norm);

            if(i != vertices.size() - 1)
            {
                Vector3 ctoi = vertices.get(i).sub(centre);
                Vector3 ctoip1 = vertices.get(i + 1).sub(centre);
                float theta = (float) ctoi.angle(ctoip1);

                angles.add(theta + lastAngle);
                lastAngle += theta;
            }
        }
    }

    private Vector3 getPointByLerp(float lerp)
    {
        return Vector3.lerp(vertices.get((int)lerp), vertices.get(lerp >= vertices.size() - 1 ? 0 : (int)lerp + 1), lerp % 1);
    }

    private float findVertLerpByAngle(float theta)
    {
        for(int i = 0; i < angles.size(); i++)
        {
            float angle = angles.get(i);
            if(theta < angle)
            {
                float prevAngle = i == 0 ? 0: angles.get(i-1);
                return (i + (theta - prevAngle) / (angle - prevAngle)) % vertices.size();
                //return Vector3.lerp(vertices.get(i), vertices.get(i+1), (theta - prevAngle) / (angle - prevAngle));
            }
        }
        float lastAngle = angles.get(angles.size()-1);
        return (vertices.size() - 1 + (theta - lastAngle) / ((float)(2 * Math.PI) - lastAngle)) % vertices.size();
        //return Vector3.lerp(vertices.get(vertices.size()-1), vertices.get(0), (theta - lastAngle) / ((float)(2 * Math.PI) - lastAngle));
    }

    private static float getHeading(Vector3 axis, Vector3 direction)
    {
        Vector3 perpenAxis = new Vector3(-axis.Y(), axis.X());

        float xDot = (float)direction.dot(axis);
        float yDot = (float)direction.dot(perpenAxis);

        float beta = (float)direction.angle(axis);

        if(xDot > 0)
        {
            if(yDot > 0)
            {
                return beta;
            }
            else
            {
                return (float)(2 * Math.PI) - beta;
            }
        }
        else
        {
            if(yDot > 0)
            {
                return (float)Math.PI - beta;
            }
            else
            {
                return (float)Math.PI + beta;
            }
        }
    }

    @Override
    public CollisionData testCollision(PhysicsBody us, Collider other, PhysicsBody them)
    {
        if(other instanceof ColliderConvexPoly)
        {
            ColliderConvexPoly ccp = (ColliderConvexPoly) other;

            Vector3 axis1 = vertices.get(0).sub(centre);
            Vector3 axis2 = ccp.vertices.get(0).sub(ccp.centre);

            Vector3 direction = ccp.centre.add(them.getGlobalPosition()).sub(centre.add(us.getGlobalPosition()));

            float l1 = findVertLerpByAngle(getHeading(axis1, direction.neg()));
            float l2 = ccp.findVertLerpByAngle(getHeading(axis2, direction.neg()));

            Vector3 p1 = getPointByLerp(l1);
            Vector3 p2 = ccp.getPointByLerp(l2);
            Vector3 p1top2 = p2.add(them.getGlobalPosition()).sub(p1.add(us.getGlobalPosition()));

            if(p1top2.dot(direction) > 0)
            {
                return null;
            }

            double v = (direction.neg().dot(axis1) / direction.Magnitude() / axis1.Magnitude());
            if(v > 1){v = 1;}
            else if(v < -1){v = -1;}
            System.out.println(l1 + " " + axis1 + " " + direction.neg() + " " + axis1.Magnitude() + " " + direction.Magnitude() + " " + Math.acos(v));
            System.out.println(p1 + " " + p2);

            Vector3 normal = normals.get((int)l1);
            return new CollisionData(normal, p1, p1top2.Magnitude());
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean testTrigger(Collider other) {
        return false;
    }
}
