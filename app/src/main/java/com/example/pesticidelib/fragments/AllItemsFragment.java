package com.example.pesticidelib.fragments;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pesticidelib.R;
import com.example.pesticidelib.adapters.RecyclerViewDataAdapter;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.DatabaseHelper;

import java.io.IOException;
import java.util.List;

public class AllItemsFragment extends Fragment {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private RecyclerView rv_items;
    private List<Pesticide> pesticideList;


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

        pesticideList = mDBHelper.getAllList();
//        for (Pesticide pesticide : pesticideList) {
//            Log.d("logd", pesticide.toString());
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        rv_items = (RecyclerView) view.findViewById(R.id.rv_items);
        ImageView imv_save = (ImageView) view.findViewById(R.id.imv_save);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setHasFixedSize(true);
        rv_items.setAdapter(new RecyclerViewDataAdapter(this.getContext(), pesticideList));
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
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.yourId).setOnClickListener(this);
//
//        // or
//        getActivity().findViewById(R.id.yourId).setOnClickListener(this);
//    }
}