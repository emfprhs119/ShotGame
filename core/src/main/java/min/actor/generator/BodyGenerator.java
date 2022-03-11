package min.actor.generator;

import com.badlogic.gdx.physics.box2d.*;
import min.manager.Box2dManager;

import static min.manager.Box2dManager.CATEGORY_INTERACT;

public class BodyGenerator {

    public static Body generateStaticBuilding(World world) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        CircleShape circle = new CircleShape();
        circle.setRadius(5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = CATEGORY_INTERACT;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = circle;
        circle.dispose();
        Body body = world.createBody(bdef);
        Fixture fixture = body.createFixture(fixtureDef);
        return body;
    }


    public static Body playerGenerateAreaBody(World world){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        CircleShape circle = new CircleShape();
        circle.setRadius(12f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        Body areaBody = world.createBody(bdef);
        Fixture fixture = areaBody.createFixture(fixtureDef);
        //fixture.setSensor(false);
        fixture.setUserData("playerArea");
        circle.dispose();
        return areaBody;
    }

    public static Body playerGenerateBody(World world){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);
        fixtureDef.shape = circle;
        body.setBullet(true);
        Fixture fixture = body.createFixture(fixtureDef);
        circle.dispose();
        //body.setTransform(getX(),getY(),0);
        return body;
    }
    /*
    public void initBody(World world) {
        super.initBody(world);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        CircleShape circle = new CircleShape();
        circle.setRadius(12f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        areaBody = world.createBody(bdef);
        Fixture fixture = areaBody.createFixture(fixtureDef);
        //fixture.setSensor(false);
        fixture.setUserData("playerArea");
        circle.dispose();
        areaBody.setTransform(getX(),getY(),0);
    }
    */


    void bodyFilter(Filter filter) {
        filter.categoryBits = Box2dManager.CATEGORY_PLAYER;
        filter.maskBits = Box2dManager.MASK_PLAYER;
        //areaBody.getFixtureList().get(0).getFilterData().categoryBits = Box2dManager.CATEGORY_PLAYER_AREA;
        //areaBody.getFixtureList().get(0).getFilterData().maskBits = Box2dManager.MASK_PLAYER_AREA;
    }
}
