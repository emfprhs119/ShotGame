package min.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.actor.generator.BodyGenerator;
import min.data.Item;

public class ChestBox extends IBasicActor {
    Item[] items;
    int size = 8;
    public ChestBox(Stage stage, World world) {
        super(stage,world);
        //this.type = InteractionType.CHEST;
        Texture sheet = new Texture(Gdx.files.internal("Downloads/overworld.png"));
        TextureRegion tmp[][] = TextureRegion.split(sheet, 16, 16);
        //textureRegion = tmp[5][17];
        items = new Item[100];
    }


    @Override
    Body getInitBody(World world) {
        return BodyGenerator.generateStaticBuilding(world);
    }

    @Override
    Filter getInitBodyFilter() {
        return null;
    }

    public String getInfo(){
        return "wowChestBox";
    }
}
