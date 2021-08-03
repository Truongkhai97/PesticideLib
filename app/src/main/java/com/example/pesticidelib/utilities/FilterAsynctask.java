package com.example.pesticidelib.utilities;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.pesticidelib.adapters.RecyclerViewDataAdapter;
import com.example.pesticidelib.models.Pesticide;

import java.util.ArrayList;
import java.util.List;


public class FilterAsynctask extends AsyncTask<String, Void, List<Pesticide>> implements ConvertToEng{
    private List<Pesticide> pesticideList;
    private Integer choice;
    private RecyclerViewDataAdapter adapter;
    private final String TAG = "FilterAsynctask";

    public FilterAsynctask(List<Pesticide> pesticideList, Integer choice, RecyclerViewDataAdapter adapter) {
        this.pesticideList = pesticideList;
        this.choice = choice;
        this.adapter = adapter;
    }

    @Override
    protected List<Pesticide> doInBackground(String... strings) {
        List<Pesticide> temp = new ArrayList();
        String text = strings[0];
        String myText;
        for (Pesticide d : pesticideList) {
            if (isCancelled()) break;
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (choice == 2) {
                myText = d.getHoatchat();
            } else if (choice == 3) {
                myText = d.getNhom();
            } else if (choice == 4) {
                myText = d.getDoituongphongtru();
            } else if (choice == 5) {
                myText = d.getTochucdangky();
//                Log.d(TAG, "doInBackground: 5");
            } else {
                myText = d.getTen();
            }
            String engText = convertToEng(myText);
            if (text == "") temp = pesticideList;
            else if (myText.toLowerCase().contains(text) || myText.toUpperCase().contains(text) || engText.toUpperCase().contains(text) || engText.toLowerCase().contains(text)) {
                temp.add(d);
            }

        }
        return temp;
    }

    @Override
    protected void onPostExecute(List<Pesticide> temp) {
        adapter.updateList(temp);
        Log.d(TAG, "onPostExecute: done");
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
}
