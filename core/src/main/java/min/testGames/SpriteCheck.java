package min.testGames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import min.actor.Player;

public class SpriteCheck extends Game {
        private SpriteBatch spriteBatch;
        Player player;

        @Override
        public void create() {
            spriteBatch = new SpriteBatch();
            //player = new Player();
        }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        player.draw(spriteBatch,0);
        spriteBatch.end();
    }

    @Override
        public void dispose() {
            super.dispose();
            spriteBatch.dispose();
        }
    }