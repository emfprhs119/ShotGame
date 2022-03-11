package min.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import min.actor.IBasicActor;
import min.data.CraftReceipt;
import min.data.PlayerItems;
import min.ui.*;

import java.util.Stack;

public class InputAction extends Action {
    GameInput gameInput;
    Stage stage;
    Stage stageUI;
    Stack<UIObj> uiStack;
    boolean isRun;
    Stack<KeyMapListener> focusStack;
    short pressButtonList;
    public InputAction(){
        gameInput = new GameInput();
        focusStack = new Stack<>();
        uiStack = new Stack<>();
        isRun = false;
    }

    public KeyMapListener getFocus(){
        return focusStack.peek();
    }

    public void initFocus(KeyMapListener focus) {
        focusStack.push(focus);
    }

    public GameInput getGameInput() {
        return gameInput;
    }

    public void init(Stage stage,Stage stageUI){
        this.stage = stage;
        this.stageUI = stageUI;
    }


    @Override
    public boolean act(float delta) {
        boolean isExec;
        Vector2 axis = gameInput.getAxis();
        int buttons = gameInput.getPressButtons();
        buttons &= ~pressButtonList;
        if ((buttons & GameInput.Key1) > 0) {
            isExec = cancel();
        } else{
            isExec = getFocus().act(delta, axis, buttons, this);
        }
        if (isExec) {
            int finalButtons = buttons;
            pressButtonList |= buttons;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    pressButtonList &= ~finalButtons;
                    isRun = false;
                }
            }, 0.3f);
        }
        return false;
    }

    public boolean cancel(){
        return popUI();
    }

    public boolean popUI(){
        if (uiStack.size() == 1)
            Gdx.input.setInputProcessor(stage);
        if (!uiStack.empty()) {
            uiStack.pop().setVisible(false);
            focusStack.pop();
            return true;
        }
        return false;
    }

    public void pushUI(UIObj uiObj) {
        if (uiStack.contains(uiObj))
            return;
        uiStack.push(uiObj);
        focusStack.push(uiObj);
        uiObj.toFront();
        uiObj.setVisible(true);
        Gdx.input.setInputProcessor(stageUI);
    }

    public void showInventory(PlayerItems items){
        Inventory inventory = stageUI.getRoot().findActor("Inventory");
        inventory.setMode(Inventory.Mode.INVENTORY);
        inventory.setItems(items);
        pushUI(inventory);
    }
    public void showCraftList(PlayerItems items){
        CraftList craftList = stageUI.getRoot().findActor("CraftList");
        craftList.setItems(items);
        craftList.setAction(this);
        pushUI(craftList);
    }
    public void showCraftConfirm(PlayerItems items, CraftReceipt receipt) {
        CraftConfirm craftConfirm = stageUI.getRoot().findActor("CraftConfirm");
        craftConfirm.setItems(items);
        craftConfirm.setReceipt(receipt);
        pushUI(craftConfirm);
        craftConfirm.toFront();
    }

    public void showChestBox(PlayerItems items, IBasicActor target) {
        Inventory inventory = stageUI.getRoot().findActor("Inventory");
        inventory.setMode(Inventory.Mode.CHEST);
        inventory.setItems(items);
        pushUI(inventory);
    }
}
