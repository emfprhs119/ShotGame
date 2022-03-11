package min.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import min.actor.generator.BodyGenerator;

public class CraftBuilding extends IBasicActor {
    public CraftBuilding(Stage stage, World world) {
        super(stage,world);
        //this.type = InteractionType.CRAFT;
        //this.mineKind = MineObj.MineKind.ROCK;
        Texture sheet = new Texture(Gdx.files.internal("Downloads/overworld.png"));
        TextureRegion tmp[][] = TextureRegion.split(sheet, 16, 16);
        //textureRegion = tmp[5][15];
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
        return "wowCraft";
    }
}
