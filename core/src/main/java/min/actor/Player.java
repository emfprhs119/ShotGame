package min.actor;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import min.actor.component.BodyComponent;
import min.actor.component.SpriteComponent;
import min.actor.generator.BodyGenerator;
import min.data.BulletData;
import min.data.PlayerData;
import min.input.KeyMapListener;
import min.input.ActionForPlayer;
import min.manager.Box2dManager;
import min.manager.BulletPool;

public class Player extends IMoveActor {
    private BulletPool bulletPool;
    private BodyComponent areaBodyComponent;
    private PlayerData playerData;
    private MoveByAction dashAction;
    private ActionForPlayer inputAction;
    private BulletData bulletData;
    private ToolsEffect toolsEffect;
    private AttackCallback attackCallback;

    public Player(Stage stage,World world) {
        super(stage,world);
        initAreaBody(world);
        playerData = new PlayerData();
        dashAction = new MoveByAction();
        inputAction = new ActionForPlayer(this);
        toolsEffect = new ToolsEffect(this);
        bulletData = new BulletData(Bullet.TARGET_MONSTER, 4,150,1);
        attackCallback = new AttackCallback();
    }

    private void initAreaBody(World world) {
        Body body = BodyGenerator.playerGenerateAreaBody(world);
        Filter filter = new Filter();
        filter.categoryBits = Box2dManager.CATEGORY_PLAYER_AREA;
        filter.maskBits = Box2dManager.MASK_PLAYER_AREA;
        areaBodyComponent = new BodyComponent(this,body,filter);
    }

    public boolean onceAction(SpriteComponent.SpriteAction action, IBasicActor target){
        if (isAction())
            return false;
        float during = -1;
        float moveSpeed = playerData.getStatus().getMoveSpeed();
        switch (action){
            case DASH:
                during = 0.1f;
                //dashAction.setAmount(lastAxis.x*moveSpeed*0.3f,lastAxis.y*moveSpeed*0.3f);
                dashAction.setDuration(during);
                dashAction.restart();
                addAction(dashAction);
                break;
            case MINING:
                during = 0.3f;
                toolsEffect.run(ToolsEffect.ActionType.MINE);
                attackCallback.setTarget(target);
                attackCallback.setPower(3);
                spriteComp.addEndCallback(toolsEffect.disableCallback);
                spriteComp.addEndCallback(attackCallback);
                break;
            case ATTACK:
                during = 0.5f;
                Bullet bullet = bulletPool.obtain();
                bullet.init(bodyComponent.body.getWorld(),bulletData);
                bullet.fireBullet(getX()+(isFlipX()?-13:13),getY(),isFlipX()?-1f:1f,0f);
                //Gdx.app.log("Player",bullet.toString()+"_"+getX()+(isFlipX()?-13:13));
                break;
        }
        spriteComp.setSpriteAction(action,during);
        return true;
    }

    class AttackCallback implements ICallback{
        IBasicActor target;
        float power;

        public void setTarget(IBasicActor target) {
            this.target = target;
        }

        public void setPower(float power) {
            this.power = power;
        }

        @Override
        public void exec() {
            target.damage(power);
        }
    }

    public float getMineDelay() {
        return 0.3f;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public KeyMapListener getPlayerInputAction() {
        return inputAction;
    }

    public Object[] getAreaContactActors(){
        return areaBodyComponent.getContactActors();
    }

    public void setBulletPool(BulletPool bulletPool) {
        this.bulletPool = bulletPool;
    }

    @Override
    Body getInitBody(World world) {
        return BodyGenerator.playerGenerateBody(world);
    }

    @Override
    Filter getInitBodyFilter() {
        Filter filter = new Filter();
        filter.categoryBits = Box2dManager.CATEGORY_PLAYER;
        filter.maskBits = Box2dManager.MASK_PLAYER;
        return filter;
    }

    @Override
    Body getHitInitBody(World world) {
        return BodyGenerator.playerGenerateBody(world);
    }

    @Override
    Filter getHitInitBodyFilter() {
        Filter filter = new Filter();
        filter.categoryBits = Box2dManager.CATEGORY_BULLET;
        filter.maskBits = Box2dManager.CATEGORY_BULLET;
        return filter;
    }

    @Override
    float getMoveSpeed() {
        return playerData.getStatus().getMoveSpeed();
    }

    @Override
    protected void positionChanged () {
        super.positionChanged();
        areaBodyComponent.setPosition(bodyComponent.getPosition());
    }

    @Override
    public boolean remove() {
        areaBodyComponent.detachBody();
        return super.remove();
    }
}