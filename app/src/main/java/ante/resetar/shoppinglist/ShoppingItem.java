package ante.resetar.shoppinglist;

import android.graphics.drawable.Drawable;

public class ShoppingItem {
    private Drawable image;
    private String name;
    private String price;
    private String category;

    public ShoppingItem(Drawable image, String name, String price, String category) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
