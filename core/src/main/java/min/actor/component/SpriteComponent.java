package min.actor.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import min.actor.ICallback;
import min.actor.TimerAction;
import min.manager.Assets;
import com.badlogic.gdx.utils.Queue;
import java.util.Locale;

public class SpriteComponent extends Action {
    public enum SpriteAction{IDLE,WALK,DASH,DAMAGE,DEAD, MINING,CRAFT, UNKNOWNS, ATTACK};
    Animation<TextureRegion>[] animations;
    private float stateTime;
    private float animationEndTime;
    public boolean isAction;
    Sprite sprite;
    SpriteAction prevAction;
    SpriteAction currAction;
    SpriteAction nextAction;
    float animationNextEndTime;
    Queue<ICallback> callbackQueue;

    public SpriteComponent(Actor actor){
        sprite = new Sprite();
        sprite.setBounds(0,0,32,32);
        actor.setBounds(0,0,32,32);
        if (actor.getName().equals("Monster"))
            sprite.setColor(Color.PINK);
        currAction = SpriteAction.IDLE;
        actor.addAction(this);
        callbackQueue = new Queue<ICallback>();
        updateStateSprite();
    }

    @Override
    public boolean act(float delta) {
        stateTime += delta;

        if (animationEndTime > 0 && stateTime > animationEndTime){
            while (!callbackQueue.isEmpty())
                callbackQueue.removeFirst().exec();
            if (currAction == SpriteAction.DEAD) {
                setSpriteAction(SpriteAction.UNKNOWNS,0);
                return false;
            }
            setSpriteAction(nextAction,animationNextEndTime);
            if (currAction == SpriteAction.IDLE) {
                isAction = false;
            }
        }
        updateStateSprite();
        return false;
    }

    public void addEndCallback(ICallback endCallback) {
        callbackQueue.addLast(endCallback);
    }

    public void setSpriteAction(SpriteAction spriteAction, float during){
        if (isChangeable(spriteAction)) {
            isAction = spriteAction != SpriteAction.IDLE;
            currAction = spriteAction;
            animationEndTime = during;
            stateTime = 0;
            setScheduleSpriteAction(SpriteComponent.SpriteAction.IDLE, 0);
        }
    }

    boolean isChangeable(SpriteAction spriteAction){
        return (currAction != SpriteAction.DAMAGE);

    }

    public void setScheduleSpriteAction(SpriteAction spriteAction, float during){
        nextAction = spriteAction;
        animationNextEndTime = during;
    }

    public void setFaceAxis(Vector2 axis) {
        if (axis.x != 0) {
            sprite.flip((axis.x < 0) == (!sprite.isFlipX()), false);
        }
        if (currAction == SpriteAction.IDLE || currAction == SpriteAction.WALK) {
            currAction = (axis.x == 0 && axis.y == 0)? SpriteAction.IDLE: SpriteAction.WALK;
            if (prevAction != currAction) {
                stateTime = 0;
                prevAction = currAction;
            }
        }
    }

    void updateStateSprite(){
        String ani_name = actor.getName().toLowerCase(Locale.ROOT)+"_"+currAction.toString().toLowerCase(Locale.ROOT);
        //Gdx.app.log(ANI_ASSET,ani_name);
        if (!Assets.animationList.containsKey(ani_name)){
            ani_name = actor.getName().toLowerCase(Locale.ROOT)+"_idle";
        }
        TextureRegion textureRegion = (TextureRegion) Assets.getAnimation(ani_name).getKeyFrame(stateTime);
        textureRegion.flip(sprite.isFlipX() != textureRegion.isFlipX(),false);
        sprite.setRegion(textureRegion);
        sprite.setSize(textureRegion.getRegionWidth(),textureRegion.getRegionHeight());
    }

    public void updatePosition(float x, float y){
        sprite.setX((x - sprite.getRegionWidth() / 2f));
        sprite.setY((y - sprite.getRegionHeight() / 4f));
        if (actor.getName().equals("Player"))
            sprite.setX((x - sprite.getRegionWidth() / 2f - (sprite.isFlipX() ? 2 : -2)));
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public boolean isFlipX() {
        return sprite.isFlipX();
    }
    /*
    private void initAnimation(){
        animations = new Animation[SpriteAction.values().length];

        Texture sheet = new Texture(Gdx.files.internal("character/FinnSprite.png"));
        TextureRegion[] textureRegions;
        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,9);
        animations[SpriteAction.IDLE.ordinal()] = new Animation<TextureRegion>(0.4f,textureRegions);
        animations[SpriteAction.IDLE.ordinal()].setPlayMode(Animation.PlayMode.LOOP);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,9,6);
        animations[SpriteAction.WALK.ordinal()] = new Animation<TextureRegion>(0.1f,textureRegions);
        animations[SpriteAction.WALK.ordinal()].setPlayMode(Animation.PlayMode.LOOP);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,9,6);
        animations[SpriteAction.DASH.ordinal()] = new Animation<TextureRegion>(0.05f,textureRegions);
        animations[SpriteAction.DASH.ordinal()].setPlayMode(Animation.PlayMode.LOOP);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,16,2);
        animations[SpriteAction.DAMAGE.ordinal()] = new Animation<TextureRegion>(0.2f,textureRegions);
        animations[SpriteAction.DAMAGE.ordinal()].setPlayMode(Animation.PlayMode.NORMAL);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,18,5);
        animations[SpriteAction.DEAD.ordinal()] = new Animation<TextureRegion>(0.1f,textureRegions);
        animations[SpriteAction.DEAD.ordinal()].setPlayMode(Animation.PlayMode.NORMAL);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,1);
        animations[SpriteAction.MINING.ordinal()] = new Animation<TextureRegion>(0f,textureRegions);
        animations[SpriteAction.MINING.ordinal()].setPlayMode(Animation.PlayMode.NORMAL);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,9);
        animations[SpriteAction.CRAFT.ordinal()] = new Animation<TextureRegion>(0.4f,textureRegions);
        animations[SpriteAction.CRAFT.ordinal()].setPlayMode(Animation.PlayMode.LOOP);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,23,5);
        animations[SpriteAction.ATTACK.ordinal()] = new Animation<TextureRegion>(0.1f,textureRegions);
        animations[SpriteAction.ATTACK.ordinal()].setPlayMode(Animation.PlayMode.NORMAL);
    }

     */
}
