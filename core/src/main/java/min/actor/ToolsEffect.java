package min.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

public class ToolsEffect extends Actor {
    public enum ActionType {MINE}
    float stateTime;
    Sprite sprite;
    TextureRegion textureRegionArr[][];
    RotateByAction rotateByAction;
    IMoveActor owner;
    ICallback disableCallback;

    public ToolsEffect(IMoveActor owner){
        setName(this.getClass().getSimpleName());
        Texture sheet = new Texture(Gdx.files.internal("Downloads/IconsPJ.png"));
        textureRegionArr = TextureRegion.split(sheet, 32, 32);
        sprite = new Sprite();
        sprite.setBounds(0,0,32,32);
        sprite.setCenter(16,16);
        sprite.setScale(0.5f);
        rotateByAction = new RotateByAction();
        rotateByAction.setAmount(800);
        rotateByAction.setDuration(10);
        disable();
        this.owner = owner;
        disableCallback = new ICallback() {
            @Override
            public void exec() {
                disable();
            }
        };
        owner.getStage().addActor(this);
    }

    public void run(ActionType actionType){
        switch (actionType){
            case MINE:
                sprite.setRegion(textureRegionArr[0][0]);
                sprite.setFlip(!owner.isFlipX(),false);
                sprite.setOrigin(sprite.isFlipX()?(sprite.getRegionWidth()-6):6,6);
                float x = owner.getX()-(sprite.getRegionWidth()/5f*(sprite.isFlipX()?4:1));
                float y = owner.getY()-sprite.getRegionHeight()/3f;
                rotateByAction.setAmount(sprite.isFlipX()?-3000:3000);
                setRotation(0);
                sprite.setPosition(x,y);
                Gdx.app.log(actionType.name(),x+"_"+y);
            break;
        }
        stateTime = 0;
        rotateByAction.restart();
        setVisible(true);
        addAction(rotateByAction);
    }

    public void disable() {
        setVisible(false);
        removeAction(rotateByAction);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.setRotation(getRotation());
        sprite.draw(batch);
    }
}
