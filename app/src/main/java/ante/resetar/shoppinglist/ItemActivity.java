package ante.resetar.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    Button backButton;
    TextView categoryText;
    ListView list;
    String category;
    String username;

    ShoppingItemAdapter adapter;

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        categoryText = findViewById(R.id.categoryTextView);
        category = getIntent().getStringExtra("category");
        categoryText.setText(category);

        dbHelper = new OnlineShopDbHelper(this, DB_NAME, null, 1);

        username = getIntent().getStringExtra("username");
        list = findViewById(R.id.itemList);
        adapter = new ShoppingItemAdapter(this, username);
        list.setAdapter(adapter);

        loadItemsByCategory(category);
        /*
        switch(category){
            case "Snacks":
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.chipsy), "Chipsy", "185 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.brusketi), "Brusketi", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.bake_rolls), "Bake Rolls", "220 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.chilli_chips), "Chilly Chips", "200 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.doritos), "Doritos", "160 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.elephant), "Elephant", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.gricko), "Gricko", "70 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kokice), "Popcorn", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.nuts), "Nuts", "190 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.perece), "Pretzels", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.pringles), "Pringles", "360 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.smoki), "Smoki", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.stapici_kikiriki), "Peanut Sticks", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.tak), "Tak", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.tortilla), "Tortilla", "200 RSD"));
                break;
            case "Drinks":
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.booster), "Booster", "110 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.cockta), "Cockta", "160 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.fanta_shokata), "Fanta Shokata", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.guarana), "Guarana", "60 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.joker), "Joker", "60 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kisela), "Sparkling water", "60 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kola), "Coca-Cola", "140 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.pepsi_max), "Pepsi Max", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.red_bull), "Red Bull", "190 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.schweppes), "Schweppes", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.sprite), "Sprite", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.ultra), "Ultra", "80 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.vitaminska_voda), "Vitamin Water", "110 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.voda), "Water", "50 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.prime), "Prime", "500 RSD"));
                break;
            case "Fruit":
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.ananas), "Pineapple", "200 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.avokado), "Avocado", "260 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.banane), "Banana", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.belo_grozdje),  "White grape", "200 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.grejp), "Grapefruit", "160 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.greni), "Granny Smith apple", "80 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.jabuka), "Apple", "90 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kivi), "Kiwi", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.limun), "Lemon", "100 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.lubenica), "Watermelon", "40 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.mandarine), "Mandarin", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.mango), "Mango", "270 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.nar), "Pomengranate", "220 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.narandza), "Orange", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.nektarina), "Nectarine", "160 RSD"));
                break;
            case "Vegetables":
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.beli_luk), "Garlic", "160 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.crni_luk), "Onion", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.celer), "Celery", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.cvekla), "Beet", "200 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.djumbir), "Ginger", "190 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.karfiol), "Cauliflower", "220 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.krastavac), "Cucumber", "140 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.krompir), "Potato", "130 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kupus), "Cabbage", "130 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.ljuta_paprika), "Hot pepper", "190 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.paprika), "Pepper", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.paradajz), "Tomato", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.pasulj), "Beans", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.sampinjoni), "Mushrooms", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.sargarepa), "Carrot", "150 RSD"));
                break;
            case "Sweets":
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.bananica), "Bananica", "30 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.bounty), "Bounty", "70 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.bueno), "Bueno", "110 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.galeb), "Galeb", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kidy), "Kidy", "60 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kinder), "Kinder", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kitkat), "KitKat", "70 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.kokos_kasato), "Kokos kasato", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.milka), "Milka", "200 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.milkinis), "Milkinis", "230 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.najlepse_zelje), "Najlepse zelje", "180 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.reeses), "Reeses", "150 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.snickers), "Snickers", "120 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.toblerone), "Toblerone", "250 RSD"));
                adapter.addShoppingItem(new ShoppingItem
                        (getDrawable(R.drawable.twix), "Twix", "100 RSD"));
                break;
        }
        */
    }

    private void loadItemsByCategory(String ctg) {
        ShoppingItem[] items = dbHelper.getItemsByCategory(this, ctg);
        if (items != null) {
            for (ShoppingItem item : items) {
                adapter.addShoppingItem(item);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backButton:
//                Intent intent = new Intent(this, HomeActivity.class);
//                intent.putExtra("return_to_fragment", getString(R.string.MenuFragmentTag));
//                startActivity(intent);
                onBackPressed();
                break;
        }
    }
}