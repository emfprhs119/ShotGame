package min;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import min.manager.Assets;
import min.manager.Box2dManager;

import static min.manager.Box2dManager.CATEGORY_MAP_BOUND;

public class GameMap extends OrthogonalTiledMapRenderer {
    //public OrthogonalTiledMapRenderer tiledMapRenderer;
    World world;
    Body body;

    public GameMap(TiledMap map) {
        super(map);
    }

    private void initBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = CATEGORY_MAP_BOUND;
        //fdef.filter.maskBits = CATEGORY_MAP_BOUND;
        for(MapLayer layer: map.getLayers()) {
            for (RectangleMapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = object.getRectangle();
                bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
                body = world.createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                fdef.shape = shape;
                Fixture fixture = body.createFixture(fdef);
            }
        }
    }

    public void attachBody(World world){
        this.world = world;
        initBody(world);
    }

    @Override
    public void dispose(){
        world.destroyBody(body);
        super.dispose();
    }

    public TiledMap getTiledMap(){
        return map;
    }
}
