package ante.resetar.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, MenuFragment.MenuButtonChangeListener {

    TextView usernameTextView;
    TextView welcomeTextView;
    Button buttonHome;
    Button buttonMenu;
    Button buttonAccount;
    Button buttonBag;
    AccountFragment accountFragment = AccountFragment.newInstance(null, null);
    MenuFragment menuFragment = MenuFragment.newInstance(null, null);

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

        buttonHome.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);
        buttonAccount.setOnClickListener(this);
        buttonBag.setOnClickListener(this);

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
                getSupportFragmentManager().popBackStack(getString(R.string.HomeActivityTag), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.menuButton:
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrame, menuFragment)
                        .addToBackStack(getString(R.string.HomeActivityTag))
                        .commit();
                break;
            case R.id.accountButton:
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.homeFrame, accountFragment)
                        .addToBackStack(getString(R.string.HomeActivityTag))
                        .commit();
                break;
            case R.id.bagButton:
                usernameTextView.setVisibility(View.INVISIBLE);
                welcomeTextView.setVisibility(View.INVISIBLE);

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
    }

    @Override
    public void onMenuButtonColorChange(int color) {
        buttonMenu.setBackgroundColor(color);
    }
}