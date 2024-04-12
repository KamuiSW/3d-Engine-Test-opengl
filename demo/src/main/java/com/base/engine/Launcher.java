package com.base.engine;

import org.lwjgl.Version;

import com.base.engine.TestGames.TestGame;
import com.base.engine.Utils.Consts;

public class Launcher {

    private static Main window;
    private static TestGame game;


    public static void main(String[] args){
        System.out.println(Version.getVersion());
        window = new Main(Consts.TITLE, 1600, 900, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();
        try{
            engine.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public static Main getWindow(){
        return window;
    }

    public static TestGame getGame(){
        return game;
    }
}
