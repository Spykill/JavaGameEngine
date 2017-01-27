package ca.spykill.gameengine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Andriy on 2017-01-25.
 */
public class Game
{
    private Main main;
    private Input input;

    private boolean haltGameLoop = false;

    private float windowWidth;
    private float windowHeight;

    private long updateTime;

    private long time;
    private long gameTime;

    private float currentInterpolation;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<PhysicsBody> physicsBodies;

    private ArrayList<PhysicsBody> kinematic;
    private ArrayList<PhysicsBody> dynamic;
    private ArrayList<PhysicsBody> trigger;

    private ArrayList<String> debugMessages;

    private Scene currentScene;

    private Camera camera;

    /**
     * Constructs this game instance.
     * @param FPS The desired number of times to run the game update per second
     * @param input The reference to the bound input (to get input from the window)
     * @param main The reference to the main (so that we have something to draw to)
     */
    public Game(float FPS, Input input, Main main)
    {
        this.updateTime = (long)((1.0f / FPS) * 1000000000.0f);
        this.input = input;
        this.main = main;

        windowWidth = main.getWindowWidth();
        windowHeight = main.getWindowHeight();

        gameTime = System.nanoTime();

        gameObjects = new ArrayList<>();
        physicsBodies = new ArrayList<>();
        kinematic = new ArrayList<>();
        dynamic = new ArrayList<>();
        trigger = new ArrayList<>();

        debugMessages = new ArrayList<>();
        debugMessages.ensureCapacity(100);
    }

    /**
     * Starts the specified scene
     * @param scene The scene to initialise and use
     */
    public void startScene(Scene scene)
    {
        scene.InitialiseScene(this);
        currentScene = scene;
    }

    /**
     * Adds <object> to the scene
     * @param object The object to add
     */
    public void addToScene(GameObject object)
    {
        gameObjects.add(object);
        if(object instanceof PhysicsBody)
        {
            PhysicsBody pb = (PhysicsBody)object;
            physicsBodies.add(pb);
            addPhysicsBodyToLists(pb);
        }
    }

    /**
     * Clears <body> from dynamic, kinematic, or trigger lists so that we can re-add it to the correct one
     * @param body The body to remove from lists
     * @return Was the body found and removed?
     */
    boolean clearPhysicsBodyFromLists(PhysicsBody body)
    {
        ArrayList<PhysicsBody> listToRemove;
        switch(body.getPhysicsType())
        {
            case Dynamic:
                listToRemove = dynamic;
                break;
            case Kinematic:
                listToRemove = kinematic;
                break;
            case Trigger:
                listToRemove = trigger;
                break;
            default:
                listToRemove = null;
                break;
        }

        int ind = listToRemove.indexOf(body);
        if(ind != -1)
        {
            listToRemove.remove(ind);
            return true;
        }
        return false;
    }

    /**
     * Adds <body> to the correct list (dynamic, kinematic, trigger)
     * @param body The body to add
     */
    void addPhysicsBodyToLists(PhysicsBody body)
    {
        switch (body.getPhysicsType())
        {
            case Dynamic:
                dynamic.add(body);
                break;
            case Kinematic:
                kinematic.add(body);
                break;
            case Trigger:
                trigger.add(body);
                break;
        }
    }

    /**
     * Starts and maintains the game loop
     */
    void gameLoop()
    {
        haltGameLoop = false;
        while(!haltGameLoop)
        {
            long newTime = System.nanoTime();
            long deltaTime = (newTime - gameTime);

            //for(int i = 0; i < deltaTime / updateTime; i++)
            while(deltaTime > updateTime)
            {
                deltaTime -= updateTime;
                gameTime += updateTime;
                update();
            }

            currentInterpolation = deltaTime * 1.0f / updateTime;
            //main.repaint();
            //main.paintImmediately(0,0, (int)windowWidth, (int)windowHeight);
            render(main.getGraphics());

            input.flush();
        }
    }

    /**
     * Performs the update iteration
     */
    private void update()
    {
        camera.update(updateTime / 1000000000.0f);

        physicsUpdate();

        for(int i = 0; i < gameObjects.size(); i++)
        {
            gameObjects.get(i).update(updateTime / 1000000000.0f);
        }
        //addDebugMessage("" + debugMessages.size());
    }

    /**
     * Performs the physics step
     */
    public void physicsUpdate()
    {
        for(int i = 0; i < dynamic.size(); i++)
        {
            dynamic.get(i).doVelocityStep(updateTime / 1000000000.0f);
        }

        for(int i = 0; i < dynamic.size(); i++)
        {
            for(int j = 0; j < kinematic.size(); j++)
            {
                dynamic.get(i).tryCollision(kinematic.get(j));
            }

            for(int j = i + 1; j < dynamic.size(); j++)
            {
                dynamic.get(i).tryCollision(dynamic.get(j));
            }
        }
    }

    /**
     * Renders the current state of the game to <g>
     * @param g The Graphics object to draw to
     */
    public void render(Graphics g)
    {
        if(camera == null)
        {
            return;
        }

        BufferedImage img = new BufferedImage((int)windowWidth, (int)windowHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics alt_g = img.getGraphics();

        alt_g.setColor(Color.BLACK);
        alt_g.fillRect(0, 0, (int)windowWidth, (int)windowHeight);
        camera.render(currentInterpolation);

        for(int i = 0; i < gameObjects.size(); i++)
        {
            gameObjects.get(i).render(alt_g, camera, currentInterpolation);
        }

        alt_g.setColor(Color.WHITE);
        alt_g.setFont(new Font("Arial", 0, 15));
        alt_g.drawString(camera.getOffset(currentInterpolation).X() + " | ", 15, 30);
        alt_g.dispose();

        for(int i = 0; i < debugMessages.size(); i++)
        {
            alt_g.drawString(debugMessages.get(i), 15, 20 *(i+1));
        }
        debugMessages.clear();
        g.drawImage(img, 0, 0, null);
    }

    public void addDebugMessage(String message)
    {
        debugMessages.add(message);
    }

    public Camera getCamera()
    {
        return camera;
    }

    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }

    public Input getInput()
    {
        return input;
    }

    public float getWindowWidth()
    {
        return windowWidth;
    }

    public float getWindowHeight()
    {
        return windowHeight;
    }
}
