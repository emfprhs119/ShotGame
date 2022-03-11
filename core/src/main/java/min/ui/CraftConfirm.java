package min.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import min.data.CraftReceipt;
import min.data.Item;
import min.data.PlayerItems;
import min.input.InputAction;
import min.manager.Assets;
import min.manager.ItemManager;

public class CraftConfirm extends UIObj {
    CraftReceipt receipt;
    ItemWithLabelHolder target;
    HorizontalGroup materialGroup;
    PlayerItems items;

    @Override
    void layoutConfiguration() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Table window = new Table();
        window.setBackground(Assets.uiskin.getDrawable("textfield-selected"));
        window.pad(10f);
        VerticalGroup verticalGroup = new VerticalGroup();

        target = new ItemWithLabelHolder(36,180,150 , ItemWithLabelHolder.LAYOUT.Vertical,true);
        verticalGroup.addActor(target);
        materialGroup = new HorizontalGroup();
        for (int i = 0; i < 3; i++) {
            materialGroup.addActor(new ItemHolder(36));
        }
        materialGroup.pack();
        verticalGroup.addActor(materialGroup);

        TextButton btn = new TextButton("제작",skin);
        btn.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                getItems().add(ItemManager.generateItemFromId(getReceipt().getItem().getId()));
            }
        });
        verticalGroup.addActor(btn);

        verticalGroup.pack();
        window.add(verticalGroup);
        window.pack();
        addActor(window);
        setPosition(Gdx.graphics.getWidth() / 2f - window.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - window.getHeight() / 2f);
    }

    public void setReceipt(CraftReceipt receipt){
        this.receipt = receipt;
        refresh();
    }

    public CraftReceipt getReceipt() {
        return receipt;
    }

    public void setItems(PlayerItems items) {
        this.items = items;
    }

    public PlayerItems getItems() {
        return items;
    }

    public void refresh(){
        target.setItem(receipt.getItem());
        Item[] items = receipt.getMaterials();
        for (int i = 0; i < materialGroup.getChildren().size; i++) {
            if (items[i] != null) {
                ((ItemHolder) materialGroup.getChild(i)).setItem(items[i]);
                materialGroup.getChild(i).setVisible(true);
            }
            else
                materialGroup.getChild(i).setVisible(false);
        }
    }

    @Override
    public boolean act(float delta, Vector2 axis, int buttons, InputAction inputAction) {
        return false;
    }
}
