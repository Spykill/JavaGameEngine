package ca.spykill.gameengine;

import ca.spykill.gameengine.math.Rectangle;
import ca.spykill.gameengine.math.Vector3;

/**
 * Created by Andriy on 2017-01-26.
 */
public abstract class Camera
{
    public static final float GAME_UNIT = 64;

    protected static Rectangle makeViewport(Vector3 centre, Vector3 screenDimensions, float scale)
    {
        return new Rectangle(centre.X() - screenDimensions.X() / GAME_UNIT * .5f / scale, centre.Y() - screenDimensions.Y() / GAME_UNIT * .5f / scale, screenDimensions.X() / GAME_UNIT / scale, screenDimensions.Y() / GAME_UNIT / scale);
    }

    private Game game;

    public Camera(Game game)
    {
        this.game = game;
    }

    public abstract float getScale(float interpolation);
    public abstract Vector3 getOffset(float interpolation);
    public abstract Rectangle getViewport(float interpolation);

    public void update(float deltaTime) {}
    public void render(float currentInterpolation) {}

    public Game getGame()
    {
        return game;
    }
}
