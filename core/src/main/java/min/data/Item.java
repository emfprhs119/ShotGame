package min.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import min.manager.Assets;

public class Item implements Json.Serializable {
    private String id;
    private String name;
    private int kind;
    private String imageKey;
    private String description;
    private int stack;

    public Item(){
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void addStack(int i) {
        stack+=i;
    }
    public int getStack() {
        return stack;
    }

    public int getKind() {
        return kind;
    }

    public String getDescription() {
        return description;
    }

    public String getImageKey() {
        return imageKey;
    }

    public TextureRegion getImage(){
        return Assets.imageMap.get(imageKey);
    }

    @Override
    public void write(Json json) {

    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        json.readFields(this, jsonData);
        stack = 1;
    }

    static public final int material = 0;

    static public final int potion = 100;
    static public final int equipment = 200;
    static public final int equipment_helmet = 201;
    static public final int equipment_armor = 202;
    static public final int equipment_leggings = 203;
    static public final int equipment_shoes = 204;

    static public final int equipment_sward = 301;

}
