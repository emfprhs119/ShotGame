package min.data;

public class CraftReceipt {
    Item item;
    Item[] materials;
    public CraftReceipt(Item item, Item[] materials) {
        this.item = item;
        this.materials = materials;
    }

    public Item getItem() {
        return item;
    }

    public Item[] getMaterials() {
        return materials;
    }
}
