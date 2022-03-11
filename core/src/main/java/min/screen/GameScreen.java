package min.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import min.*;
import min.actor.IMoveActor;
import min.actor.Player;
import min.input.InputAction;
import min.manager.Assets;
import min.manager.Box2dManager;
import min.manager.BulletPool;
import min.manager.Initializer;

import java.util.Arrays;

public class GameScreen extends ScreenAdapter {
    ShapeRenderer sr = new ShapeRenderer();
    private OrthographicCamera hudCamera;
    private BitmapFont font;
    boolean DEBUG = true;

    private final Stage stage;
    private final Stage stageUI;
    private final OrthographicCamera camera;
    private final Vector3 cameraPos;
    private final GameMap gameMap;
    Box2dManager b2dm;
    Player player;
    private InputAction inputAction;
    final BulletPool bp = new BulletPool();
    private Rectangle viewBounds;
    public GameScreen(MainGame game) {
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
        font = new BitmapFont(Gdx.files.internal("ui/NEXON Lv1 Gothic Low OTF_12.fnt"));

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        cameraPos = new Vector3();
        stage = new Stage(new FitViewport(w/h*240, 240, camera), game.getBatch());

        OrthographicCamera cameraUI = new OrthographicCamera();
        Matrix4 uiMatrix = cameraUI.combined;
        uiMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stageUI = new Stage(new FitViewport(w/h*240*2, 240*2, cameraUI), game.getBatch());

        b2dm = new Box2dManager(stage);
        Initializer.initObject(stage,b2dm.getWorld());
        Initializer.initObjectUI(stageUI);
        TiledMap map = Assets.getAsset(Assets.Tmxs.MAP_001, TiledMap.class);

        gameMap = new GameMap(map);

        inputAction = new InputAction();
        inputAction.init(stage,stageUI);
        stage.addAction(inputAction);

        gameMap.attachBody(b2dm.getWorld());
        /*
        for(Actor actor :stage.getActors()){
            if (actor instanceof IBodyActor) {
                ((IBodyActor)actor).attachBody(b2dm.getWorld());
            }
            if (actor instanceof IMoveActor) {
                ((IMoveActor) actor).setBulletPool(bp);
            }
        }

         */
        player = stage.getRoot().findActor("Player");
        player.setBulletPool(bp);
        inputAction.initFocus(player.getPlayerInputAction());
        stage.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                return false;
            }
        });
        viewBounds = new Rectangle();
    }

    public void setStageProcessor(Stage stage){
        Gdx.input.setInputProcessor(stage);
    }

    Vector2 debugV = new Vector2();
    @Override
    public void show(){
        stage.addListener(inputAction.getGameInput().getKeyListener());
        stage.addListener(new InputListener(){
            public boolean mouseMoved (InputEvent event, float x, float y) {
                debugV.set(x,y);
                return false;
            }
        });
        stage.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.log("Left Click",x+"_"+y);
            }
        });
        stage.addListener(new ClickListener(Input.Buttons.RIGHT){
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.log("Right Click",x+"_"+y);

            }
        });
        stageUI.addListener(inputAction.getGameInput().getKeyListener());
        Controllers.addListener(inputAction.getGameInput().getController());
        setStageProcessor(stage);
    }

    @Override
    public void hide(){
        Controllers.clearListeners();
        Gdx.input.setInputProcessor(null);
    }

    /** Clean screen on black color between render frames */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void setViewBounds(OrthographicCamera camera) {
        float width = camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;
        float w = width * Math.abs(camera.up.y) + height * Math.abs(camera.up.x);
        float h = height * Math.abs(camera.up.y) + width * Math.abs(camera.up.x);
        viewBounds.set(camera.position.x - w / 6 * 4, camera.position.y - h / 6*4, w/6*8, h/6*8);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        setViewBounds(camera);
        {
            gameMap.setView(camera);
            gameMap.render();
        }

        {
            bp.setView(stage);
            bp.setViewBounds(viewBounds);
            bp.render();
            bp.act(delta);
        }
        // z-index sort
        Actor[] actors = stage.getActors().toArray();
        Arrays.sort(actors, (o1, o2) -> Math.round(o2.getY()-o1.getY()));
        for (int i = 0; i < actors.length; i++) {
            actors[i].setZIndex(i);
            /*
            boolean result = viewBounds.contains(actors[i].getX(),actors[i].getY());
            actors[i].setVisible(result);
            if (actors[i] instanceof IBodyActor)
                ((IBodyActor)actors[i]).setActive(result);

             */
        }

        {
            cameraPos.x = (player.getX());
            cameraPos.y = (player.getY());
            camera.position.lerp(cameraPos, 0.6f);
        }

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        stageUI.draw();
        stageUI.act(Gdx.graphics.getDeltaTime());

        b2dm.render(camera);


        if (DEBUG) {
            /*
            sr.setColor(Color.BLACK);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setProjectionMatrix(camera.combined);
            sr.line(player.getX(),player.getY(),debugV.x,debugV.y);
            sr.end();
            */
            stage.getBatch().begin();
            font.draw(stage.getBatch(), "FPS " + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
            stage.getBatch().end();
        }
    }
    public float pixelToUnits = 40f;
    public float RoundToNearestPixel(float unityUnits)
    {
        float valueInPixels = unityUnits * pixelToUnits;
        valueInPixels = Math.round(valueInPixels);
        float roundedUnityUnits = valueInPixels * (1 / pixelToUnits);
        return roundedUnityUnits;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stageUI.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        stage.dispose();
        stageUI.dispose();
    }
}
