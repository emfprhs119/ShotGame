package min;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import min.manager.Assets;
import min.manager.ItemManager;
import min.screen.GameScreen;
import min.screen.SettingScreen;
import min.screen.TestScreen;
import min.screen.TitleScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
	public enum SCREEN{TITLE,GAME,SETTING,TEST}
	private Screen titleScreen;
	private Screen gameScreen;
	private Screen testScreen;
	private Screen settingScreen;
	private SpriteBatch spriteBatch;

	@Override
	public void create() {
		// for Debug Item Load
		ItemManager.loadItemsFromJSON();
		spriteBatch = new SpriteBatch();
		Assets.loadAll(new AssetManager());
		Assets.finishLoading();
		initScreen();
		setScreen(gameScreen);
	}

	public SpriteBatch getBatch(){
		return spriteBatch;
	}

	public void changeScreen(SCREEN screenType){
		switch (screenType){
			case TITLE: setScreen(titleScreen); break;
			case GAME: setScreen(gameScreen); break;
			case SETTING: setScreen(settingScreen); break;
			case TEST: setScreen(testScreen); break;
		}
	}

	private void initScreen() {
		titleScreen = new TitleScreen(this);
		settingScreen = new SettingScreen(this);
		gameScreen = new GameScreen(this);
		testScreen = new TestScreen(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		spriteBatch.dispose();
		titleScreen.dispose();
		settingScreen.dispose();
		gameScreen.dispose();
		testScreen.dispose();
	}
}