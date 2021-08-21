package com.example.pesticidelib.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pesticidelib.R;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.ConvertToEng;
import com.example.pesticidelib.utilities.DatabaseHelper;
import com.example.pesticidelib.utilities.HideVirtualKeyBoard;

public class PesticideEditActivity extends AppCompatActivity implements HideVirtualKeyBoard, ConvertToEng {
    private Pesticide oldPesticide = null, pesticide = null;
    private ActionBar actionBar;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;
    private String TAG = "PesticideEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_pesticide_edit);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Sửa thông tin thuốc");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        EditText edt_ten = findViewById(R.id.edt_ten);
        EditText edt_hoatchat = findViewById(R.id.edt_hoatchat);
        EditText edt_nhom = findViewById(R.id.edt_nhom);
        EditText edt_doituongphongtru = findViewById(R.id.edt_doituongphongtru);
        EditText edt_tochucdangky = findViewById(R.id.edt_tochucdangky);
        Button btn_luu = findViewById(R.id.btn_luu);

        LinearLayout linearLayout = findViewById(R.id.edit_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if no view has focus:
                View view = getCurrentFocus();
                if (view != null) {
                    hideKeyboard(v);
                }
            }
        });

        mDBHelper = DatabaseHelper.getInstance();
        mDB = mDBHelper.getWritableDatabase();

        oldPesticide = (Pesticide) getIntent().getSerializableExtra("pesticide");
        //2 bien cung tro den 1 dia chi object nen gan nhu la 1, dieu nay gay ra loi
//        pesticide = (Pesticide) getIntent().getSerializableExtra("pesticide");
        pesticide = new Pesticide(oldPesticide.getId(), oldPesticide.getTen(), oldPesticide.getTen_ascii(), oldPesticide.getHoatchat(), oldPesticide.getNhom(), oldPesticide.getDoituongphongtru(),oldPesticide.getDoituongphongtru_ascii(), oldPesticide.getTochucdangky(),oldPesticide.getTochucdangky_ascii(), oldPesticide.getIsSaved());

        edt_ten.setText(pesticide.getTen());

        edt_hoatchat.setText(pesticide.getHoatchat());

        edt_nhom.setText(pesticide.getNhom());

        edt_doituongphongtru.setText(pesticide.getDoituongphongtru());

        edt_tochucdangky.setText(pesticide.getTochucdangky());

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check null
                if (TextUtils.isEmpty(edt_ten.getText())) {
                    edt_ten.setError("Tên không được để trống");
                    edt_ten.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(edt_nhom.getText())) {
                    edt_nhom.setError("Nhóm không được để trống");
                    edt_nhom.requestFocus();
                    return;

                } else if (TextUtils.isEmpty(edt_doituongphongtru.getText())) {
                    edt_doituongphongtru.setError("Đối tượng phòng trừ không được để trống");
                    edt_doituongphongtru.requestFocus();
                    return;
                }

                //lay du lieu
                pesticide.setTen(edt_ten.getText().toString());
                pesticide.setTen_ascii(convertToEng(edt_ten.getText().toString()));
                pesticide.setHoatchat(edt_hoatchat.getText().toString());
                pesticide.setNhom(edt_nhom.getText().toString());
                pesticide.setDoituongphongtru(edt_doituongphongtru.getText().toString());
                pesticide.setDoituongphongtru_ascii(convertToEng(edt_doituongphongtru.getText().toString()));
                pesticide.setTochucdangky(edt_tochucdangky.getText().toString());
                pesticide.setTochucdangky_ascii(convertToEng(edt_tochucdangky.getText().toString()));

                //tien hanh update
                if (mDBHelper.updateItem(pesticide)) {

                    //quay lai giao dien truoc
//                onBackPressed();
                    Intent intent = new Intent(getApplicationContext(), PesticideInfoActivity.class);
                    intent.putExtra("pesticide", pesticide);
                    startActivity(intent);
                    finish();
                    //thong bao da luu lai
                    Toast.makeText(getApplicationContext(), "Đã lưu lại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thất bại, tên có thể đã tồn tại", Toast.LENGTH_LONG).show();
                    edt_ten.requestFocus();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), PesticideInfoActivity.class);
        intent.putExtra("pesticide", oldPesticide);
        startActivity(intent);
        finish();
    }

    @Override
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
//        Log.d(TAG, "hideKeyboard: called");
    }

    //ham ho tro chuyen doi sang khong dau
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
}