package bootcamp.fufnProject.dto;

import java.util.ArrayList;

public class DBManager {

    private static ArrayList<Items> items = new ArrayList<>();
    private static Long id = 5L;

    static {

        items.add(new Items(1L, "Iphone 12 Pro", "128GB 6GB RAM", 20, 450000));
        items.add(new Items(2L, "Iphone 12 Pro", "256GB 6GB RAM", 10, 600000));
        items.add(new Items(3L, "XIAOMI REDMI NOTE", "128 GB 6GB RAM", 20, 120000));
        items.add(new Items(4L, "Iphone 11", "128GB 6GB RAM", 50, 300000));

    }

    public static void addItem(Items item){
        item.setId(id);
        id++;
        items.add(item);
    }

    public static ArrayList<Items> getItems(){
        return items;
    }


}
