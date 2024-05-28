package ante.resetar.shoppinglist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingItemAdapter extends BaseAdapter {
    private Context myContext;
    private ArrayList<ShoppingItem> itemsList;
    private String username;

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    public ShoppingItemAdapter(Context myContext, String username) {
        this.myContext = myContext;
        itemsList = new ArrayList<ShoppingItem>();
        dbHelper = new OnlineShopDbHelper(myContext, DB_NAME, null, 1);
        this.username = username;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        Object rv = null;
        try {
            rv = itemsList.get(i);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addShoppingItem(ShoppingItem item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }
    public void removeShoppingItem(ShoppingItem item) {
        itemsList.remove(item);
        notifyDataSetChanged();
    }

    public void clearList(){
        itemsList.clear();
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
        Button addButton;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shopping_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)
                    view.findViewById(R.id.textViewName);
            viewHolder.price = (TextView)
                    view.findViewById(R.id.textViewPrice);
            viewHolder.image = (ImageView)
                    view.findViewById(R.id.imageView1);
            viewHolder.addButton = (Button)
                    view.findViewById(R.id.itemButton);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        /* Get data Object on position from list/database */
        viewHolder.name.setText(itemsList.get(i).getName());
        final String oldPrice = itemsList.get(i).getPrice();
        String newPrice = oldPrice;
        if(((ItemActivity)myContext).getIntent().getExtras().getBoolean("isSale", false)){
            String priceString = newPrice.substring(0, newPrice.length() - 4);
            float price = Float.parseFloat(priceString.trim());
            price = price * 0.8f;
            newPrice = String.valueOf((int)price + " RSD");
            viewHolder.price.setTextColor(((ItemActivity)myContext).getResources().getColor(R.color.red));
        }
        final String finalPrice = newPrice;
        viewHolder.price.setText(finalPrice);
        viewHolder.image.setImageDrawable(itemsList.get(i).getImage());
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemsList.get(i).getName();
                itemsList.get(i).setPrice(finalPrice);
                dbHelper.insertItemToPurchaseHistory(itemsList.get(i), username);
                itemsList.get(i).setPrice(oldPrice);
                Toast.makeText(myContext, "Predmet " + itemName + " dodat u korpu.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
