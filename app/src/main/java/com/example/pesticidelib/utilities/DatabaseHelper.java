package com.example.pesticidelib.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pesticidelib.models.Pesticide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "ThuocBVTV.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private static Context mContext;
    private boolean mNeedUpdate = false;
    private static DatabaseHelper mInstance;

    public static synchronized DatabaseHelper getInstance() {

        if (mInstance == null) {
            mInstance = new DatabaseHelper(mContext);
        }
        return mInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
                Log.d("logd", "copyDataBase: success!");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //truong hop db nam trong res\raw
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    //CAC CAU LENH TRUY VAN DU LIEU
    public int count() {
        Integer count = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id from thuoc", null);

        //Đến dòng đầu của tập dữ liệu
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            count++;
//            cursor.moveToNext();
//        }
//
//        cursor.close();
//        return count;
        return cursor.getCount();
    }

    public List<Pesticide> getAllList() {
        Log.d("Logd", String.valueOf(checkDataBase()));
        List<Pesticide> pesticideList = new ArrayList<>();
        mDataBase = this.getReadableDatabase();
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM thuoc", null);

        //chuyen den dong dau cua du lieu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String hoatchat = cursor.getString(2);
            String nhom = cursor.getString(3);
            String doituongphongtru = cursor.getString(4);
            String tochucdangky = cursor.getString(5);
            int isSaved = cursor.getInt(6);

            pesticideList.add(new Pesticide(id, ten, hoatchat, nhom, doituongphongtru, tochucdangky, isSaved));
            cursor.moveToNext();
        }
        cursor.close();
        return pesticideList;
    }

    public List<Pesticide> getFavList() {
        List<Pesticide> pesticideList = new ArrayList<>();
        mDataBase = this.getReadableDatabase();
        Cursor cursor = mDataBase.rawQuery("SELECT * FROM thuoc where uathich=1", null);

        //chuyen den dong dau cua du lieu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String hoatchat = cursor.getString(2);
            String nhom = cursor.getString(3);
            String doituongphongtru = cursor.getString(4);
            String tochucdangky = cursor.getString(5);
            int isSaved = cursor.getInt(6);

            pesticideList.add(new Pesticide(id, ten, hoatchat, nhom, doituongphongtru, tochucdangky, isSaved));
            cursor.moveToNext();
        }
        cursor.close();
        return pesticideList;
    }

    public List<Pesticide> getSearchedItems(String keyword, int choice) {
        List<Pesticide> pesticideList = new ArrayList<>();
        mDataBase = this.getReadableDatabase();
        Cursor cursor;

        switch (choice) {
            case 2:
                cursor = mDataBase.rawQuery("SELECT * FROM thuoc where hoatchat like '%" + keyword + "%'", null);
                break;
            case 3:
                cursor = mDataBase.rawQuery("SELECT * FROM thuoc where nhom like '%" + keyword + "%'", null);
                break;
            case 4:
                cursor = mDataBase.rawQuery("SELECT * FROM thuoc where doituongphongtru like '%" + keyword + "%'", null);
                break;
            case 5:
                cursor = mDataBase.rawQuery("SELECT * FROM thuoc where tochucdangky like '%" + keyword + "%'", null);
                break;
            default:
                cursor = mDataBase.rawQuery("SELECT * FROM thuoc where ten like '%" + keyword + "%'", null);
                break;

        }

        //chuyen den dong dau cua du lieu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String hoatchat = cursor.getString(2);
            String nhom = cursor.getString(3);
            String doituongphongtru = cursor.getString(4);
            String tochucdangky = cursor.getString(5);
            int isSaved = cursor.getInt(6);

            pesticideList.add(new Pesticide(id, ten, hoatchat, nhom, doituongphongtru, tochucdangky, isSaved));
            cursor.moveToNext();
        }
        cursor.close();
        return pesticideList;
    }

    public void setIsSaved(int id, int val) {
        mDataBase = this.getReadableDatabase();
//        Cursor cursor = mDataBase.rawQuery("SELECT * FROM thuoc where uathich=1", null);

        mDataBase.execSQL("UPDATE thuoc SET uathich=" + val + " WHERE id=" + id);
//        cursor.close();
    }

    public void updateItem(Pesticide pesticide) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", pesticide.getId());
        contentValues.put("ten", pesticide.getTen());
        contentValues.put("hoatchat", pesticide.getHoatchat());
        contentValues.put("nhom", pesticide.getNhom());
        contentValues.put("doituongphongtru", pesticide.getDoituongphongtru());
        contentValues.put("tochucdangky", pesticide.getTochucdangky());
        String whereClause = "id=?";
        String whereArgs[] = {pesticide.getId().toString()};
        db.update("thuoc", contentValues, whereClause, whereArgs);
    }

    public void deleteItem(Pesticide pesticide) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {pesticide.getId().toString()};
        db.delete("thuoc", whereClause, whereArgs);
    }

    public String getAllTableNames() {
        Log.d("Logd", String.valueOf(checkDataBase()));
        String tbname = null;
        mDataBase = this.getReadableDatabase();
        Cursor cursor = mDataBase.rawQuery("SELECT \n" +
                "    name\n" +
                "FROM \n" +
                "    sqlite_master \n" +
                "WHERE \n" +
                "    type ='table' AND \n" +
                "    name NOT LIKE 'sqlite_%';", null);

        //chuyen den dong dau cua du lieu
        Log.d("Logd", "getAllTables: " + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tbname = cursor.getString(0);

            cursor.moveToNext();
        }
        cursor.close();
        return tbname;
    }

    //tim kiem tren nhieu cot
    //SELECT *
    //FROM thuoc
    //WHERE ten LIKE '%việt%' OR
    //      hoatchat LIKE '%việt%' OR
    //      nhom LIKE '%việt%' OR
    //      doituongphongtru LIKE '%việt%' OR
    //      tochucdangky LIKE '%việt%' OR
    //      uathich LIKE '%việt%'
}

