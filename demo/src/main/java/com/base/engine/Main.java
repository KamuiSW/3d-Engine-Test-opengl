package com.base.engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.lang.Math;

import org.joml.*;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Main{
    //FOV and stuff
    public static final float FOV = (float) Math.toRadians(90);
    public static final float Z_Near = 0.01f;
    public static final float Z_Far = 1000f;

    private String title;

    private int width, height;

    private long window;

    private boolean resize, vSync;

    private Matrix4f projectionMatrix;

    //here i learned some new stuff. want to acces the variables outside of this code? do this.
    //it's like a public string, int and bool all in one function, able to acces them.
    public Main(String title, int width, int height, boolean vSync){
        //the variables in this local thing is the same as the variables in this code.
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix = new Matrix4f();
    }

    public void init(){
        //initializing and if there is an error it would print system error
        //tbh this opengl shit is weird but sure i get it.
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()){
            //if it didn't initialize, then throw in this stuff.
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //window hints, not sure what they do, but they work.
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        boolean maximised = false;
        if(width == 0 || height == 0){
            //if the width and height are 0, it would be set to 100 so you would see something instead of just a dark emnptyness void of death.
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximised = true;
        }

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL){
            //if there is no memoryutil, it would throw in this stuff idk sounds funny tho
            throw new RuntimeException("failed to create GLFW window");
        }

        //setting the windows height and width in the glfwsetframe to the width and height of the code
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResize(true);
        });

        //if it gets key pressed escape and then released, it would close the window.
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->{
            if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE){
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });

        //if the boolean maximised gets checked, it would set the window as, ofcourse maximized the fuck you think.
        if(maximised){
            GLFW.glfwMaximizeWindow(window);
        }
        else{
            //if no maximized then set the window in the primary monitor, and then the position would be in the center and some math bullshit.
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, (vidMode.width() - width) /2, (vidMode.height() - height) / 2);
        }
        //make the goddamn window finally.
        GLFW.glfwMakeContextCurrent(window);

        // if vsync is turned on it would do that shit. (who the fuck would turn on vsync you sick bastard)
        if(vSync){
            GLFW.glfwSwapInterval(1);
        }

        //show the window because otherwise NO FUCKING WINDOW WHAT DO YOU THINK
        GLFW.glfwShowWindow(window);

        //create capabilities? what? idk
        GL.createCapabilities();

        //look at them colors on them screen! damn. (it's just black and white btw)
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
    }

    //finally a piece of code thats in c#, updating every frame and it would swapbuffers. i have no fucking clue what it means but sure.
    public void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();


    }

    public void cleanup(){
        //clean up the window, or DESTROY IT.
        GLFW.glfwDestroyWindow(window);
    }

    public void SetClearColour(float r, float g, float b, float a){
        //set the color of the window. look at those pretty colors!
        GL11.glClearColor(r, g, b, a);
    }

    //bool to check if the key is pressed, if it is, then return the keycode and it would be as GLFW_pressed and you can do shit with it simple to call instead of a increadibly long fucking code like this line that is being increadibly long im totally not streching it what do you mean. l.
    public boolean isKeyPressed(int keycode){
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }

    //bool to check if window should be closed, and then it would close it. incredible.
    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    //omg it's a title
    public String getTitle(){
        return title;
    }

    //set the title of the title to be the title including the title and also featuering the title.
    public void setTitle(String title){
        GLFW.glfwSetWindowTitle(window, title);
    }

    //bool to check if resize, if it is resize, then resize featuering resize.
    public boolean isResize(){
        return resize;
    }
    //set the resize to be resize like resize WHY IS THERE SO MANY RESIZES.
    public void setResize(boolean resize){
        this.resize = resize;
    }

    //get the width so you can change it using other codes.
    public int getWidth(){
        return width;
    }
    //samething like on top
    public int getHeight(){
        return height;
    }
    //same shit as above
    public long getWindow(){
        return window;
    }
    //ive been writing this the entire french class.
    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
    //a matrix4f(have no idea what it means) and it would set the aspect ratio to be width devided by height, also return some FOV and stuff.
    public Matrix4f updateProjectionMatrix(){
        float aspectRatio = (float) width / height;
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_Near, Z_Far);
    }
    //finally last piece of code in main. it's the same thing i guess.
    public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height){
        float aspectRatio = (float) width / height;
        return matrix.setPerspective(FOV, aspectRatio, Z_Near, Z_Far);
    }

    private void start(){
        vSync = false;
    }
}