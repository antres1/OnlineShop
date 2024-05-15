package ante.resetar.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PasswordActivity extends AppCompatActivity {

    Button saveButton;
    EditText currPass;
    EditText newPass;

    HTTPHelper httpHelper;

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        dbHelper = new OnlineShopDbHelper(this, DB_NAME, null, 1);

        currPass = findViewById(R.id.currPassEditText);
        newPass = findViewById(R.id.newPassEditText);
        saveButton = findViewById(R.id.saveButton);

        httpHelper = new HTTPHelper();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getStringExtra("username");
                String currentPassword = currPass.getText().toString();
                String newPassword = newPass.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            JSONObject jsonObject = httpHelper.changePassword(username, currentPassword, newPassword);
                            if(jsonObject == null)
                            {
                                try {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(PasswordActivity.this, "Failed to change password", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Thread.currentThread().stop();
                            }
                            try {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(PasswordActivity.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dbHelper.updatePassword(username, newPassword);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();

//                if (dbHelper.correctPassword(username, currentPassword)) {
//                    if (dbHelper.updatePassword(username, newPassword)) {
//                        Toast.makeText(PasswordActivity.this, "Password updated successfully", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(PasswordActivity.this, "Failed to update password", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(PasswordActivity.this, "Incorrect current password", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}