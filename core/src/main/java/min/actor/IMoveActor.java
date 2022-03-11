package min.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.actor.component.BodyComponent;

public abstract class IMoveActor extends IBasicActor {
    private BodyComponent hitBodyComponent;

    public IMoveActor(Stage stage, World world) {
        super(stage,world);
        initHitBody(world);
    }

    private void initHitBody(World world){
        Body body = getHitInitBody(world);
        Filter filter = getHitInitBodyFilter();
        hitBodyComponent = new BodyComponent(this,body,filter);
    }

    abstract Body getInitBody(World world);
    abstract Filter getInitBodyFilter();
    abstract Body getHitInitBody(World world);
    abstract Filter getHitInitBodyFilter();

    abstract float getMoveSpeed();

    public void moveByAxis(float delta,Vector2 axis) {
        if (isAction())
            return;
        spriteComp.setFaceAxis(axis);
        float moveSpeed = getMoveSpeed();
        bodyComponent.movePosition(axis.x * moveSpeed * delta,axis.y * moveSpeed * delta);
    }

    public boolean isAction(){
        return spriteComp.isAction;
    }

    public boolean isFlipX(){
        return spriteComp.isFlipX();
    }
    @Override
    protected void positionChanged () {
        super.positionChanged();
        hitBodyComponent.setPosition(bodyComponent.body.getPosition());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        setPosition(bodyComponent.getPosition().x, bodyComponent.getPosition().y);
        spriteComp.updatePosition(getX(),getY());
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean remove() {
        hitBodyComponent.detachBody();
        return super.remove();
    }
}
