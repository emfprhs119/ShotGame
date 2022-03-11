package min.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import min.data.Item;
import min.manager.Assets;

public class ItemHolder extends SelectableHolder {
    Item item;
    Label stackLabel;
    float pad;

    ItemHolder(float size){
        super();
        stackLabel = new Label("", Assets.uiskin);
        stackLabel.setAlignment(Align.bottomRight);
        setSize(size,size);
        setPad(2);
    }

    void setPad(float pad){
        this.pad = pad;
    }

    public void setItem(Item item){
        this.item = item;
        stackLabel.setText(item.getStack());
    }

    @Override
    protected void positionChanged () {
        stackLabel.setPosition(getX() + pad, getY() + pad);
    }

    @Override
    protected void sizeChanged () {
        stackLabel.setSize(getWidth() - pad - pad, getHeight() - pad - pad);
    }

    public void clearToInit(){
        item = null;
    }

    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        if (item != null) {
            batch.draw(item.getImage(), getX() + pad, getY() + pad, getWidth() - pad - pad, getHeight() - pad - pad);
            if (item.getStack() > 1) {
                stackLabel.draw(batch, parentAlpha);
            }
        }
    }
}

