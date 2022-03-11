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

import java.util.Vector;

public class CraftList extends UIObj {
    ItemWithLabelHolder[] holders;
    Vector<CraftReceipt> receipts;
    PlayerItems items;

    public CraftList(){
        super();
        generateFakeData();
    }

    public void setItems(PlayerItems items) {
        this.items = items;
        for(ItemWithLabelHolder holder:holders){
            holder.clearToInit();
        }
        for (int i = 0; i < receipts.size(); i++) {
            CraftReceipt receipt = receipts.get(i);
            ItemWithLabelHolder holder = holders[i];
            holder.setUserObject(receipt);
            holder.setItem(receipts.get(i).getItem());
        }
    }

    public PlayerItems getItems() {
        return items;
    }

    public void generateFakeData(){
        Item[] materials = new Item[3];
        materials[0] = ItemManager.generateItemFromId("100_0");
        materials[1] = ItemManager.generateItemFromId("100_0");
        receipts.add(new CraftReceipt(ItemManager.generateItemFromId("100_0"),materials));

        materials = new Item[3];
        materials[0] = ItemManager.generateItemFromId("204_1");
        materials[1] = ItemManager.generateItemFromId("204_0");
        materials[2] = ItemManager.generateItemFromId("000_1");
        receipts.add(new CraftReceipt(ItemManager.generateItemFromId("204_1"),materials));
    }

    @Override
    void layoutConfiguration() {
        holders = new ItemWithLabelHolder[10];
        receipts = new Vector<>();
        Table window = new Table();
        window.pad(10f);
        window.setBackground(Assets.uiskin.getDrawable("textfield-selected"));
        //window.debug();
        VerticalGroup verticalGroup = new VerticalGroup();
        for (int i = 0; i < holders.length; i++) {
            holders[i] = new ItemWithLabelHolder(36,200,36);
            holders[i].addListener(new ClickListener(){
                public void clicked (InputEvent event, float x, float y) {
                    if (event.getListenerActor().getUserObject() != null) {
                        getInputAction().showCraftConfirm(getItems(), (CraftReceipt) event.getListenerActor().getUserObject());
                    }
                }
            });
            verticalGroup.addActor(holders[i]);
        }
        verticalGroup.pack();
        window.add(verticalGroup);
        window.pack();
        window.setPosition(
                Gdx.graphics.getWidth() / 4f - window.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - window.getHeight() / 2f);
        addActor(window);
    }

    InputAction inputAction;

    public InputAction getInputAction() {
        return inputAction;
    }

    public void setAction(InputAction inputAction) {
        this.inputAction = inputAction;
    }

    @Override
    public boolean act(float delta, Vector2 axis, int buttons, InputAction inputAction) {
        return false;
    }

}
