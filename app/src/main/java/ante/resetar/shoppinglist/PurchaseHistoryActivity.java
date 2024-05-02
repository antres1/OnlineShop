package ante.resetar.shoppinglist;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PurchaseHistoryActivity extends AppCompatActivity {

    ListView list;
    TextView emptyTextView;
    PurchaseHistoryItemAdapter adapter;

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        list = findViewById(R.id.purchaseHistoryList);
        emptyTextView = findViewById(R.id.emptyListTextView);
        list.setEmptyView(emptyTextView);

        dbHelper = new OnlineShopDbHelper(this, DB_NAME, null, 1);

        adapter = new PurchaseHistoryItemAdapter(this);
        list.setAdapter(adapter);

        PurchaseHistoryItem[] purchaseHistoryItems = dbHelper.getAllPurchaseHistoryItems();
        adapter.clearList();
        if (purchaseHistoryItems != null && purchaseHistoryItems.length > 0) {
            for (PurchaseHistoryItem item : purchaseHistoryItems) {
                adapter.addPurchaseHistoryItem(item);
            }
        }
        /*
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "2000 RSD", "05.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("CANCELLED", "4000 RSD", "06.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("WAITING FOR DELIVERY", "3000 RSD", "07.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1000 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1001 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1002 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1003 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1004 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1005 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1006 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1007 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1008 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1009 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1010 RSD", "01.01.2024."));
        adapter.addPurchaseHistoryItem(new PurchaseHistoryItem("DELIVERED", "1011 RSD", "01.01.2024."));
         */
    }
}