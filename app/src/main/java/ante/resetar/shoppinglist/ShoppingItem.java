package ante.resetar.shoppinglist;

import android.graphics.drawable.Drawable;

public class ShoppingItem {
    private Drawable image;
    private String name;
    private String price;

    public ShoppingItem(Drawable image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
