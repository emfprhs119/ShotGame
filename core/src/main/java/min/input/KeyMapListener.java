package min.input;

import com.badlogic.gdx.math.Vector2;

public interface KeyMapListener {
    boolean act(float delta, Vector2 axis, int buttons, InputAction inputAction);
}
