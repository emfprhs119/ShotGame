package min.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import min.input.KeyMapListener;

public abstract class UIObj extends Group implements KeyMapListener {
    public UIObj() {
        setName(this.getClass().getSimpleName());
        layoutConfiguration();
        setVisible(false);
    }
    abstract void layoutConfiguration();
}
