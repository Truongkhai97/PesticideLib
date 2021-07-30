package com.example.pesticidelib.fragments;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private SearchView searchView;


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
//        pesticideList = mDBHelper.getAllList();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        rv_items = (RecyclerView) view.findViewById(R.id.rv_items);
        ImageView imv_save = (ImageView) view.findViewById(R.id.imv_save);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setHasFixedSize(true);
        pesticideList=mDBHelper.getAllList();
        adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList,rv_items);
//        ArrayAdapter mAdapter = new ArrayAdapter(MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                getResources().getStringArray(R.array.sort_option));

        rv_items.setAdapter(adapter);

//        rv_items.setAdapter(mAdapter);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem itemSearchView = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) itemSearchView.getActionView();
        searchView.setQueryHint("Search");

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
//                Log.d(TAG, "onQueryTextChange: " + query);
                adapter.getFilter().filter(query);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Log.d(TAG, "onOptionsItemSelected: true");


        return super.onOptionsItemSelected(item);
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