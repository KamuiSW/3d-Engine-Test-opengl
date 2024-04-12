package com.base.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.base.engine.Utils.Consts;

public class EngineManager {
    //some math stuff i need later
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frametime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private Main window;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;

    private void init() throws Exception{
        //initializing shit AGAIN and also getting the Launcher code.
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Launcher.getWindow();
        gameLogic = Launcher.getGame();
        window.init();
        gameLogic.init();
    }

    public void start() throws Exception{
        //if the code start it would intialize and run the function "run". unless if isRunning is true, then it would return.
        init();
        if(isRunning){
            return;
        }

        run();
    }

    public void run(){
        //run shit i really don't want to keep writing this shit
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while(isRunning){
            boolean render = false;
            long startTime = System.nanoTime();
            long passTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passTime / (double) NANOSECOND;
            frameCounter += passTime;

            input();

            while(unprocessedTime > frametime){
                render = true;
                unprocessedTime -= frametime;

                if(window.windowShouldClose())
                    stop();

                if(frameCounter >= NANOSECOND){
                    setFPS(frames);
                    window.setTitle(Consts.TITLE + getFPS());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render){
                update();
                render();
                frames++;

            }
        }

        cleanup();
    }

    public void stop(){
        if(!isRunning){
            return;
        }
        isRunning = false;
    }

    public void input(){
        gameLogic.input();
    }

    private void render(){
        gameLogic.render();
        window.update();
    }

    private void update(){
        gameLogic.update();
    }

    private void cleanup(){
        window.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFPS(){
        return fps;
    }

    public static void setFPS(int fps){
        EngineManager.fps = fps;
    }
}
