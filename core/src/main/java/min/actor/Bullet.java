package min.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import min.data.BulletData;
import min.manager.Box2dManager;
import min.manager.BulletPool;


public class Bullet implements Pool.Poolable {
    Animation<TextureRegion> animation;
    Body body;
    public Vector2 position = new Vector2(0,0);
    public Vector2 direction= new Vector2(0,0);
    public static short TARGET_PLAYER = 1;
    public static short TARGET_MONSTER = 1 << 1;
    BulletData bulletData;
    float stateTime;
    float removeEffectTime = 0.5f;
    boolean removeEffect = false;

    BulletPool bp;
    public Bullet(BulletPool bp){
        super();
        this.bp = bp;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void init(World world, BulletData bulletData) {
        if (body == null)
            initBody(world);
        this.bulletData = bulletData;
    }

    public void setActive(boolean active) {
        body.setActive(active & !removeEffect);
    }

    public void act(float delta){
        stateTime += delta;
        timeoutCheck();
        collideCheck();
        moveToDirection(delta);
    }

    void moveToDirection(float delta){
        if (!removeEffect) {
            position.add(
                    direction.x * bulletData.getMoveSpeed() * delta,
                    direction.y * bulletData.getMoveSpeed() * delta);
            if (body != null) {
                body.setTransform(position, 0);
            }
        }
    }

    void collideCheck(){
        if (body.isActive()) {
            IMoveActor actor = getCollideTarget();
            if (actor != null) {
                Gdx.app.log("DAMAGE", actor.getName());
                actor.damage(bulletData.getPower());
                body.setActive(false);
                removeEffect = true;
                stateTime = 0;
            }
        }
    }

    void timeoutCheck(){
        float endTime = removeEffect?removeEffectTime:bulletData.getFinishTime();
        if (stateTime>endTime)
            bp.free(this);
    }

    public void draw(Batch batch) {
        TextureRegion textureRegion;
        if (removeEffect)
            textureRegion = animation.getKeyFrame(stateTime);
        else
            textureRegion = animation.getKeyFrame(0);
        batch.draw(textureRegion,position.x-16,position.y-16,16,16,32,32,1,1,0);
    }

    public Vector2 getPosition() {
        return position;
    }

    public IMoveActor getCollideTarget(){
        IMoveActor IMoveActor = null;
        Array<Contact> contacts = body.getWorld().getContactList();
        for(Contact contact:contacts) {
            Fixture fixA = contact.getFixtureA();
            Fixture fixB = contact.getFixtureB();
            Fixture owner = body.getFixtureList().get(0);
            Object obj = null;
            if (owner == fixA) {
                obj = fixB.getUserData();
            }else if (owner == fixB) {
                obj = fixA.getUserData();
            }
            if (obj instanceof IMoveActor) {
                if (((bulletData.getTarget() & TARGET_PLAYER) > 0 && ((IMoveActor) obj).getName().equals("Player"))||
                        ((bulletData.getTarget() & TARGET_MONSTER) > 0 && ((IMoveActor) obj).getName().equals("Monster"))){
                    IMoveActor = ((IMoveActor) obj);
                }
                break;
            }
        }
        return IMoveActor;
    }

    void initBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);
        fixtureDef.shape = circle;
        body = world.createBody(bdef);
        fixtureDef.filter.categoryBits = Box2dManager.CATEGORY_BULLET;
        fixtureDef.filter.maskBits = Box2dManager.CATEGORY_BULLET;
        //body.setBullet(true);
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circle.dispose();
    }

    public void detachBody(){
        if (body != null){
            body.getWorld().destroyBody(body);
            body = null;
        }
    }

    public void setDirection(float x, float y) {
        this.direction.set(x,y);
    }

    public void fireBullet(float xpos, float ypos, float xvel, float yvel){
        this.position.set(xpos,ypos);
        this.direction.set(xvel,yvel);
        body.setTransform(position,0);
        setActive(true);
    }

    @Override
    public void reset() {
        this.removeEffect = false;
        this.stateTime = 0;
        this.position.set(0,0);
        this.direction.set(0,0);
        this.body.setActive(false);
    }
}
