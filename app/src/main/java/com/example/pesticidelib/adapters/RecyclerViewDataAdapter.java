package com.example.pesticidelib.adapters;

import android.annotation.SuppressLint;
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
import com.example.pesticidelib.utilities.ConvertToEng;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.PesticideDataViewHolder> implements Filterable, ConvertToEng {
    private List<Pesticide> pesticideList;
    private Context context;
    private final String TAG = "RecyclerViewDataAdapter";
    private boolean show_stack_trace = false;
    private List<Pesticide> pesticideListFiltered;
    private RecyclerView recyclerView;
    private String choice = "1";

    public RecyclerViewDataAdapter(Context context, List<Pesticide> pesticideList) {
        this.context = context;
        this.pesticideList = pesticideList;
        this.pesticideListFiltered = pesticideList;
    }

//    public RecyclerViewDataAdapter(Context context, List<String> stringList,RecyclerView recyclerView, int choice) {
//        this.context = context;
//    }

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

//        switch (choice) {
//            case 2:
//                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_layout, parent, false);
//                break;
//            case 3:
//                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_ingredient_layout, parent, false);
//                break;
//            case 4:
//                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.manufacturer_layout, parent, false);
//                break;
//            default:
//                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesticide_layout, parent, false);
//        }

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesticide_layout, parent, false);
        return new PesticideDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PesticideDataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = pesticideListFiltered.get(position).getTen();
        if (name.length() > 45) {
            name = name.substring(0, 45) + "...";
        }

        switch (choice) {
            case "2":
                String doituongphongtru = pesticideListFiltered.get(position).getDoituongphongtru();
                holder.tvName.setText(name);
                holder.tvType.setText(doituongphongtru);
                break;
            case "3":
                String hoatchat = pesticideListFiltered.get(position).getHoatchat();
                holder.tvName.setText(name);
                holder.tvType.setText(hoatchat);
                break;
            case "4":
                String tochucdangky = pesticideListFiltered.get(position).getTochucdangky();
                holder.tvName.setText(name);
                holder.tvType.setText(tochucdangky);
                break;
            default:
                String nhom = pesticideListFiltered.get(position).getNhom();
                holder.tvName.setText(name);
                holder.tvType.setText(nhom);
        }


        if (pesticideListFiltered.get(position).getIsSaved() == 1) {
            holder.imvSave.setImageResource(R.drawable.ic_star_yellow_24dp);
//        } else holder.imvSave.setImageResource(R.drawable.ic_star_white_24dp);
        } else holder.imvSave.setImageResource(R.drawable.ic_star_white_16dp_black_border);

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
//        recyclerView.getRecycledViewPool().clear();
//        if(show_stack_trace) Log.d(TAG,"updateList", new RuntimeException().fillInStackTrace());
        pesticideListFiltered = list;
        notifyDataSetChanged();
    }

    public void updateListItems(List<Pesticide> list, RecyclerView recyclerView) {
        recyclerView.getRecycledViewPool().clear();
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
//                choice = Integer.parseInt(charSequence.toString().substring(charSequence.length() - 1, charSequence.length()));
                choice = charSequence.toString().substring(charSequence.length() - 1, charSequence.length());
                charSequence = charSequence.toString().substring(0, charSequence.length() - 1);

                String charString = charSequence.toString().trim().replaceAll("\\s+", " ").toLowerCase();
//                Log.d("AllItemsFragment", "performFiltering: " + charString);

                //dung cho tim kiem khong dau
                String charString_ascii = convertToEng(charString.toString());
                List<Pesticide> filteredList;
                FilterResults filterResults = new FilterResults();
                if (charString.isEmpty()) {
                    filterResults.values = pesticideList;
                    return filterResults;
                } else {
                    filteredList = new ArrayList<>();
                    switch (choice) {
                        case "2":
                            for (Pesticide row : pesticideList) {
//                                if (convertToEng(row.getDoituongphongtru()).toLowerCase().contains(charStringEng.toLowerCase())) {
                                if (row.getDoituongphongtru_ascii().toLowerCase().contains(charString_ascii)) {
                                    filteredList.add(row);
                                }
                            }
                            break;
                        case "3":
                            for (Pesticide row : pesticideList) {
//                                if (row.getHoatchat().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
//                                if (convertToEng(row.getHoatchat()).toLowerCase().contains(charStringEng.toLowerCase())) {
                                if (row.getHoatchat().toLowerCase().contains(charString_ascii)) {
                                    filteredList.add(row);
                                }
                            }
                            break;
                        case "4":
                            for (Pesticide row : pesticideList) {
//                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
//                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
                                if (row.getTochucdangky_ascii().toLowerCase().contains(charString_ascii)) {
                                    filteredList.add(row);
                                }
                            }
                            break;
//                        case "a":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "b":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc trừ bệnh")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "c":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc trừ sâu")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "d":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc trừ cỏ")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "e":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc trừ ốc")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "f":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc trừ mối")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "g":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc trừ chuột")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "h":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc điều hòa sinh trưởng")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "j":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Chất dẫn dụ côn trùng")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "k":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc khử trùng kho")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
//                        case "l":
//                            for (Pesticide row : pesticideList) {
////                                if (row.getTochucdangky().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
////                                if (convertToEng(row.getTochucdangky()).toLowerCase().contains(charStringEng.toLowerCase())) {
//                                if (row.getNhom().contains("Thuốc bảo quản lâm sản")) {
//                                    filteredList.add(row);
//                                }
//                            }
//                            break;
                        default:
                            for (Pesticide row : pesticideList) {
//                                if (row.getTen().toLowerCase().contains(charString.toLowerCase()) /*|| row.getHoatchat().contains(charSequence)*/) {
//                                if (convertToEng(row.getTen()).toLowerCase().contains(charStringEng.toLowerCase())) {
                                if (row.getTen_ascii().toLowerCase().contains(charString_ascii)) {
                                    filteredList.add(row);
//                                    Log.d(TAG, "performFiltering: added");
                                }
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

//    //ham ho tro tim kiem khong dau
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

