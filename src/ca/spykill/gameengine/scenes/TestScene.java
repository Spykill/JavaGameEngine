package ca.spykill.gameengine.scenes;

import ca.spykill.gameengine.*;
import ca.spykill.gameengine.cameras.MovingCamera;
import ca.spykill.gameengine.components.Gravity;
import ca.spykill.gameengine.components.RenderSprite;
import ca.spykill.gameengine.math.*;
import ca.spykill.gameengine.math.Rectangle;
import ca.spykill.gameengine.physics.ColliderBox;
import ca.spykill.gameengine.physics.ColliderConvexPoly;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestScene extends Scene
{
	@Override
	public void InitialiseScene(Game game)
	{
		MovingCamera cam = new MovingCamera(game, 1f, 1f, new Vector3(game.getWindowWidth(), game.getWindowHeight()));
		game.setCamera(cam);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/test.png"));
		} catch (IOException e) {
		}

		/*
		GameObject obj = new GameObject(null, new Vector3(0,0,0), game);
		RenderSprite rs = new RenderSprite(img);
		rs.pivot = new Vector3(.5f, .5f);
		rs.scale = new Vector3(1, 1);
		obj.addComponent(rs);

		game.addToScene(obj);*/

		PhysicsBody obj = new PhysicsBody(null, new Vector3(), game, 1, 0);
		obj.setPhysicsType(PhysicsBody.PhysicsType.Dynamic);

		obj.colliders.add(new ColliderBox(new Rectangle(-.5f, -.5f, 1, 1)));
		/*
		ArrayList<Vector3> points = new ArrayList<Vector3>(4);
		points.add(new Vector3(0, -.5f));
		points.add(new Vector3(.5f, 0));
		points.add(new Vector3(0, .5f));
		points.add(new Vector3(-.5f, 0));
		obj.colliders.add(new ColliderConvexPoly(points));*/

		RenderSprite rs = new RenderSprite(img);
		rs.pivot = new Vector3(.5f, .5f);
		rs.scale = new Vector3(1, 1);
		obj.addComponent(rs);

		Gravity grav = new Gravity();
		grav.acceleration = new Vector3(0, 2, 0);
		obj.addComponent(grav);

		game.addToScene(obj);

		obj = new PhysicsBody(null, new Vector3(0, 3, 0), game, 1, 0);
		obj.setPhysicsType(PhysicsBody.PhysicsType.Kinematic);
		/*
		points = new ArrayList<>();
		points.add(new Vector3(-4, -.25f));
		points.add(new Vector3(-4, .25f));
		points.add(new Vector3(4, .25f));
		points.add(new Vector3(4, -.25f));
		obj.colliders.add(new ColliderConvexPoly(points));*/
		obj.colliders.add(new ColliderBox(new Rectangle(-4, -.25f, 8, .5f)));

		obj.setVelocity(new Vector3(0, 0, 0));

		rs = new RenderSprite(img);
		rs.pivot = new Vector3(4f, .25f);
		rs.scale = new Vector3(8, .5f);
		rs.tint = Color.red;
		obj.addComponent(rs);

		game.addToScene(obj);
	}
}
