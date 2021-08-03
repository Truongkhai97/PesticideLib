package com.example.pesticidelib.fragments;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pesticidelib.R;
import com.example.pesticidelib.activities.MainActivity;
import com.example.pesticidelib.adapters.RecyclerViewDataAdapter;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;
import com.example.pesticidelib.utilities.GetAllItemsAsynctask;

import java.io.IOException;
import java.util.List;

public class AllItemsFragment extends Fragment {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private RecyclerView rv_items;
    private List<Pesticide> pesticideList = null;
    private RecyclerViewDataAdapter adapter;
    private final String TAG = "AllItemsFragment";
    private int choice = 1;
    private SearchView searchView = null;
    private Menu menu;


    public AllItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = new DatabaseHelper(this.getContext());

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
        Log.d("logd", "AllItemsFragment-onCreate-count: " + mDBHelper.count());

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        rv_items = (RecyclerView) view.findViewById(R.id.rv_items);
        ImageView imv_save = (ImageView) view.findViewById(R.id.imv_save);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setHasFixedSize(true);
        pesticideList = mDBHelper.getAllList();
        adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);

        rv_items.setAdapter(adapter);

        //su dung assynctask
//        GetAllItemsAsynctask getAllItemsAsynctask=new GetAllItemsAsynctask(mDBHelper, adapter, rv_items);
//        getAllItemsAsynctask.execute();

//        Log.d(TAG, "pesticideList size: "+pesticideList.size());

//        searchView=view.findViewById(R.id.action_search);
//        searchView.setQueryHint("Search");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String text) {
//                Log.d(TAG, "onQueryTextChange: "+text);
//                return true;
//            }
//        });

//        GetAllItemsAsynctask getAllItemsAsynctask = new GetAllItemsAsynctask(mDBHelper, adapter, rv_items);
//        getAllItemsAsynctask.execute();

//        rv_items.setAdapter(new RecyclerViewDataAdapter(this.getContext(), pesticideList));


//        rv_items.addOnItemTouchListener(
//                new RecyclerView.OnItemTouchListener() {
//                    @Override
//                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                        Log.d("TAG", "onTouchEvent: clicked");
//                    }
//
//                    @Override
//                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//                    }
//                }
//        );

        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        this.menu = menu;

        getActivity().getMenuInflater().inflate(R.menu.all_items_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

//        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//                Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.filter);
//        actionbar.setOverflowIcon(drawable);

        MenuItem itemSearchView = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearchView.getActionView();
        searchView.setQueryHint("Tên thuốc");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
//                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                Log.d(TAG, "onQueryTextChange: " + query);
                query = query + choice;
                adapter.getFilter().filter(query);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: true");
//        SearchView searchView = (SearchView) menu.getItem(0).getActionView();
        switch (item.getItemId()) {
            case R.id.option_search_by_name:
                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = 1;
                searchView.setQueryHint("Tên thuốc");
                break;
            case R.id.option_search_by_disease:
                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = 2;
                searchView.setQueryHint("Tên bệnh hoặc loại cây");
                break;
            case R.id.option_search_by_active_ingredient:
                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = 3;
                searchView.setQueryHint("Tên hoạt chất");
                break;
            case R.id.option_search_by_producer:
                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = 4;
                searchView.setQueryHint("Tên nhà sản xuất");
                break;
        }
        adapter.getFilter().filter(searchView.getQuery().toString() + choice);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
//        if (searchView != null & pesticideList != null) {
        if (searchView != null) {
            pesticideList = mDBHelper.getAllList();
            adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
            rv_items.setAdapter(adapter);
            adapter.getFilter().filter(searchView.getQuery().toString() + choice);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.yourId).setOnClickListener(this);
//
//        // or
//        getActivity().findViewById(R.id.yourId).setOnClickListener(this);
//    }
}