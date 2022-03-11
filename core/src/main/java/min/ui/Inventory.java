package min.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import min.data.Item;
import min.data.PlayerItems;
import min.input.GameInput;
import min.input.InputAction;

public class Inventory extends UIObj {
    public enum Mode{INVENTORY,CHEST}
    Mode mode;
    static final int rows = 6;
    static final int cols = 6;
    int tabIndex = 0;
    Group invenGrp;
    Group equipGrp;
    Group boxGrp;
    ItemHolder[] invenHolder;
    ItemHolder[] equipHolder;
    ItemHolder[] boxHolder;

    @Override
    void layoutConfiguration() {
        boxHolder = new ItemHolder[8*8];
        LayoutConfig.generateGrid(boxHolder,8, 8);
        invenGrp = new VerticalGroup();

        invenHolder = new ItemHolder[rows*cols];
        invenGrp.addActor(generateTabBar());
        invenGrp.addActor(LayoutConfig.generateGrid(invenHolder,rows, cols));
        ((VerticalGroup)invenGrp).pack();
        invenGrp.setPosition(-invenGrp.getWidth()/2,-invenGrp.getHeight() / 2f);
        addActor(invenGrp);

        equipHolder = new ItemHolder[3*2];
        equipGrp = LayoutConfig.generateGrid(equipHolder,3, 2);
        equipGrp.setPosition(-Gdx.graphics.getWidth()/4f-equipGrp.getWidth()/2,-equipGrp.getHeight() / 2f);
        addActor(equipGrp);

        boxHolder = new ItemHolder[4*6];
        boxGrp = LayoutConfig.generateGrid(boxHolder,3, 5);
        boxGrp.setPosition(Gdx.graphics.getWidth()/4f-boxGrp.getWidth()/2,-boxGrp.getHeight() / 2f);
        boxGrp.setVisible(false);
        addActor(boxGrp);

        MenuButton btn = new MenuButton();
        btn.getList().setPosition(Gdx.graphics.getWidth()/4f,Gdx.graphics.getHeight()/4f);
        addActor(btn.getList());

        setPosition(Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/2f);

        setMode(Mode.INVENTORY);
    }

    class RadioButtonListener extends ClickListener{
        ButtonGroup<TextButton> group;
        RadioButtonListener(ButtonGroup<TextButton> group){
            this.group = group;
            for (TextButton btn:group.getButtons()) {
                Label label = btn.getLabel();
                btn.removeActor(label);
                btn.addListener(this);
            }
        }
        public void clicked (InputEvent event, float x, float y) {
            group.uncheckAll();
            ((Button)event.getListenerActor()).setChecked(true);
            tabIndex = group.getCheckedIndex();
            refresh();
        }
    }

    private Group generateTabBar() {
        HorizontalGroup tabGroup = new HorizontalGroup();
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        CheckBox[] btns = new CheckBox[3];
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new CheckBox((i)+"",skin);
            tabGroup.addActor(btns[i]);
        }
        new RadioButtonListener(new ButtonGroup<>(btns));
        btns[0].toggle();
        tabGroup.pack();
        return tabGroup;
    }

    public void setItems(PlayerItems items) {
        setUserObject(items);
        refresh();
    }

    public void refresh(){
        Item[] items = ((PlayerItems)getUserObject()).getInventory(tabIndex);
        for (int i = 0; i < invenHolder.length ; i++) {
            if (items[i] == null)
                invenHolder[i].clearToInit();
            else
                invenHolder[i].setItem(items[i]);
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        boxGrp.setVisible(mode == Mode.CHEST);
    }

    @Override
    public boolean act(float delta, Vector2 axis, int buttons, InputAction inputAction) {
        if ((buttons & GameInput.Key2) > 0){
            inputAction.popUI();
            //inputAction.showEquipments(player.getPlayerData().getItems());
            return true;
        }
        return false;
    }
}
