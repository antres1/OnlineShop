package ante.resetar.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    Bundle bundle;

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        usernameEditText = v.findViewById(R.id.usernameEditText);
        emailEditText = v.findViewById(R.id.emailEditText);
        passwordEditText = v.findViewById(R.id.passwordEditText);
        registerButton = v.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        dbHelper = new OnlineShopDbHelper(getContext(), DB_NAME, null, 1);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                String username = usernameEditText.getText().toString().trim();
                String mail = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                if(!username.isEmpty() && !mail.isEmpty() && !password.isEmpty()){
                    if(!dbHelper.userExists(username)){
                        dbHelper.insertUser(new User(username, mail, password));
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        bundle = new Bundle();
                        bundle.putString("username", username);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), "User already exists", Toast.LENGTH_LONG).show();
                    }

                }
                break;
            default:
                break;
        }
    }
}