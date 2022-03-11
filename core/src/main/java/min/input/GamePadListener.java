package min.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class GamePadListener implements ControllerListener {
    Vector<Integer> inputKeyVector = new Vector<Integer>();
    Vector2 controlLeftAxis = new Vector2();
    Vector2 controlRightAxis = new Vector2();
    Vector2 axis = new Vector2();
    ControllerMapping mapping;
    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        mapping = controller == null ? null : controller.getMapping();
        //changeMode(GameInput.MODE.CONTROLLER);
        switch (axisCode) {
            case 0:
                controlLeftAxis.x = value;
                break;
            case 1:
                controlLeftAxis.y = value;
                break;
            case 2:
                controlRightAxis.x = value;
                break;
            case 3:
                controlRightAxis.y = value;
                break;
        }
        return false;
    }


    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown (Controller controller, int buttonIndex) {
        //changeMode(GameInput.MODE.CONTROLLER);
        inputKeyVector.add(buttonIndex);
        return false;
    }

    @Override
    public boolean buttonUp (Controller controller, int buttonIndex) {
        inputKeyVector.removeElement(buttonIndex);
        return false;
    }

    public Vector2 getAxis(){
        axis.x = Math.abs(controlLeftAxis.x) < 0.2 ? 0 : controlLeftAxis.x;
        axis.y = Math.abs(controlLeftAxis.y) < 0.2 ? 0 : -controlLeftAxis.y;
        return axis;
    }

    public int getPressButtons(){
        if (mapping == null)
            return 0;
        return (inputKeyVector.contains(mapping.buttonA)?GameInput.Key0:0) |
                (inputKeyVector.contains(mapping.buttonX)?GameInput.Key1:0) |
                (inputKeyVector.contains(mapping.buttonB)?GameInput.Key2:0) |
                (inputKeyVector.contains(mapping.buttonY)?GameInput.Key3:0);
    }
}
