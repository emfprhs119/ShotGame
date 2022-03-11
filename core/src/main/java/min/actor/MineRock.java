package min.actor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.actor.component.SpriteComponent;
import min.actor.generator.BodyGenerator;
import min.manager.Box2dManager;

public class MineRock extends IBasicActor implements IInteraction{
    public MineRock(Stage stage, World world) {
        super(stage, world);
        state.init(10);
    }

    @Override
    Body getInitBody(World world) {
        return BodyGenerator.generateStaticBuilding(world);
    }

    @Override
    Filter getInitBodyFilter() {
        Filter filter = new Filter();
        filter.categoryBits = Box2dManager.CATEGORY_INTERACT;
        filter.maskBits = -1;
        return filter;
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        spriteComp.updatePosition(getX(),getY());
    }

    @Override
    public boolean interactionWithPlayer(Player player) {
        player.onceAction(SpriteComponent.SpriteAction.MINING,this);
        return true;
    }
}
