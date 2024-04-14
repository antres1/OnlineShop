package ante.resetar.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button passwordButton;
    Button endSessionButton;
    TextView usernameTextView;
    TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        passwordButton = findViewById(R.id.passwordActivityButton);
        endSessionButton = findViewById(R.id.endSessionButton);
        usernameTextView = findViewById(R.id.usernameTextView2);
        emailTextView = findViewById(R.id.emailTextView);
        usernameTextView.setText(getIntent().getStringExtra("username"));
        emailTextView.setText(getIntent().getStringExtra("email"));
        passwordButton.setOnClickListener(this);
        endSessionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.passwordActivityButton:
                Intent intent1 = new Intent(ProfileActivity.this, PasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.endSessionButton:
                //ne moze ovako, radi samo za fragmente getSupportFragmentManager().popBackStack();
//                Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
//                startActivity(intent2);
                finishAffinity();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}