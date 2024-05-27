package ante.resetar.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MenuFragment extends Fragment {

    OnlineShopDbHelper dbHelper;
    private final String DB_NAME = "OnlineShop.db";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public interface MenuButtonChangeListener {
        void onMenuButtonColorChange(int color);
    }

    Button menuButton;
    private MenuButtonChangeListener callback;
    ListView list1;
    HTTPHelper httpHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (MenuButtonChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MenuButtonChangeListener");
        }
    }

    private void changeMenuButtonColor(int color) {
        if (callback != null) {
            callback.onMenuButtonColorChange(color);
        }
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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

    boolean isSale = false;
    IMyAidlInterface binderInterface = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        menuButton = view.findViewById(R.id.menuButton);
        list1 = view.findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        list1.setAdapter(adapter);
        dbHelper = new OnlineShopDbHelper(getContext(), DB_NAME, null, 1);
        httpHelper = new HTTPHelper();
        new Thread(new Runnable() {
            public void run() {
                try {
                    JSONArray items = httpHelper.getAllItems();
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                if(items != null){
                                    ArrayList<String> categories = new ArrayList<String>();
                                    for(int i = 0; i < items.length(); i++){
                                        try {
                                            JSONObject item = items.getJSONObject(i);
                                            String ctg = item.getString("category");
                                            if(!categories.contains(ctg))
                                            {
                                                categories.add(ctg);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    for(String category: categories) {
                                        adapter.add(category);
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        String[] categories = dbHelper.findCategories();
//        if (categories == null || categories.length == 0) {
//            insertItemsIntoDatabase();
//            categories = dbHelper.findCategories();
//        }
//        for (String category : categories) {
//            adapter.add(category);
//        }

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) adapterView.getAdapter();
                // Retrieve the string associated with the clicked item position
                String clickedString = arrayAdapter.getItem(i);
                //Log.d("Clicked Item", "Clicked string: " + clickedString);

                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("category", clickedString);
                intent.putExtra("username", getActivity().getIntent().getStringExtra("username"));
                intent.putExtra("isSale", isSale);
                getActivity().startActivity(intent);
            }
        });
        //Binding the service
        Intent intent = new Intent(getActivity(), SaleService.class);
        requireActivity().bindService(intent, (ServiceConnection) getActivity(), Context.BIND_AUTO_CREATE);
        binderInterface = ((HomeActivity)getActivity()).getBinder();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        isSale = binderInterface.getSale();
                        Thread.sleep(1000);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        return view;
    }

    private void insertItemsIntoDatabase() {
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.chipsy), "Chipsy", "185 RSD", "Snacks"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.brusketi), "Brusketi", "180 RSD", "Snacks"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.bake_rolls), "Bake Rolls", "220 RSD", "Snacks"));

        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.booster), "Booster", "110 RSD", "Drinks"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.cockta), "Cockta", "160 RSD", "Drinks"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.fanta_shokata), "Fanta Shokata", "180 RSD", "Drinks"));

        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.ananas), "Pineapple", "200 RSD", "Fruit"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.avokado), "Avocado", "260 RSD", "Fruit"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.banane), "Banana", "120 RSD", "Fruit"));

        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.beli_luk), "Garlic", "160 RSD", "Vegetables"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.crni_luk), "Onion", "180 RSD", "Vegetables"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.celer), "Celery", "180 RSD", "Vegetables"));

        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.bananica), "Bananica", "30 RSD", "Sweets"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.bounty), "Bounty", "70 RSD", "Sweets"));
        dbHelper.insertItem(new ShoppingItem(getActivity().getDrawable(R.drawable.bueno), "Bueno", "110 RSD", "Sweets"));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisible()){
            changeMenuButtonColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isVisible()){
            changeMenuButtonColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        changeMenuButtonColor(ContextCompat.getColor(requireContext(), R.color.red));
    }



}