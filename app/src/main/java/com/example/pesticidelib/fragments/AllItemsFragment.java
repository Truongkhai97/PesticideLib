package com.example.pesticidelib.fragments;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Toast;

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
    private String choice = "1";
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
//        menu.setGroupCheckable(R.id.searchOptions, true, true);
        getActivity().getMenuInflater().inflate(R.menu.all_items_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

//        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//                Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.filter);
//        actionbar.setOverflowIcon(drawable);

        MenuItem itemSearchView = menu.findItem(R.id.action_search);
        menu.getItem(1).setChecked(true);
        searchView = (SearchView) itemSearchView.getActionView();
        searchView.setQueryHint("T??n thu???c");
//        searchView.setBackground(R.drawable.rect_border);
//        searchView.setBackgroundColor(getResources().getColor(R.color.LimeGreen));
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

        MenuItem itemSearchByGroup = menu.findItem(R.id.option_search_by_group);
        itemSearchByGroup.getSubMenu().setGroupCheckable(R.id.searchOptions, true, true);

        menu.findItem(R.id.option_search_by_name).setChecked(true);
//        menu.findItem(R.id.option_search_by_group).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: true");
//        SearchView searchView = (SearchView) menu.getItem(0).getActionView();
        switch (item.getItemId()) {
            case R.id.option_search_by_name:
                pesticideList = mDBHelper.getAllList();
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "1";
                searchView.setQueryHint("T??n thu???c");
                break;
            case R.id.option_search_by_disease:
                pesticideList = mDBHelper.getAllList();
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "2";
                searchView.setQueryHint("T??n b???nh ho???c lo???i c??y");
                break;
            case R.id.option_search_by_active_ingredient:
                pesticideList = mDBHelper.getAllList();
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "3";
                searchView.setQueryHint("T??n ho???t ch???t");
                break;
            case R.id.option_search_by_producer:
                pesticideList = mDBHelper.getAllList();
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "4";
                searchView.setQueryHint("T??n nh?? s???n xu???t");
                break;

            case R.id.option_search_by_group:
                item.setChecked(true);
                menu.findItem(R.id.action_search).expandActionView();
                menu.findItem(R.id.option_search_null).setChecked(true);
//                item.setChecked(true);
                choice = "5";
                searchView.setQueryHint("Search");
                if (menu.findItem(R.id.option_search_by_group1).isChecked()) {
                    pesticideList = mDBHelper.getAllList();
                } else if (menu.findItem(R.id.option_search_by_group2).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? b???nh", 3);
                else if (menu.findItem(R.id.option_search_by_group3).isChecked()) {
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? s??u", 3);
                } else if (menu.findItem(R.id.option_search_by_group4).isChecked()) {
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? c???", 3);
                } else if (menu.findItem(R.id.option_search_by_group5).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? ???c", 3);
                else if (menu.findItem(R.id.option_search_by_group6).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? m???i", 3);
                else if (menu.findItem(R.id.option_search_by_group7).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? chu???t", 3);
                else if (menu.findItem(R.id.option_search_by_group8).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c ??i???u h??a sinh tr?????ng", 3);
                else if (menu.findItem(R.id.option_search_by_group9).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Ch???t d???n d??? c??n tr??ng", 3);
                else if (menu.findItem(R.id.option_search_by_group10).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c kh??? tr??ng kho", 3);
                else if (menu.findItem(R.id.option_search_by_group11).isChecked())
                    pesticideList = mDBHelper.getSearchedItems("Thu???c b???o qu???n l??m s???n", 3);

                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

//                menu.getItem(5).getSubMenu().getItem(0).setChecked(true);
//                menu.getItem(5).getSubMenu().getItem(1).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(2).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(3).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(4).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(5).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(6).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(7).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(8).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(9).setChecked(false);
//                menu.getItem(5).getSubMenu().getItem(10).setChecked(false);
                break;

            case R.id.option_search_by_group1:
                pesticideList = mDBHelper.getAllList();
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "a";
                searchView.setQueryHint("T???t c??? thu???c");
                break;
            case R.id.option_search_by_group2:
                pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? b???nh", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "b";
                searchView.setQueryHint("Thu???c tr??? b???nh");
//                searchView.setQuery("Thu???c tr??? b???nh", true);
                break;
            case R.id.option_search_by_group3:
                pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? s??u", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "c";
                searchView.setQueryHint("Thu???c tr??? s??u");
//                searchView.setQuery("Thu???c tr??? s??u", true);
                break;
            case R.id.option_search_by_group4:
                pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? c???", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "d";
                searchView.setQueryHint("Thu???c tr??? c???");
//                searchView.setQuery("Thu???c tr??? c???", true);
                break;
            case R.id.option_search_by_group5:
                pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? ???c", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "e";
                searchView.setQueryHint("Thu???c tr??? ???c");
//                searchView.setQuery("Thu???c tr??? ???c", true);
                break;
            case R.id.option_search_by_group6:
                pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? m???i", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "f";
                searchView.setQueryHint("Thu???c tr??? m???i");
//                searchView.setQuery("Thu???c tr??? m???i", true);
                break;
            case R.id.option_search_by_group7:
                pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? chu???t", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "g";
                searchView.setQueryHint("Thu???c tr??? chu???t");
//                searchView.setQuery("Thu???c tr??? chu???t", true);
                break;
            case R.id.option_search_by_group8:
                pesticideList = mDBHelper.getSearchedItems("Thu???c ??i???u h??a sinh tr?????ng", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "h";
                searchView.setQueryHint("Thu???c ??i???u h??a sinh tr?????ng");
//                searchView.setQuery("Thu???c ??i???u h??a sinh tr?????ng", true);
                break;
            case R.id.option_search_by_group9:
                pesticideList = mDBHelper.getSearchedItems("Ch???t d???n d??? c??n tr??ng", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "j";
                searchView.setQueryHint("Ch???t d???n d??? c??n tr??ng");
//                searchView.setQuery("Ch???t d???n d??? c??n tr??ng", true);
                break;
            case R.id.option_search_by_group10:
                pesticideList = mDBHelper.getSearchedItems("Thu???c kh??? tr??ng kho", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "k";
                searchView.setQueryHint("Thu???c kh??? tr??ng kho");
//                searchView.setQuery("Thu???c kh??? tr??ng kho", true);
                break;
            case R.id.option_search_by_group11:
                pesticideList = mDBHelper.getSearchedItems("Thu???c b???o qu???n l??m s???n", 3);
                adapter = new RecyclerViewDataAdapter(this.getContext(), pesticideList, rv_items);
                rv_items.setAdapter(adapter);

                menu.findItem(R.id.action_search).expandActionView();
                item.setChecked(true);
                choice = "l";
                searchView.setQueryHint("Thu???c b???o qu???n l??m s???n");
//                searchView.setQuery("Thu???c b???o qu???n l??m s???n", true);
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

            switch (choice) {
                case "b":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? b???nh", 3);
                    break;
                case "c":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? s??u", 3);
                    break;
                case "d":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? c???", 3);
                    break;
                case "e":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? ???c", 3);
                    break;
                case "f":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? m???i", 3);
                    break;
                case "g":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c tr??? chu???t", 3);
                    break;
                case "h":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c ??i???u h??a sinh tr?????ng", 3);
                    break;
                case "j":
                    pesticideList = mDBHelper.getSearchedItems("Ch???t d???n d??? c??n tr??ng", 3);
                    break;
                case "k":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c kh??? tr??ng kho", 3);
                    break;
                case "l":
                    pesticideList = mDBHelper.getSearchedItems("Thu???c b???o qu???n l??m s???n", 3);
                    break;
                default:
                    pesticideList = mDBHelper.getAllList();

            }
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