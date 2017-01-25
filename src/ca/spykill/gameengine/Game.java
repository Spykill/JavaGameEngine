package ca.spykill.gameengine;

import ca.spykill.gameengine.scenes.TestScene;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Andriy on 2017-01-25.
 */
public class Game
{
    private Main main;
    private Input input;

    private float updateTime;

    private float time;
    private float gameTime;

    private float currentInterpolation;

    public ArrayList<GameObject> gameObjects;
    public ArrayList<PhysicsBody> physicsBodies;

    public ArrayList<PhysicsBody> kinematic;
    public ArrayList<PhysicsBody> dynamic;

    public ArrayList<PhysicsBody> trigger;

    public Scene currentScene;

    public Game(float FPS, Input input, Main main)
    {
        this.updateTime = 1.0f / FPS;
        this.input = input;
        this.main = main;

        gameTime = System.nanoTime() / 1000000000.0f;

        gameObjects = new ArrayList<GameObject>();
        physicsBodies = new ArrayList<PhysicsBody>();
        kinematic = new ArrayList<PhysicsBody>();
        dynamic = new ArrayList<PhysicsBody>();
        trigger = new ArrayList<PhysicsBody>();
    }

    public void StartScene(Scene scene)
    {
        scene.InitialiseScene(this);
        currentScene = scene;
    }

    public void AddToScene(GameObject object)
    {
        gameObjects.add(object);
        if(object instanceof PhysicsBody)
        {
            PhysicsBody pb = (PhysicsBody)object;
            physicsBodies.add(pb);
            if(pb.isKinematic())
            {
                kinematic.add(pb);
            }
            else
            {
                dynamic.add(pb);
            }

            if(pb.isTrigger())
            {
                trigger.add(pb);
            }
        }
    }

    public void gameLoop()
    {
        while(true)
        {
            float newTime = System.nanoTime() / 1000000000.0f;
            float deltaTime = (newTime - gameTime);

            for(int i = 0; i < deltaTime / updateTime; i++)
            {
                deltaTime -= updateTime;
                gameTime += updateTime;
                update();
            }

            currentInterpolation = deltaTime / updateTime;
            main.repaint();
            input.flush();
        }
    }

    public void update()
    {
        physicsUpdate();

        for(int i = 0; i < gameObjects.size(); i++)
        {
            gameObjects.get(i).update(updateTime);
        }
    }

    public void physicsUpdate()
    {
    }

    public void render(Graphics g)
    {
        for(int i = 0; i < gameObjects.size(); i++)
        {
            gameObjects.get(i).render(g, currentInterpolation);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 0, 25));
        g.drawString(input.getMouseX() + " | ", 15, 30);
    }
}
