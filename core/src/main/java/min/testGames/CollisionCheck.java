package min.testGames;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import min.manager.Assets;
import min.GameMap;

public class CollisionCheck extends Game {

    private OrthographicCamera camera;
    private SpriteBatch sBatch;
    private Texture player;
    private Texture enemy;
    //private SpriteBatch enemyBatch;
    private Sprite sprite1;
    private Sprite sprite2;
    //just setting my game heighty and width
    public static int gameWidth = 1280, gameHeight = 720;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    GameMap cmap;
    @Override
    public void create () {
        Assets.loadAll(new AssetManager());
        Assets.finishLoading();
        //camera related
        camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        //end of camera related
        sBatch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();

        //cmap = new WorldMap(null);
        /*
//BOX2D CODE FOR CREATING WWORLD
        world = new World(new Vector2(0, -0.1f), true);

        //creating box2d body definition
        BodyDef bodyDef = new BodyDef();
        //setting body type to dynamic
        bodyDef.type = BodyType.DynamicBody;
        //position
        bodyDef.position.set(0, 0);
        //sending what i just made to the world i created
        // making a circle with a radius of 6


        CircleShape circle = new CircleShape();
        circle.setRadius(30f);
        //making my fixtures for the circle
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        world.createBody(bodyDef).createFixture(fixtureDef);
        circle.dispose();


        RectangularShape rect = new RectShape();
        circle.setRadius(30f);
        //making my fixtures for the circle
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        world.createBody(bodyDef).createFixture(fixtureDef);
        circle.dispose();

        //GROUND START
        /*
        BodyDef bodyGround = new BodyDef();
        bodyGround.type = BodyType.StaticBody;
        bodyGround.position.set(-100,-100);
        //setting shape of ground
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {new Vector2(-250, 0), new Vector2(250, 0)});

        //fixtures for ground
        FixtureDef fixtureDefGround = new FixtureDef();
        fixtureDefGround.shape = groundShape;
        fixtureDefGround.friction = 0.5f;
        fixtureDefGround.restitution = 0;

        world.createBody(bodyGround).createFixture(fixtureDefGround);
        groundShape.dispose();
        */
    }

    public  void dispose() {
        //cmap.world.dispose();
    }

    @Override
    public void render () {
        super.render();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // camera related
        sBatch.setProjectionMatrix(camera.combined);

        //cmap.tiledMapRenderer.setView(camera);
        //cmap.tiledMapRenderer.render();
        //debugRenderer.render(cmap.world, camera.combined);

        //world.step(0.1f, 0, 0);
    }
    public  void resize(int width, int height){
    }

    public  void pause(){
    }

    public  void resume(){
    }
}