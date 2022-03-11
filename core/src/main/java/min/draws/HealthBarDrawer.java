package min.draws;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import min.data.State;
import min.manager.Assets;

public class HealthBarDrawer extends BaseDrawable {
    State state;
    Vector2 position;
    Texture bar;
    float maxBarWidth;
    float barHeight;

    public HealthBarDrawer(State state){
        this.state = state;
        bar = Assets.healthPixel;
        position = new Vector2();
        maxBarWidth = 14;
        barHeight = 3;
    }

    @Override
    public void draw (Batch batch, float x, float y, float width, float height) {
        batch.draw(bar,x,y,width,height);
    }

    public void draw(Batch batch){
        draw(batch,position.x,position.y,maxBarWidth*state.getHealthPointPercent(),barHeight);
    }

    public void setPosition(float x, float y) {
        position.set(x-maxBarWidth/2,y-8);
    }
}
