package com.example.pesticidelib.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesticidelib.R;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;

public class PesticideInfoActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Menu menu;
    private Pesticide pesticide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesticide_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin thuốc");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDBHelper = DatabaseHelper.getInstance();
        mDb = mDBHelper.getWritableDatabase();

        pesticide = (Pesticide) getIntent().getSerializableExtra("pesticide");
//        Log.d("logd", getIntent().getExtras().getString("tab"));

        TextView tv_ten = findViewById(R.id.tv_ten);
        tv_ten.setText(pesticide.getTen());

        TextView tv_nhom = findViewById(R.id.tv_nhom);
        tv_nhom.setText(pesticide.getNhom());

        TextView tv_hoatchat = findViewById(R.id.tv_hoatchat);
        tv_hoatchat.setText(pesticide.getHoatchat());

        TextView tv_tochucdangky = findViewById(R.id.tv_tochucdangky);
        tv_tochucdangky.setText(pesticide.getTochucdangky());

        TextView tv_doituongphongtru = findViewById(R.id.tv_doituongphongtru);
        tv_doituongphongtru.setText(pesticide.getDoituongphongtru());
    }

    //    @SuppressLint("RestrictedApi")
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                finish();
//                Intent intent=new Intent(PesticideInfoActivity.this, MainActivity.class);
//                startActivity(new Intent(PesticideInfoActivity.this,MainActivity.class));
//                Log.d("logd", "onOptionsItemSelected: back button worked");
                return true;
            case R.id.add_icon:

//                mDBHelper = DatabaseHelper.getInstance();
//                mDb = mDBHelper.getWritableDatabase();

                if (pesticide.getIsSaved() == 0) {
                    mDBHelper.setIsSaved(pesticide.getId(), 1);
                    menu.getItem(0).setIcon(R.drawable.remove_from_fav);
                    pesticide.setIsSaved(1);
                    Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show();
//                    Log.d("logd", "added to fav");
                } else {
                    mDBHelper.setIsSaved(pesticide.getId(), 0);
                    menu.getItem(0).setIcon(R.drawable.add_to_fav);
                    pesticide.setIsSaved(0);
//                    Log.d("logd", "removed from fav");
                    Toast.makeText(this, "Đã hủy lưu", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_icon:

                Toast.makeText(this, "Đã xóa", Toast.LENGTH_LONG).show();
                //tien hanh xoa
                mDBHelper.deleteItem(pesticide);
                onBackPressed();
                //onBackPressed+Toast

                break;
            case R.id.edit_icon:

//                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                //intent toi activity moi co giao dien sua thong tin
                Intent intent=new Intent(this,PesticideEditActivity.class);
                intent.putExtra("pesticide",pesticide);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (MainActivity.getCurrentFragment() == 3) {
            super.onBackPressed();
//            finishAffinity();
        } else {
            finishAffinity();
            Intent intent = new Intent(PesticideInfoActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
//        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pesticide_infor_menu, menu);
        this.menu = menu;
        if (pesticide.getIsSaved() == 0) {
            menu.getItem(0).setIcon(R.drawable.add_to_fav);
        } else menu.getItem(0).setIcon(R.drawable.remove_from_fav);
        return true;
    }
}