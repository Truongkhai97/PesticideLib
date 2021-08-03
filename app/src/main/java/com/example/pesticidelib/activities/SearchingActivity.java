package com.example.pesticidelib.activities;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.pesticidelib.utilities.ConvertToEng;
import com.example.pesticidelib.utilities.DatabaseHelper;
import com.example.pesticidelib.utilities.FilterAsynctask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchingActivity extends AppCompatActivity implements ConvertToEng {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private RecyclerView rv_searched_items;
    private List<Pesticide> pesticideList = null;
    private ImageButton imbSearch;
    private EditText editText;
    private LinearLayoutManager linearLayoutManager;
    private final String TAG = "SearchingActivity";
    private String text;
    private int choice;
    private RecyclerViewDataAdapter adapter;
    private FilterAsynctask filterAsynctask=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_searching);


        ActionBar actionbar = getSupportActionBar();
//        actionbar.setTitle("Tìm kiếm");
        choice = getIntent().getIntExtra("choice", 1);
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
        // Tham khao: https://stackoverflow.com/questions/40754174/android-implementing-search-filter-to-a-recyclerview
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: called!");
//                filter(editable.toString());
                if(filterAsynctask!=null) filterAsynctask.cancel(true);
                filterAsynctask=new FilterAsynctask(pesticideList,choice,adapter);
                filterAsynctask.execute(editable.toString());
                //can xu ly asynctask gan vi tri nay
            }
        });
        
        
        imbSearch = findViewById(R.id.imb_search);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        // Check if no view has focus:
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }


        imbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = String.valueOf(editText.getText()).trim().replaceAll("\\s+", " ");
//                Log.d(TAG, "text: " + text);
                // Check if no view has focus:
//                View view = this.getCurrentFocus();
//                if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                pesticideList = mDBHelper.getSearchedItems(text, choice);
                Toast.makeText(getBaseContext(), "Tìm thấy " + pesticideList.size() + " kết quả", Toast.LENGTH_LONG).show();

                rv_searched_items = (RecyclerView) findViewById(R.id.rv_searched_items);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_searched_items.setLayoutManager(linearLayoutManager);
                rv_searched_items.setHasFixedSize(true);

                rv_searched_items.setAdapter(new RecyclerViewDataAdapter(getApplicationContext(), pesticideList));
            }
        });
    }

    void filter (String text) {
        List<Pesticide> temp = new ArrayList();
        String myText;
        Log.d(TAG, choice + "choice");
        for (Pesticide d : pesticideList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (choice == 2) {
                myText = d.getHoatchat();
            } else if (choice == 3) {
                myText = d.getNhom();
            } else if (choice == 4) {
                myText = d.getDoituongphongtru();
            } else {
                myText = d.getTen();
            }
            String engText = convertToEng(myText);
            if (text == "") temp = pesticideList;
            else if (myText.toLowerCase().contains(text) || myText.toUpperCase().contains(text) || engText.toUpperCase().contains(text) || engText.toLowerCase().contains(text)) {
                temp.add(d);
            }

        }
        //update recyclerview
        adapter.updateList(temp);
    }

//    public String convertToEng(String str) {
//        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
//        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
//        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
//        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
//        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
//        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
//        str = str.replaceAll("đ", "d");
//
//        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
//        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
//        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
//        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
//        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
//        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
//        str = str.replaceAll("Đ", "D");
//        return str;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
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
        text = String.valueOf(editText.getText()).trim().replaceAll("\\s+", " ");

        pesticideList = mDBHelper.getSearchedItems(text, choice);
        adapter = new RecyclerViewDataAdapter(getApplicationContext(), pesticideList);
        rv_searched_items = (RecyclerView) findViewById(R.id.rv_searched_items);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_searched_items.setLayoutManager(linearLayoutManager);
        rv_searched_items.setHasFixedSize(true);

        rv_searched_items.setAdapter(adapter);
        Log.d(TAG, "onStart: " + pesticideList.size());
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