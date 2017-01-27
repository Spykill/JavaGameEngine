package ca.spykill.gameengine;

/**
 * Created by Andriy on 2017-01-27.
 */
public class PhysicsComponent extends Component
{
    protected PhysicsBody ownerPhysicsBody;

    @Override
    public void setOwner(GameObject obj)
    {
        super.setOwner(obj);
        try
        {
            ownerPhysicsBody = (PhysicsBody)obj;
        }
        catch(ClassCastException e)
        {
            throw new RuntimeException("PhysicsComponent added to non-physics body");
        }
    }
}
