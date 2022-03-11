package min.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import min.actor.Bullet;

public class BulletPool extends Pool<Bullet> {
    protected Rectangle viewBounds;
    Batch batch;
    Animation<TextureRegion> animation;
    final Array<Bullet> activeBullets = new Array<Bullet>();

    public BulletPool(){
        super();
        initAnimation();
        viewBounds = new Rectangle();
    }

    public void render() {
        batch.begin();
        batch.setColor(Color.PINK);
        for(Bullet bullet:activeBullets){
            if (viewBounds.contains(bullet.getPosition())) {
                bullet.draw(batch);
                bullet.setActive(true);
            }else{
                bullet.setActive(false);
            }
        }
        batch.setColor(Color.WHITE);
        batch.end();
    }

    public void act(float delta) {
        for(Bullet bullet:activeBullets){
            bullet.act(delta);
        }
    }

    @Override
    public void free(Bullet bullet) {
        activeBullets.removeValue(bullet,true);
        super.free(bullet);
    }

    @Override
    public Bullet obtain () {
        Bullet bullet = super.obtain();
        bullet.setAnimation(animation);
        activeBullets.add(bullet);
        return bullet;
    }

    @Override
    protected Bullet newObject() {
        return new Bullet(this);
    }

    void initAnimation(){
        Texture sheet = new Texture(Gdx.files.internal("Downloads/spark_sprite.png"));
        TextureRegion[] textureRegions;
        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,9);
        animation = new Animation<TextureRegion>(0.2f,textureRegions);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void setView (Stage stage) {
        this.batch = stage.getBatch();
        this.batch.setProjectionMatrix(stage.getCamera().combined);
    }

    public void setViewBounds(Rectangle viewBounds) {
        this.viewBounds = viewBounds;
    }
}
