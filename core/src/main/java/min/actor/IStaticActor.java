package min.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static min.manager.Box2dManager.CATEGORY_INTERACT;
/*
public abstract class IStaticActor extends IBasicActor {
    public enum InteractionType {MINE,CRAFT,CHEST}
    TextureRegion textureRegion;
    float drawX,drawY;
    InteractionType type;

        filter.categoryBits = CATEGORY_INTERACT;
        filter.maskBits = -1;

    public InteractionType getType(){
        return type;
    }

    @Override
    protected void positionChanged () {
        float x = getX();
        float y = getY();
        drawX = x-textureRegion.getRegionWidth()/2f;
        drawY = y-textureRegion.getRegionHeight()/2f;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(textureRegion,drawX,drawY);
    }

}
*/