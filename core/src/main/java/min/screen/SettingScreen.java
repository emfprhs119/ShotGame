package min.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import min.manager.Assets;
import min.MainGame;

public class SettingScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    Window window;

    public SettingScreen(MainGame game) {
        stage = new Stage(new FitViewport(640, 480), game.getBatch());
        skin = Assets.getAsset(Assets.UI_SKIN,Skin.class);//new Skin(Gdx.files.internal("ui/uiskin.json"));

        window = new Window("SETTING", skin, "border");
        window.defaults().pad(4f);
        window.add("This is Setting.");
        window.row();
        TextButton button;
        button = new TextButton("Save", skin);
        button.pad(8f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.changeScreen(MainGame.SCREEN.TITLE);
            }
        });
        window.add(button);
        button = new TextButton("Cancel", skin);
        button.pad(8f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.changeScreen(MainGame.SCREEN.TITLE);
            }
        });
        window.add(button);

        window.pack();
        window.setPosition(stage.getWidth() / 2f - window.getWidth() / 2f,
                stage.getHeight() / 2f - window.getHeight() / 2f);
        stage.addActor(window);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.5f)));
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    /** Clean screen on black color between render frames */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        clearScreen();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
