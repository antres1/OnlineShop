package ante.resetar.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    Button b2;
    LoginFragment loginFragment = LoginFragment.newInstance(null, null);
    RegisterFragment registerFragment = RegisterFragment.newInstance(null, null);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        //b1.setVisibility(View.VISIBLE);
        //b2.setVisibility(View.VISIBLE);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, loginFragment)
                .addToBackStack(null)
                .commit();
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                break;
            case R.id.button2:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, registerFragment)
                .addToBackStack(null)
                .commit();
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    /* resen problem u onBackPressed()
    @Override
    protected void onResume() {
        super.onResume();
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
    }
    */

    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
    }
}