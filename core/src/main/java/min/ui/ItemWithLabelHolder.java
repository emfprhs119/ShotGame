package min.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import min.data.Item;
import min.manager.Assets;

public class ItemWithLabelHolder extends Group {
    enum LAYOUT {Horizontal, Vertical}
    LAYOUT layout;
    boolean isShowDescription;
    ItemHolder itemHolder;
    SelectableHolder labelBackgroundHolder;
    Label label;

    public ItemWithLabelHolder(float itemSize, float width,float height){
        this(itemSize, width,height,LAYOUT.Horizontal,false);
    }

    public ItemWithLabelHolder(float itemSize, float width,float height,LAYOUT layout,boolean isShowDescription) {
        this.layout = layout;
        this.isShowDescription = isShowDescription;
        itemHolder = new ItemHolder(itemSize);
        labelBackgroundHolder = new SelectableHolder();
        label = new Label("", Assets.uiskin);
        if (layout == LAYOUT.Vertical)
            label.setAlignment(Align.center);
        setSize(width,height);
        addActor(itemHolder);
        //addActor(labelBackgroundHolder);
        //addActor(label);
    }

    public void setItem(Item item){
        itemHolder.setItem(item);
        label.setText(item.getName()+(!isShowDescription?"":"\n\n"+item.getDescription()));
    }

    public void clearToInit(){
        itemHolder.clearToInit();
        label.setText("");
    }

    public void toFront(){
        super.toFront();
        itemHolder.toFront();
        labelBackgroundHolder.toFront();
        label.toFront();
    }

    public void toBack(){
        super.toBack();
        itemHolder.toBack();
        labelBackgroundHolder.toBack();
        label.toBack();
    }

    @Override
    protected void positionChanged () {
        if (layout == LAYOUT.Horizontal) {
            itemHolder.setPosition(getX(),getY());
            labelBackgroundHolder.setPosition(getX() + getHeight(), getY());
            label.setPosition(getX() + getHeight() + (getHeight() - label.getPrefHeight()) / 2f, getY());
        }else if (layout == LAYOUT.Vertical){
            itemHolder.setPosition(getX()+getWidth()/2-itemHolder.getWidth()/2,getY()+getHeight()-itemHolder.getHeight());
            labelBackgroundHolder.setPosition(getX(),getY());
            label.setPosition(getX(),getY());
        }
    }

    @Override
    protected void sizeChanged () {
        if (layout == LAYOUT.Horizontal) {
            labelBackgroundHolder.setBounds(getX() + getHeight(), getY(), getWidth() - getHeight(), getHeight());
            label.setBounds(getX() + getHeight() + (getHeight() - label.getPrefHeight()) / 2f, getY(), getWidth() - getHeight(), getHeight());
        }else if (layout == LAYOUT.Vertical){
            itemHolder.setPosition(getX()+getWidth()/2-itemHolder.getWidth()/2,getY()+getHeight()-itemHolder.getHeight());
            labelBackgroundHolder.setSize(getWidth(),getHeight()-itemHolder.getHeight());
            label.setSize(getWidth(),getHeight()-itemHolder.getHeight());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        itemHolder.draw(batch,parentAlpha);
        labelBackgroundHolder.draw(batch,parentAlpha);
        label.draw(batch,parentAlpha);
    }
}
