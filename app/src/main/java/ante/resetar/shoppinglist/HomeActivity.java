package ante.resetar.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, MenuFragment.MenuButtonChangeListener {

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    TextView usernameTextView;
    TextView welcomeTextView;
    Button buttonHome;
    Button buttonMenu;
    Button buttonAccount;
    Button buttonBag;
    AccountFragment accountFragment = AccountFragment.newInstance(null, null);
    MenuFragment menuFragment = MenuFragment.newInstance(null, null);

    //Button addCategoryButton;
    Button addItemButton;
    EditText itemName;
    EditText itemPrice;
    EditText itemCategory;

    private String username;

    private void displayUsernameAndWelcome() {
        usernameTextView.setVisibility(View.VISIBLE);
        welcomeTextView.setVisibility(View.VISIBLE);
        // Set the username text
        usernameTextView.setText(username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usernameTextView = findViewById(R.id.usernameTextView);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String usr = getIntent().getStringExtra("username");
            if (usr != null) {
                username = usr;
                displayUsernameAndWelcome();
            }
        }

        buttonHome = findViewById(R.id.homeButton);
        buttonMenu = findViewById(R.id.menuButton);
        buttonAccount = findViewById(R.id.accountButton);
        buttonBag = findViewById(R.id.bagButton);
        // ukloni kasnije ovu liniju ispod
        buttonBag.setEnabled(false);

        itemName = findViewById(R.id.itemNameEditText);
        itemPrice = findViewById(R.id.itemPriceEditText);
        itemCategory = findViewById(R.id.itemCategoryEditText);
        addItemButton = findViewById(R.id.addItemButton);
        dbHelper = new OnlineShopDbHelper(this, DB_NAME, null, 1);
        if(dbHelper.isAdmin(username)){
            addItemButton.setVisibility(View.VISIBLE);
            itemName.setVisibility(View.VISIBLE);
            itemPrice.setVisibility(View.VISIBLE);
            itemCategory.setVisibility(View.VISIBLE);
        }
        else{
            addItemButton.setVisibility(View.INVISIBLE);
            itemName.setVisibility(View.INVISIBLE);
            itemPrice.setVisibility(View.INVISIBLE);
            itemCategory.setVisibility(View.INVISIBLE);
        }

        buttonHome.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);
        buttonAccount.setOnClickListener(this);
        buttonBag.setOnClickListener(this);

        addItemButton.setOnClickListener(this);

//        if (getIntent() != null && getIntent().getExtras() != null) {
//            String fragmentTag = getIntent().getStringExtra("return_to_fragment");
//            if (fragmentTag != null) {
//                if (fragmentTag.equals(getString(R.string.MenuFragmentTag))) {
//                    usernameTextView.setVisibility(View.INVISIBLE);
//                    welcomeTextView.setVisibility(View.INVISIBLE);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.homeFrame, menuFragment)
//                            .commit();
//                }
//            }
//        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.homeButton:
                usernameTextView.setVisibility(View.VISIBLE);
                welcomeTextView.setVisibility(View.VISIBLE);
                if(dbHelper.isAdmin(username)){
                    addItemButton.setVisibility(View.VISIBLE);
                    itemName.setVisibility(View.VISIBLE);
                    itemPrice.setVisibility(View.VISIBLE);
                    itemCategory.setVisibility(View.VISIBLE);
                }
                getSupportFragmentManager().popBackStack(getString(R.string.HomeActivityTag), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.menuButton:
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);
                addItemButton.setVisibility(View.INVISIBLE);
                itemName.setVisibility(View.INVISIBLE);
                itemPrice.setVisibility(View.INVISIBLE);
                itemCategory.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrame, menuFragment)
                        .addToBackStack(getString(R.string.HomeActivityTag))
                        .commit();
                break;
            case R.id.accountButton:
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);
                addItemButton.setVisibility(View.INVISIBLE);
                itemName.setVisibility(View.INVISIBLE);
                itemPrice.setVisibility(View.INVISIBLE);
                itemCategory.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrame, accountFragment)
                        .addToBackStack(getString(R.string.HomeActivityTag))
                        .commit();
                break;
            case R.id.bagButton:
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);
                addItemButton.setVisibility(View.INVISIBLE);
                itemName.setVisibility(View.INVISIBLE);
                itemPrice.setVisibility(View.INVISIBLE);
                itemCategory.setVisibility(View.INVISIBLE);
                break;
            case R.id.addItemButton:
                String itmName = itemName.getText().toString().trim();
                String itmPrice = itemPrice.getText().toString().trim();
                String itmCategory = itemCategory.getText().toString().trim();
                if(!itmName.isEmpty() && !itmPrice.isEmpty() && !itmCategory.isEmpty()){
                    itmPrice += " RSD";
                    dbHelper.insertItem(new ShoppingItem(getDrawable(R.drawable.bananica), itmName, itmPrice, itmCategory));
                }
                itemName.setText("");
                itemPrice.setText("");
                itemCategory.setText("");

                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        //super.onBackPressed();
        getSupportFragmentManager().popBackStack(getString(R.string.HomeActivityTag), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        usernameTextView.setVisibility(View.VISIBLE);
        welcomeTextView.setVisibility(View.VISIBLE);
        if(dbHelper.isAdmin(username)){
            addItemButton.setVisibility(View.VISIBLE);
            itemName.setVisibility(View.VISIBLE);
            itemPrice.setVisibility(View.VISIBLE);
            itemCategory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMenuButtonColorChange(int color) {
        buttonMenu.setBackgroundColor(color);
    }
}