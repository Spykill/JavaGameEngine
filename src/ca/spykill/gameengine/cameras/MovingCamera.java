package ca.spykill.gameengine.cameras;

import ca.spykill.gameengine.Camera;
import ca.spykill.gameengine.Game;
import ca.spykill.gameengine.Input;
import ca.spykill.gameengine.math.Rectangle;
import ca.spykill.gameengine.math.Vector3;

/**
 * Created by Andriy on 2017-01-26.
 */
public class MovingCamera extends Camera
{
    public float scale;
    public float moveSpeed;
    public Vector3 screenDimensions;

    private Vector3 lastOffset;
    private Vector3 offset;
    private Rectangle viewport;

    public MovingCamera(Game game, float scale, float moveSpeed, Vector3 screenDimensions)
    {
        super(game);
        this.scale = scale;
        this.moveSpeed = moveSpeed;
        this.screenDimensions = screenDimensions;

        lastOffset = new Vector3();
        offset = new Vector3(0, 0);

        viewport = new Rectangle(-this.screenDimensions.X() / GAME_UNIT*.5f, -this.screenDimensions.Y() / GAME_UNIT*.5f, this.screenDimensions.X() / GAME_UNIT, this.screenDimensions.Y() / GAME_UNIT);
    }

    @Override
    public float getScale(float interpolation)
    {
        return scale;
    }

    @Override
    public Vector3 getOffset(float interpolation)
    {
        return Vector3.lerp(lastOffset, offset, interpolation);
    }

    @Override
    public Rectangle getViewport(float interpolation)
    {
        return viewport.clone().translate(Vector3.lerp(new Vector3(), offset.sub(lastOffset), interpolation));
    }

    @Override
    public void update(float deltaTime)
    {
        lastOffset = offset.clone();
        Input in = getGame().getInput();

        Vector3 movement = new Vector3();
        if(in.isKeyPressed('A'))
        {
            double d = movement.X() - moveSpeed * deltaTime;
            movement.X(d);
        }
        if(in.isKeyPressed('D'))
        {
            double d = movement.X() + moveSpeed * deltaTime;
            movement.X(d);
        }
        if(in.isKeyPressed('W'))
        {
            double d = movement.Y() - moveSpeed * deltaTime;
            movement.Y(d);
        }
        if(in.isKeyPressed('S'))
        {
            double d = movement.Y() + moveSpeed * deltaTime;
            movement.Y(d);
        }
        offset.m_add(movement);
        viewport = new Rectangle(offset.X() - screenDimensions.X() / GAME_UNIT * .5f / scale, offset.Y() - screenDimensions.Y() / GAME_UNIT * .5f / scale, screenDimensions.X() / GAME_UNIT / scale, screenDimensions.Y() / GAME_UNIT / scale);
    }
}
