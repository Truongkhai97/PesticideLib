package com.example.pesticidelib.activities;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pesticidelib.R;
import com.example.pesticidelib.adapters.RecyclerViewDataAdapter;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;

import java.io.IOException;
import java.util.List;

public class SearchingActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private RecyclerView rv_searched_items;
    private List<Pesticide> pesticideList;
    private ImageButton imbSearch;
    private EditText editText;
    private LinearLayoutManager linearLayoutManager;
    private final String TAG = "logd";
    private String text;
    private int choice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


        ActionBar actionbar = getSupportActionBar();
//        actionbar.setTitle("Tìm kiếm");
        choice=getIntent().getIntExtra("choice", 1);
        switch (choice) {
            case 2:
                actionbar.setTitle("Tìm kiếm theo hoạt chất");
                break;
            case 3:
                actionbar.setTitle("Tìm kiếm theo nhóm");
                break;
            case 4:
                actionbar.setTitle("Tìm kiếm theo đối tượng phòng trừ");
                break;
            case 5:
                actionbar.setTitle("Tìm kiếm theo nhà sản xuất");
                break;
            default:
                actionbar.setTitle("Tìm kiếm theo tên thuốc");
                break;
        }
        actionbar.setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.edt_search);
        imbSearch = findViewById(R.id.imb_search);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
//        Log.d(TAG, "Choice: "+choice);

        // Check if no view has focus:
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }

//        editText.onke
        Log.d(TAG, "onCreate: ");
        imbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: ");
                text = String.valueOf(editText.getText()).trim().replaceAll("\\s+"," ");
//                Log.d(TAG, "text: " + text);
                // Check if no view has focus:
//                View view = this.getCurrentFocus();
//                if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                pesticideList = mDBHelper.getSearchedItems(text, choice);
                Toast.makeText(getBaseContext(),"Tìm thấy "+pesticideList.size()+" kết quả",Toast.LENGTH_LONG).show();


                rv_searched_items = (RecyclerView) findViewById(R.id.rv_searched_items);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_searched_items.setLayoutManager(linearLayoutManager);
                rv_searched_items.setHasFixedSize(true);
                rv_searched_items.setAdapter(new RecyclerViewDataAdapter(getApplicationContext(), pesticideList));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
//                finish();
//                Intent intent=new Intent(PesticideInfoActivity.this, MainActivity.class);
//                startActivity(new Intent(PesticideInfoActivity.this,MainActivity.class));
//                Log.d("logd", "onOptionsItemSelected: back button worked");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //test lifecycle

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart: ");
        text = String.valueOf(editText.getText()).trim().replaceAll("\\s+"," ");
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        pesticideList = mDBHelper.getSearchedItems(text, choice);

        rv_searched_items = (RecyclerView) findViewById(R.id.rv_searched_items);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_searched_items.setLayoutManager(linearLayoutManager);
        rv_searched_items.setHasFixedSize(true);
        rv_searched_items.setAdapter(new RecyclerViewDataAdapter(getApplicationContext(), pesticideList));
        Log.d(TAG, "onStart: "+pesticideList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}