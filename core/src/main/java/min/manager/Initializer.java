package min.manager;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.actor.*;
import min.ui.CraftConfirm;
import min.ui.CraftList;
import min.ui.Inventory;

public class Initializer {
    public static void initObject(Stage stage, World world){
        Player player = new Player(stage, world);
        player.setInitPosition(300,200);
        /*
        Monster monster = new Monster(stage);
        monster.setPosition(300,250);
        Monster monster1 = new Monster(stage);
        monster1.setPosition(300,280);
        Monster monster2 = new Monster(stage);
        monster2.setPosition(260,250);
        */
        /*
        for(int i=0;i<0;i++){

            Monster monster = new Monster(stage, world);
            monster.setPosition(280+(i/10)*50,220+(i%10)*24);
        }
        */

        for(int i=0;i<4;i++){
            IBasicActor obj = new MineRock(stage, world);
            obj.setInitPosition(220+16*(i/4),150+16*(i%4));
        }



        /*
        IBasicActor craftBuilding = new CraftBuilding(stage, world);
        craftBuilding.setPosition(250,250);

        ChestBox chestBox = new ChestBox(stage, world);
        chestBox.setPosition(270,250);
        */
        /*
        Group effectGroup = new Group();
        ToolsEffect toolsEffect = new ToolsEffect();
        effectGroup.addActor(toolsEffect);
        stage.addActor(effectGroup);
        */
    }

    public static void initObjectUI(Stage stageUI){
        CraftConfirm craftConfirm = new CraftConfirm();
        stageUI.addActor(craftConfirm);

        CraftList craftList = new CraftList();
        stageUI.addActor(craftList);

        Inventory inventory = new Inventory();
        stageUI.addActor(inventory);

    }
}
