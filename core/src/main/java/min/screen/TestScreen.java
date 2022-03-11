package min.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.MainGame;
import min.ui.ItemWithLabelHolder;

public class TestScreen extends ScreenAdapter {
    Stage stage;

    public TestScreen(MainGame game){
        stage = new Stage();
        ItemWithLabelHolder itemLabelHolder = new ItemWithLabelHolder(36,300,500);
        itemLabelHolder.setBounds(24,24,100,36);
        stage.addActor(itemLabelHolder);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
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
    }
}
