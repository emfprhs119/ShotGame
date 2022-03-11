package min.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Box2dManager {
    public static final short CATEGORY_INTERACT = 0x0001; // 1 in binary
    public static final short CATEGORY_PLAYER = 0x0002; // 10 in binary
    public static final short CATEGORY_PLAYER_AREA = 0x0004; // 10 in binary
    public static final short CATEGORY_MONSTER = 0x0008; // 10 in binary
    public static final short CATEGORY_BULLET = 0x0010; // 1000 in binary
    public static final short CATEGORY_MAP_BOUND = 0X0020; // 10000 in binary
    public static final short CATEGORY_FISH = 0x0020; // 100000 in binary
    public static final short CATEGORY_MONSTER_MOUTH = 0x0040; // 1000000 in binary

    public static final short MASK_PLAYER = CATEGORY_MONSTER | CATEGORY_INTERACT | CATEGORY_MAP_BOUND; // or ~CATEGORY_PLAYER
    public static final short MASK_PLAYER_AREA = CATEGORY_INTERACT; // or ~CATEGORY_PLAYER
    public static final short MASK_MONSTER = CATEGORY_PLAYER | CATEGORY_INTERACT | CATEGORY_MAP_BOUND; // or ~CATEGORY_MONSTER
    public static final short MASK_SCENERY = -1;

    Box2DDebugRenderer b2dr;
    World world;

    public Box2dManager(Stage stage){
        world = new World(new Vector2(), true);
        b2dr = new Box2DDebugRenderer();
        stage.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                world.step(delta,1,0);
                return false;
            }
        });
    }

    public World getWorld() {
        return world;
    }

    public void render(Camera camera){
        b2dr.render(world,camera.combined);
    }

    public void dispose() {
        b2dr.dispose();
        world.dispose();
    }

    public void destroy(Body body) {
        if (body != null)
            world.destroyBody(body);
    }

}
