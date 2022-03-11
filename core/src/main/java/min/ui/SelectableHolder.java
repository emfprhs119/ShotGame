package min.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import min.data.Item;
import min.manager.Assets;

public class SelectableHolder extends Actor {
    Drawable blankBackground;
    Drawable selectBackground;
    boolean isSelect;

    SelectableHolder(){
        isSelect = false;
        blankBackground = Assets.uiskin.getDrawable("textfield-selected");
        selectBackground = Assets.uiskin.getDrawable("textfield-selected");
    }

    public void draw (Batch batch, float parentAlpha) {
        if (isSelect)
            selectBackground.draw(batch, getX(), getY(), getWidth(), getHeight());
        else
            blankBackground.draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}
