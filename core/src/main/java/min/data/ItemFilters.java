package min.data;

import java.util.function.Predicate;

public class ItemFilters {
    private static class LazyHolder {
        private static final ItemFilters itemFilters = new ItemFilters();
    }
    public static ItemFilters getInstance(){
        return ItemFilters.LazyHolder.itemFilters;
    }

    Predicate<Item> kindFilter;
    String itemKind;
    public ItemFilters(){
        //kindFilter = item -> item.kind.equals(itemKind);
        /*
        kindFilter = new Predicate<Item>() {
            @Override
            public boolean test(Item item) {
                return item.kind.equals(itemKind);
            }
        };

         */
    }
}
