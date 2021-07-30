package com.example.pesticidelib.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pesticidelib.R;
import com.example.pesticidelib.activities.PesticideInfoActivity;
import com.example.pesticidelib.models.Pesticide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.PesticideDataViewHolder> implements Filterable {
    private List<Pesticide> pesticideList;
    private Context context;
    private final String TAG = "RecyclerViewDataAdapter";
    private boolean show_stack_trace = false;
    private List<Pesticide> pesticideListFiltered;
    private RecyclerView recyclerView;

    public RecyclerViewDataAdapter(Context context, List<Pesticide> pesticideList) {
        this.context = context;
        this.pesticideList = pesticideList;
        this.pesticideListFiltered = pesticideList;
    }

    public RecyclerViewDataAdapter(Context context, List<Pesticide> pesticideList, RecyclerView recyclerView) {
        this.context = context;
        this.pesticideList = pesticideList;
        this.pesticideListFiltered = pesticideList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PesticideDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new PesticideDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PesticideDataViewHolder holder, int position) {
        String name = pesticideListFiltered.get(position).getTen();
        if (name.length() > 45) {
            name = name.substring(0, 45) + "...";
        }
        String nhom = pesticideListFiltered.get(position).getNhom();
        holder.tvName.setText(name);
        holder.tvType.setText(nhom);
        if (pesticideListFiltered.get(position).getIsSaved() == 1) {
            holder.imvSave.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else holder.imvSave.setImageResource(R.drawable.ic_star_white_24dp);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Long clicked", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onLongClick: ");
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PesticideInfoActivity.class);
                //doi voi activity
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                //doi voi fragment
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pesticide", pesticideListFiltered.get(position));

//                Fragment f = (Activity)context.getFragmentManager().findFragmentById(R.id.fragment_container);
//                if(f instanceof CustomFragmentClass)
//                    // do something with f
//                    ((CustomFragmentClass) f).doSomething();
//
//                Fragment fragment=(Activity)context


//                intent.putExtra("tab", MainActivity.getCurrentFragment());
//                Log.d("logd", "fragment: "+MainActivity.getCurrentFragment());
                v.getContext().startActivity(intent);
            }
        });
    }

    //https://stackoverflow.com/questions/40754174/android-implementing-search-filter-to-a-recyclerview
    public void updateList(List<Pesticide> list) {
//        if(show_stack_trace) Log.d(TAG,"updateList", new RuntimeException().fillInStackTrace());
        pesticideListFiltered = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pesticideListFiltered == null ? 0 : pesticideListFiltered.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (pesticideList.get(position).isMale())
//            return 1;
//        else return 2;
        return -1;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                Log.d(TAG, "performFiltering: called");
//                String charString = charSequence.toString();
                String charString = charSequence.toString().trim().replaceAll("\\s+", " ");
                List<Pesticide> filteredList;
                FilterResults filterResults = new FilterResults();
                if (charString.isEmpty()) {
                    filterResults.values = pesticideList;
                    return filterResults;
                } else {
                    filteredList = new ArrayList<>();
                    for (Pesticide row : pesticideList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTen().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
                            filteredList.add(row);
//                            Log.d(TAG, "performFiltering: added");
                        }
                    }

//                    pesticideListFiltered = filteredList;
                }

//                filterResults.values = pesticideListFiltered;
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                recyclerView.getRecycledViewPool().clear();
//                notifyItemRangeRemoved(0,pesticideListFiltered.size());
                pesticideListFiltered = (ArrayList<Pesticide>) filterResults.values;
                // refresh the list with filtered data
                Log.d(TAG, "items found: " + pesticideListFiltered.size());

                notifyDataSetChanged();
            }
        };
    }

//    @Override
//    public void onClick(View view) {
//
//    }

    public static class PesticideDataViewHolder extends RecyclerView.ViewHolder /*implements ItemClickListener*/ {
        private TextView tvName;
        private TextView tvType;
        private ImageView imvSave;

        public PesticideDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
            imvSave = itemView.findViewById(R.id.imv_save);
        }

//        @Override
//        public void onClick(View view) {
//            Log.d("click", "onClick: ");
//        }
    }

//    public String getLastestFragment(){
//        int index = getActivity().getFragmentManager().getBackStackEntryCount() - 1;
//        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
//        String tag = backEntry.getName();
//        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
//    }
}

