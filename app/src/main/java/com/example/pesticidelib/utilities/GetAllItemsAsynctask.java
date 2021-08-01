package com.example.pesticidelib.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pesticidelib.adapters.RecyclerViewDataAdapter;
import com.example.pesticidelib.models.Pesticide;

import java.util.List;

public class GetAllItemsAsynctask extends AsyncTask<String, Void, List<Pesticide>> {
    private DatabaseHelper mDBHelper;
    private List<Pesticide> pesticideList;
    private RecyclerViewDataAdapter adapter;
    private RecyclerView recyclerView;

    public GetAllItemsAsynctask(DatabaseHelper mDBHelper, RecyclerViewDataAdapter adapter, RecyclerView recyclerView) {
        this.mDBHelper = mDBHelper;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    protected List<Pesticide> doInBackground(String... strings) {
        pesticideList = mDBHelper.getAllList();
        return pesticideList;
    }

    @Override
    protected void onPostExecute(List<Pesticide> pesticideList) {
        adapter.updateListItems(pesticideList,recyclerView);
//        adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList);
//        rv_items.setAdapter(adapter);
//        Log.d("AllItemsFragment", "updated " + pesticideList.size() + " items");
    }
}
