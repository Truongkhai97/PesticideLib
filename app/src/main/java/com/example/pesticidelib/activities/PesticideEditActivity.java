package com.example.pesticidelib.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pesticidelib.R;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;

public class PesticideEditActivity extends AppCompatActivity {
    private Pesticide pesticide;
    private ActionBar actionBar;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesticide_edit);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Sửa thông tin thuốc");

        EditText edt_ten = findViewById(R.id.edt_ten);
        EditText edt_hoatchat = findViewById(R.id.edt_hoatchat);
        EditText edt_nhom = findViewById(R.id.edt_nhom);
        EditText edt_doituongphongtru = findViewById(R.id.edt_doituongphongtru);
        EditText edt_tochucdangky = findViewById(R.id.edt_tochucdangky);
        Button btn_luu = findViewById(R.id.btn_luu);

        mDBHelper = DatabaseHelper.getInstance();
        mDB = mDBHelper.getWritableDatabase();

        pesticide = (Pesticide) getIntent().getSerializableExtra("pesticide");
        edt_ten.setText(pesticide.getTen());
        edt_hoatchat.setText(pesticide.getHoatchat());
        edt_nhom.setText(pesticide.getNhom());
        edt_doituongphongtru.setText(pesticide.getDoituongphongtru());
        edt_tochucdangky.setText(pesticide.getTochucdangky());

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay du lieu
                pesticide.setTen(edt_ten.getText().toString());
                pesticide.setHoatchat(edt_hoatchat.getText().toString());
                pesticide.setNhom(edt_nhom.getText().toString());
                pesticide.setDoituongphongtru(edt_doituongphongtru.getText().toString());
                pesticide.setTochucdangky(edt_tochucdangky.getText().toString());

                //tien hanh update
                mDBHelper.updateItem(pesticide);

                //quay lai giao dien truoc
//                onBackPressed();
                Intent intent = new Intent(getApplicationContext(), PesticideInfoActivity.class);
                intent.putExtra("pesticide", pesticide);
//                intent.putExtra("tab", MainActivity.getCurrentFragment());
                startActivity(intent);
                finish();
                //thong bao da luu lai
                Toast.makeText(getApplicationContext(), "Đã lưu lại", Toast.LENGTH_LONG).show();
            }
        });
    }
}