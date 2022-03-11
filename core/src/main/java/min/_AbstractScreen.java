package min;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class _AbstractScreen extends ScreenAdapter {
    protected Game game;
    protected Stage stage;
    private OrthographicCamera camera;
    protected SpriteBatch spriteBatch;

    public _AbstractScreen(Game game) {
        this.game = game;
        createCamera();
        /* Stage for actors */
        stage = new Stage(new StretchViewport(1,1, camera));
        /* Batch for sprites */
        spriteBatch = new SpriteBatch();
        /* Stage takes user inputs */
        Gdx.input.setInputProcessor(stage);
        init();
    }

    protected abstract void init();

    private void createCamera() {
        /* Orthographic means like in CAD drawings */
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1,1);
        camera.update();
    }

    /** Clean screen on black color between render frames */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

}