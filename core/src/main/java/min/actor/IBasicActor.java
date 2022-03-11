package min.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.actor.component.BodyComponent;
import min.actor.component.SpriteComponent;
import min.data.State;
import min.draws.HealthBarDrawer;

abstract public class IBasicActor extends Actor {
    public BodyComponent bodyComponent;
    public SpriteComponent spriteComp;
    public State state;
    private HealthBarDrawer healthBarDrawer;
    private ICallback removeCallback;

    public IBasicActor(Stage stage, World world){
        super();
        setName(this.getClass().getSimpleName());
        initBody(world);
        spriteComp = new SpriteComponent(this);
        state = new State();
        healthBarDrawer = new HealthBarDrawer(state);
        removeCallback = new ICallback(){
            @Override
            public void exec() {
                remove();
            }
        };
        stage.addActor(this);
    }

    private void initBody(World world){
        Body body = getInitBody(world);
        Filter filter = getInitBodyFilter();
        bodyComponent = new BodyComponent(this,body,filter);
    }

    abstract Body getInitBody(World world);
    abstract Filter getInitBodyFilter();

    public void setInitPosition(float x, float y){
        bodyComponent.body.setTransform(x,y,0);
        setPosition(x,y);
    }

    public void damage(float power){
        state.damage(power);
        spriteComp.setSpriteAction(SpriteComponent.SpriteAction.DAMAGE,0.5f);
        if (state.isDead()) {
            setActive(false);
            spriteComp.setScheduleSpriteAction(SpriteComponent.SpriteAction.DEAD,0.5f);
            spriteComp.addEndCallback(removeCallback);
        }
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        healthBarDrawer.setPosition(getX(),getY());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        spriteComp.draw(batch);
        healthBarDrawer.draw(batch);
    }

    @Override
    public boolean remove() {
        bodyComponent.detachBody();
        return super.remove();
    }

    public boolean isAction(){
        return spriteComp.isAction;
    }

    public void setActive(boolean flag){
        bodyComponent.setActive(flag);
    }
}
