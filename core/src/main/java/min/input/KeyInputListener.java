package min.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.Vector;

public class KeyInputListener extends InputListener {

    Vector<Integer> inputKeyVector = new Vector<Integer>();
    Vector2 axis = new Vector2();
    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        //changeMode(GameInput.MODE.KEYBOARD);
        //Gdx.app.log(event.toString(),keycode+"");
        inputKeyVector.add(keycode);
        return false;
    }
    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        inputKeyVector.removeElement(keycode);
        return false;
    }
    public int getPressButtons(){
        return (inputKeyVector.contains(Input.Keys.Z)?GameInput.Key0:0) |
                (inputKeyVector.contains(Input.Keys.X)?GameInput.Key1:0) |
                (inputKeyVector.contains(Input.Keys.C)?GameInput.Key2:0) |
                (inputKeyVector.contains(Input.Keys.V)?GameInput.Key3:0);
    }
    public Vector2 getAxis(){
        float axisX,axisY;
        axisX = ((inputKeyVector.contains(Input.Keys.A) || inputKeyVector.contains(Input.Keys.LEFT))?-1:0) +
                ((inputKeyVector.contains(Input.Keys.D) || inputKeyVector.contains(Input.Keys.RIGHT))?+1:0);
        axisY = ((inputKeyVector.contains(Input.Keys.W) || inputKeyVector.contains(Input.Keys.UP))?+1:0) +
                ((inputKeyVector.contains(Input.Keys.S) || inputKeyVector.contains(Input.Keys.DOWN))?-1:0);
        if (Math.abs(axisX) == 1 && Math.abs(axisY) == 1){
            axisX = axisX>0?0.70710678f:-0.70710678f;
            axisY = axisY>0?0.70710678f:-0.70710678f;
        }
        axis.x = axisX;
        axis.y = axisY;
        return axis;
    }
    public boolean isTypedKey(int key){
        return inputKeyVector.contains(key);
    }
}