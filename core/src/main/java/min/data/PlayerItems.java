package min.data;

import java.util.Objects;
import java.util.Vector;

public class PlayerItems {
    Vector<Item> equipmentItem;
    Vector<Item> materialItem;
    Item[][] inventory;
    public PlayerItems(){
        equipmentItem = new Vector<>();
        materialItem = new Vector<>();
        inventory = new Item[3][100];   // tab_items
    }

    public void add(Item item) {
        int type = -1;
        if (item.getKind()/100 > 1)
            type = 0;
        else if (item.getKind()/100 > 0)
            type = 1;
        else
            type = 2;

        if (type > 0){
            for (int i = 0; i < inventory[type].length; i++) {
                if (inventory[type][i] != null && Objects.equals(inventory[type][i].getId(), item.getId())) {
                    inventory[type][i].addStack(1);
                    return;
                }
            }
        }
        for (int i = 0; i < inventory[type].length; i++) {
            if (inventory[type][i] == null){
                inventory[type][i] = item;
                break;
            }
        }
    }

    public Vector<Item> getEquipmentItem() {
        return equipmentItem;
    }

    public Vector<Item> getMaterialItem() {
        return materialItem;
    }

    public Item[] getInventory(int tabIndex) {
        return inventory[tabIndex];
    }
}
