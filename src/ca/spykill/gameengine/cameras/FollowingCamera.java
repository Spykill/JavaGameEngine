package ca.spykill.gameengine.cameras;

import ca.spykill.gameengine.Camera;
import ca.spykill.gameengine.Game;
import ca.spykill.gameengine.math.Rectangle;
import ca.spykill.gameengine.math.Vector3;

/**
 * Created by Andriy on 2017-01-27.
 */
public class FollowingCamera extends Camera
{
    public FollowingCamera(Game game)
    {
        super(game);
    }

    @Override
    public float getScale(float interpolation)
    {
        return 0;
    }

    @Override
    public Vector3 getOffset(float interpolation)
    {
        return null;
    }

    @Override
    public Rectangle getViewport(float interpolation)
    {
        return null;
    }
}
