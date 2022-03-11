package min.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Queue;
import min.manager.Box2dManager;

import java.util.Vector;

public class GameInput{
    enum MODE{KEYBOARD, CONTROLLER}
    static public final int Key0 = 1 << 0;
    static public final int Key1 = 1 << 1;
    static public final int Key2 = 1 << 2;
    static public final int Key3 = 1 << 3;
    static public final int KeyALL = Key0|Key1|Key2|Key3;

    MODE mode;
    KeyInputListener keyInput;
    GamePadListener controllerInput;
    Queue<Integer> inputQueue;

    public GameInput(){
        controllerInput = new GamePadListener();
        keyInput = new KeyInputListener();
        inputQueue = new Queue<>();
        Controllers.addListener(controllerInput);
        mode = MODE.KEYBOARD;
    }
    public int getPressButtons(){
        if (keyInput.getPressButtons()>0) {
            mode = MODE.KEYBOARD;
        }else if (controllerInput.getPressButtons()>0){
            mode = MODE.CONTROLLER;
        }

        if (mode == MODE.KEYBOARD){
            return keyInput.getPressButtons();
        }else if (mode == MODE.CONTROLLER){
            return controllerInput.getPressButtons();
        }else
            return 0;
    }
    public Vector2 getAxis(){
        Vector2 axis = null;
        if (mode == MODE.KEYBOARD){
            axis = keyInput.getAxis();
        }else if (mode == MODE.CONTROLLER){
            axis = controllerInput.getAxis();
        }
        return axis;
    }
    public ControllerListener getController() {
        return controllerInput;
    }

    public EventListener getKeyListener() {
        return keyInput;
    }
}
