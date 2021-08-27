package com.example.pesticidelib.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pesticidelib.R;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;
import com.example.pesticidelib.utilities.HideVirtualKeyBoard;

public class PesticideInfoActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Menu menu;
    private Pesticide pesticide;
    private final String TAG = "PesticideInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesticide_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thông tin thuốc");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

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

                new AlertDialog.Builder(this)
                        .setTitle("Xóa thuốc")
                        .setMessage("Bạn có chắc chắn muốn xóa?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //OK
                                Toast.makeText(getApplicationContext(), "Đã xóa", Toast.LENGTH_LONG).show();
                                //tien hanh xoa
                                mDBHelper.deleteItem(pesticide);
                                //onBackPressed+Toast
                                onBackPressed();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.delete)
                        .show();

                break;
            case R.id.edit_icon:

                new AlertDialog.Builder(this)
                        .setTitle("Sửa thông tin")
                        .setMessage("Thông tin đưa vào có thể chưa được kiểm định, bạn có muốn tiến tiếp tục?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //OK
                                //intent toi activity moi co giao dien sua thong tin
                                Intent intent = new Intent(getApplicationContext(), PesticideEditActivity.class);
                                intent.putExtra("pesticide", pesticide);
                                startActivity(intent);
                                finish();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.edit)
                        .show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
//        finishAffinity();

//        if (MainActivity.getCurrentFragment() == 3) {
//            super.onBackPressed();
//        } else {
//            finishAffinity();
//            Intent intent = new Intent(PesticideInfoActivity.this, MainActivity.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pesticide_infor_menu, menu);
        this.menu = menu;
        if (pesticide.getIsSaved() == 0) {
            menu.getItem(0).setIcon(R.drawable.add_to_fav);
        } else menu.getItem(0).setIcon(R.drawable.remove_from_fav);

        menu.getItem(1).setVisible(false);
        return true;
    }

}