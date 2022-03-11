package min.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import min.data.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager {
    private static class LazyHolder {
        private static final ItemManager itemManager = new ItemManager();
    }
    public static ItemManager getInstance(){
        return ItemManager.LazyHolder.itemManager;
    }
    private ItemManager(){
        itemHashMap = new HashMap<>();
    }
    ArrayList<Item> items;
    HashMap<String,Item> itemHashMap;

    public static ArrayList<Item> getItems() {
        return getInstance().items;
    }

    public static Item generateItemFromId(String id){
        return getInstance().itemHashMap.get(id);
    };

    public static void loadItemsFromJSON() {
        Json json = new Json();
        getInstance().items = json.fromJson(ArrayList.class, Item.class, Gdx.files.internal("items/items_equipment.json").reader("UTF8"));
        for (Item item:getInstance().items) {
            getInstance().itemHashMap.put(item.getId(),item);
        }
        getInstance().items = json.fromJson(ArrayList.class, Item.class, Gdx.files.internal("items/items_potion.json").reader("UTF8"));
        for (Item item:getInstance().items) {
            getInstance().itemHashMap.put(item.getId(),item);
        }
        getInstance().items = json.fromJson(ArrayList.class, Item.class, Gdx.files.internal("items/items_material.json").reader("UTF8"));
        for (Item item:getInstance().items) {
            getInstance().itemHashMap.put(item.getId(),item);
        }
    }

}
