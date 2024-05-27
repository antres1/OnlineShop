package ante.resetar.shoppinglist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, MenuFragment.MenuButtonChangeListener, ServiceConnection {

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    HTTPHelper httpHelper;

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
    EditText imageName;

    Button addCategoryButton;
    EditText addCategoryEditText;

    private String username;

    private IMyAidlInterface binderInterface = null;

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

        httpHelper = new HTTPHelper();

        buttonHome = findViewById(R.id.homeButton);
        buttonMenu = findViewById(R.id.menuButton);
        buttonAccount = findViewById(R.id.accountButton);
        buttonBag = findViewById(R.id.bagButton);
        // ukloni kasnije ovu liniju ispod
        buttonBag.setEnabled(false);

        itemName = findViewById(R.id.itemNameEditText);
        itemPrice = findViewById(R.id.itemPriceEditText);
        itemCategory = findViewById(R.id.itemCategoryEditText);
        imageName = findViewById(R.id.imageNameEditText);
        addItemButton = findViewById(R.id.addItemButton);

        addCategoryEditText = findViewById(R.id.addCategoryEditText);
        addCategoryButton = findViewById(R.id.addCategoryButton);

        dbHelper = new OnlineShopDbHelper(this, DB_NAME, null, 1);
        if(dbHelper.isAdmin(username)){
            addItemButton.setVisibility(View.VISIBLE);
            itemName.setVisibility(View.VISIBLE);
            itemPrice.setVisibility(View.VISIBLE);
            itemCategory.setVisibility(View.VISIBLE);
            imageName.setVisibility(View.VISIBLE);
            addCategoryEditText.setVisibility(View.VISIBLE);
            addCategoryButton.setVisibility(View.VISIBLE);
        }
        else{
            addItemButton.setVisibility(View.INVISIBLE);
            itemName.setVisibility(View.INVISIBLE);
            itemPrice.setVisibility(View.INVISIBLE);
            itemCategory.setVisibility(View.INVISIBLE);
            imageName.setVisibility(View.INVISIBLE);
            addCategoryEditText.setVisibility(View.INVISIBLE);
            addCategoryButton.setVisibility(View.INVISIBLE);
        }

        buttonHome.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);
        buttonAccount.setOnClickListener(this);
        buttonBag.setOnClickListener(this);

        addItemButton.setOnClickListener(this);
        addCategoryButton.setOnClickListener(this);

        Intent intent = new Intent(HomeActivity.this, SaleService.class);
        bindService(intent, HomeActivity.this, Context.BIND_AUTO_CREATE);
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
                    imageName.setVisibility(View.VISIBLE);
                    addCategoryEditText.setVisibility(View.VISIBLE);
                    addCategoryButton.setVisibility(View.VISIBLE);
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
                imageName.setVisibility(View.INVISIBLE);
                addCategoryEditText.setVisibility(View.INVISIBLE);
                addCategoryButton.setVisibility(View.INVISIBLE);
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
                imageName.setVisibility(View.INVISIBLE);
                addCategoryEditText.setVisibility(View.INVISIBLE);
                addCategoryButton.setVisibility(View.INVISIBLE);
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
                imageName.setVisibility(View.INVISIBLE);
                addCategoryEditText.setVisibility(View.INVISIBLE);
                addCategoryButton.setVisibility(View.INVISIBLE);
                break;
            case R.id.addItemButton:
                String itmName = itemName.getText().toString().trim();
                String itmPrice = itemPrice.getText().toString().trim() + " RSD";
                String itmCategory = itemCategory.getText().toString().trim();
                String itmImageName = imageName.getText().toString().trim();
                if(!itmName.isEmpty() && !itmPrice.isEmpty() && !itmCategory.isEmpty() && !itmImageName.isEmpty()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                if(!httpHelper.addItemToCategory(itmName, itmPrice, itmCategory, itmImageName))
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(HomeActivity.this, "Couldn't add item.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Thread.currentThread().stop();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Drawable drawable = getDrawableFromName(this, itmImageName);
                    if(drawable != null) dbHelper.insertItem(new ShoppingItem(drawable, itmName, itmPrice, itmCategory));
                }
                itemName.setText("");
                itemPrice.setText("");
                itemCategory.setText("");
                imageName.setText("");

                break;
            case R.id.addCategoryButton:
                String category = addCategoryEditText.getText().toString().trim();
                if(!category.isEmpty()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                if(!httpHelper.addCategory(category))
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(HomeActivity.this, "Couldn't add category.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Thread.currentThread().stop();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    addCategoryEditText.setText("");
                }
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
            imageName.setVisibility(View.VISIBLE);
            addCategoryEditText.setVisibility(View.VISIBLE);
            addCategoryButton.setVisibility(View.VISIBLE);
        }
    }

    public Drawable getDrawableFromName(Context context, String imageName) {
        // Get the resource ID of the drawable
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        // If resource ID is valid, return the drawable
        if (resourceId != 0) {
            return context.getResources().getDrawable(resourceId, null);
        } else {
            // Return null if resource ID is not found
            return null;
        }
    }


    @Override
    public void onMenuButtonColorChange(int color) {
        buttonMenu.setBackgroundColor(color);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("ServiceTAG", "onServiceConnected");
        binderInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        try {
            binderInterface.setUsername(getIntent().getStringExtra("username"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            if(binderInterface.getSale()){
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);
                addItemButton.setVisibility(View.INVISIBLE);
                itemName.setVisibility(View.INVISIBLE);
                itemPrice.setVisibility(View.INVISIBLE);
                itemCategory.setVisibility(View.INVISIBLE);
                imageName.setVisibility(View.INVISIBLE);
                addCategoryEditText.setVisibility(View.INVISIBLE);
                addCategoryButton.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrame, menuFragment)
                        .addToBackStack(getString(R.string.HomeActivityTag))
                        .commit();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        binderInterface = null;
    }

    @Override
    protected void onDestroy() {
        unbindService(HomeActivity.this);
        super.onDestroy();
    }

    public IMyAidlInterface getBinder() {
        return binderInterface;
    }
}