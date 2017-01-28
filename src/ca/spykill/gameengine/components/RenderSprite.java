package ca.spykill.gameengine.components;

import ca.spykill.gameengine.Camera;
import ca.spykill.gameengine.Component;
import ca.spykill.gameengine.math.*;
import ca.spykill.gameengine.math.Rectangle;

import java.awt.*;

/**
 * Created by Andriy on 2017-01-26.
 */
public class RenderSprite extends Component
{
    public Image sprite;

    public Vector3 pivot;
    public Vector3 scale;

    public Color tint;

    private Vector3 lastUpdatePos;
    private Vector3 nowPos;

    public RenderSprite(Image sprite)
    {
        this.sprite = sprite;
        tint = Color.white;
        scale = new Vector3(1,1);
        pivot = new Vector3(0, 0);

        lastUpdatePos = new Vector3();
        nowPos = new Vector3();
    }

    @Override
    public void update(float deltaTime)
    {
        lastUpdatePos = nowPos;
        nowPos = getOwner().getGlobalPosition();
        if(lastUpdatePos == null)
        {
            lastUpdatePos = nowPos;
        }
    }

    @Override
    public void render(Graphics g, Camera cam, float interpolation)
    {
        Vector3 pos = Vector3.lerp(lastUpdatePos, nowPos, interpolation);

        Vector3 tl = pos.sub(pivot);
        Vector3 br = tl.add(scale);

        Vector3 camOffset = cam.getOffset(interpolation);
        float camScale = cam.getScale(interpolation);
        Rectangle camViewport = cam.getViewport(interpolation);

        tl.m_sub(camOffset);
        br.m_sub(camOffset);

        tl.m_add(new Vector3(camViewport.getW() * .5f, camViewport.getH() * .5f));
        br.m_add(new Vector3(camViewport.getW() * .5f, camViewport.getH() * .5f));
        tl.m_scale(Camera.GAME_UNIT);
        br.m_scale(Camera.GAME_UNIT);

        //g.drawImage(sprite, (int)(tl.X() * Camera.GAME_UNIT), (int)(tl.Y() * Camera.GAME_UNIT), (int)(br.X() * Camera.GAME_UNIT), (int)(br.Y() * Camera.GAME_UNIT), 0, 0, sprite.getWidth(null), sprite.getHeight(null), tint, null);
        g.drawImage(sprite, (int)tl.X(), (int)tl.Y(), (int)br.X(), (int)br.Y(), 0,0, sprite.getWidth(null), sprite.getHeight(null), null);
    }
}
