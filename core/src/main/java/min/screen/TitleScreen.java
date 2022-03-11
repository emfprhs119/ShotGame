package min.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import min.manager.Assets;
import min.MainGame;
import min.manager.ItemManager;

public class TitleScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    Window window;

    public TitleScreen(MainGame game) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        stage = new Stage(new FitViewport(w/h*240, 240), game.getBatch());
        //stage = new Stage(new FitViewport(640/2, 480/2), game.getBatch());
        Skin bskin = Assets.getAsset(Assets.UI_SKIN,Skin.class);//
        skin =  new Skin(Gdx.files.internal("skin/plain-james-ui.json"));

        window = new Window("TITLE", skin, "default");
        window.defaults().pad(4f);
        //window.add("This is title.");
        Label label = new Label("This is title.",skin);
        label.setFontScale(0.5f);
        window.add(label);
        window.row();
        TextButton button;
        skin.setScale(0.1f);
        button = new TextButton("GAME START", skin);
        button.getLabel().setScale(0.5f);
        button.pad(3f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.changeScreen(MainGame.SCREEN.GAME);
            }
        });
        window.add(button);
        window.row();
        button = new TextButton("SETTING", skin);
        button.pad(8f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.changeScreen(MainGame.SCREEN.SETTING);
            }
        });
        window.add(button);

        window.row();
        button = new TextButton("TEST", skin);
        button.pad(8f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.changeScreen(MainGame.SCREEN.TEST);
            }
        });
        window.add(button);

        window.pack();
        window.setPosition(stage.getWidth() / 2f - window.getWidth() / 2f,
                stage.getHeight() / 2f - window.getHeight() / 2f);

        Group group = new Group();
        group.addActor(window);

        stage.addActor(group);
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
