package min.input;

import com.badlogic.gdx.math.Vector2;
import min.actor.ToolsEffect;
import min.actor.IInteraction;
import min.actor.Player;
import min.actor.component.SpriteComponent;

public class ActionForPlayer implements KeyMapListener {
    Player player;
    public ActionForPlayer(Player player){
        this.player = player;
    }

    @Override
    public boolean act(float delta, Vector2 axis, int buttons, InputAction inputAction) {
        player.moveByAxis(delta,axis);
        if ((buttons & GameInput.Key0) > 0 && !player.isAction()){
            return interaction(inputAction);
        }if ((buttons & GameInput.Key2) > 0){
            inputAction.showInventory(player.getPlayerData().getItems());
            //inputAction.showEquipments(player.getPlayerData().getItems());
            return true;
        }if ((buttons & GameInput.Key3) > 0 && !player.isAction()){
            return player.onceAction(SpriteComponent.SpriteAction.DASH, null);
        }
        return false;
    }

    public boolean interaction(InputAction inputAction){
        if (!player.isAction()) {
            Object[] contactActors = player.getAreaContactActors();

            for(Object actor:contactActors){
                if (actor instanceof IInteraction){
                    if (((IInteraction) actor).interactionWithPlayer(player)) {
                        return true;
                    }
                }
            }
            //if (target != null) {
                /*
                switch (target.getType()) {
                    case MINE:
                        player.onceAction(SpriteComponent.SpriteAction.MINING, target);
                        ((ActionEffect) (player.getStage().getRoot().findActor("ActionEffect"))).run(ActionEffect.ActionType.MINE, player);
                        return true;
                    case CRAFT:
                        inputAction.showCraftList(player.getPlayerData().getItems());
                        return true;
                    case CHEST:
                        inputAction.showChestBox(player.getPlayerData().getItems(), target);
                        return true;
                }

                 */
            /*
            }else{
                player.onceAction(SpriteComponent.SpriteAction.ATTACK, null);
            }
            */
        }
        return false;
    }
}
