package ante.resetar.shoppinglist;

import android.content.Context;
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

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    public ShoppingItemAdapter(Context myContext) {
        this.myContext = myContext;
        itemsList = new ArrayList<ShoppingItem>();
        dbHelper = new OnlineShopDbHelper(myContext, DB_NAME, null, 1);
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
        viewHolder.price.setText(itemsList.get(i).getPrice());
        viewHolder.image.setImageDrawable(itemsList.get(i).getImage());
        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemsList.get(i).getName();
                dbHelper.insertItemToPurchaseHistory(itemsList.get(i));
                Toast.makeText(myContext, "Predmet " + itemName + " dodat u korpu.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
