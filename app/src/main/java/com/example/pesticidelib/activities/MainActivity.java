package com.example.pesticidelib.activities;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pesticidelib.R;
import com.example.pesticidelib.fragments.AboutFragment;
import com.example.pesticidelib.fragments.AllItemsFragment;
import com.example.pesticidelib.fragments.SavedItemsFragment;
import com.example.pesticidelib.fragments.SearchFragment;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private List<Pesticide> pesticideList;
    public static int currentFragment = 1;
    private BottomNavigationView bottomNavigationView;
    private String TAG = "logd";
    boolean isDoubleBackPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        actionBar.setTitle("Tất cả thuốc");

        bottomNavigationView = findViewById(R.id.navigation);
//        if(getIntent()!=null) currentFragment=1;
//        else{
//            currentFragment=getIntent().getExtras().getInt("tab");
//        }
        switch (currentFragment) {
//            case 1:
//                loadFragment(new AllItemsFragment());
//                bottomNavigationView.setSelectedItemId(R.id.navigation_all_items);
//                break;
            case 2:
//                loadFragment(new SavedItemsFragment());
//                bottomNavigationView.setSelectedItemId(R.id.navigation_saved_items);
                findViewById(R.id.navigation_saved_items).performClick();
                Log.d(TAG, "case 2: ");
                break;
            case 3:
//                loadFragment(new SearchFragment());
//                bottomNavigationView.setSelectedItemId(R.id.navigation_search);
                findViewById(R.id.navigation_search).performClick();
                Log.d(TAG, "case 3: ");
                break;
            case 4:
//                loadFragment(new AboutFragment());
//                bottomNavigationView.setSelectedItemId(R.id.navigation_about);
                findViewById(R.id.navigation_about).performClick();
                Log.d(TAG, "case 4: ");
                break;
            default:
//                loadFragment(new AllItemsFragment());
//                bottomNavigationView.setSelectedItemId(R.id.navigation_all_items);
                Log.d(TAG, "case 1: ");
                findViewById(R.id.navigation_all_items).performClick();
                break;
        }

//        Log.d(TAG, "main-current frm: " + currentFragment);

    }

    //su kien khi item duoc click
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_all_items:
                    actionBar.setTitle("Tất cả thuốc");
                    fragment = new AllItemsFragment();
                    loadFragment(fragment);
                    currentFragment = 1;
                    return true;
                case R.id.navigation_saved_items:
                    actionBar.setTitle("Đã lưu");
                    fragment = new SavedItemsFragment();
                    loadFragment(fragment);
                    currentFragment = 2;
                    return true;
                case R.id.navigation_search:
                    actionBar.setTitle("Tìm kiếm");
                    loadFragment(new SearchFragment());
                    currentFragment = 3;
                    return true;
                case R.id.navigation_about:
                    actionBar.setTitle("Thông tin ứng dụng");
                    loadFragment(new AboutFragment());
                    currentFragment = 4;
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (isDoubleBackPressed) {
            finish();
            return;
        }

        this.isDoubleBackPressed = true;
        if (getCurrentFragment() != 1) {
//            toolbar.setTitle("Tất cả thuốc");
//            Fragment fragment = new AllItemsFragment();
//            loadFragment(fragment);
            findViewById(R.id.navigation_all_items).performClick();
            currentFragment = 1;
        }
        Toast.makeText(this, "Nhấn 2 lần để thoát", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                isDoubleBackPressed = false;
            }
        }, 2000);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_items_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayList<CharSequence> arrayList = new ArrayList<>();
        arrayList.add("Tất cả");
        arrayList.add("Thuốc trừ sâu");
        arrayList.add("Thuốc trừ bệnh");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sort_option, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.setBackgroundColor(254);
        return true;
    }

    public static int getCurrentFragment() {
        return currentFragment;
    }

}