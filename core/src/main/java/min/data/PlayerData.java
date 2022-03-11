package min.data;

import min.manager.ItemManager;

public class PlayerData {
    public PlayerData(){
        items = new PlayerItems();
        status = new Status();
        generateFakeData();
    }
    private PlayerItems items;
    private Status status;

    public void generateFakeData(){
        items.add(ItemManager.generateItemFromId("000_1"));
        items.add(ItemManager.generateItemFromId("000_1"));
        items.add(ItemManager.generateItemFromId("000_1"));
        items.add(ItemManager.generateItemFromId("201_0"));
        items.add(ItemManager.generateItemFromId("202_1"));
        items.add(ItemManager.generateItemFromId("204_2"));
        items.add(ItemManager.generateItemFromId("301_0"));
    }

    public PlayerItems getItems(){
        return items;
    }

    public Status getStatus() {
        return status;
    }
}
