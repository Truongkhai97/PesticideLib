package com.example.pesticidelib.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pesticidelib.R;
import com.example.pesticidelib.activities.PesticideEditActivity;
import com.example.pesticidelib.activities.PesticideInfoActivity;
import com.example.pesticidelib.models.Pesticide;
import com.example.pesticidelib.utilities.ConvertToEng;
import com.example.pesticidelib.utilities.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements ConvertToEng {
    private Pesticide pesticide;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDB;
    private String TAG = "AddFragment";

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        EditText edt_themten = view.findViewById(R.id.edt_them_ten);
        EditText edt_themhoatchat = view.findViewById(R.id.edt_them_hoat_chat);
        EditText edt_themnhom = view.findViewById(R.id.edt_them_nhom);
        EditText edt_themdoituongphongtru = view.findViewById(R.id.edt_them_doi_tuong_phong_tru);
        EditText edt_themtochucdangky = view.findViewById(R.id.edt_them_to_chuc_dang_ky);
        Button btn_them = view.findViewById(R.id.btn_them);

        LinearLayout linearLayout = view.findViewById(R.id.add_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if no view has focus:
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
            }
        });

        mDBHelper = DatabaseHelper.getInstance();
        mDB = mDBHelper.getWritableDatabase();

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check null
                if (TextUtils.isEmpty(edt_themten.getText())) {
                    edt_themten.setError("T??n kh??ng ???????c ????? tr???ng");
                    edt_themten.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(edt_themnhom.getText())) {
                    edt_themnhom.setError("Nh??m kh??ng ???????c ????? tr???ng");
                    edt_themnhom.requestFocus();
                    return;

                } else if (TextUtils.isEmpty(edt_themdoituongphongtru.getText())) {
                    edt_themdoituongphongtru.setError("?????i t?????ng ph??ng tr??? kh??ng ???????c ????? tr???ng");
                    edt_themdoituongphongtru.requestFocus();
                    return;
                }

                //lay du lieu
                pesticide = new Pesticide(null, edt_themten.getText().toString(), convertToEng(edt_themten.getText().toString()), edt_themhoatchat.getText().toString(), edt_themnhom.getText().toString(), edt_themdoituongphongtru.getText().toString(), convertToEng(edt_themdoituongphongtru.getText().toString()), edt_themtochucdangky.getText().toString(), convertToEng(edt_themtochucdangky.getText().toString()), 0);

                new AlertDialog.Builder(getContext())
                        .setTitle("Th??m thu???c")
                        .setMessage("Th??ng tin ????a v??o c?? th??? ch??a ???????c ki???m ?????nh, b???n c?? mu???n ti???n ti???p t???c?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //OK
                                //tien hanh insert
                                if (mDBHelper.addItem(pesticide) == true) {
                                    //thong bao da them
                                    Toast.makeText(view.getContext(), "???? th??m", Toast.LENGTH_LONG).show();
                                    //reset du lieu
                                    pesticide = null;
                                    edt_themten.setText("");
                                    edt_themhoatchat.setText("");
                                    edt_themnhom.setText("");
                                    edt_themdoituongphongtru.setText("");
                                    edt_themtochucdangky.setText("");
                                } else {
                                    Toast.makeText(view.getContext(), "Th??m kh??ng th??nh c??ng, t??n c?? th??? ???? t???n t???i", Toast.LENGTH_LONG).show();
//                                    edt_themten.setError("T??n c?? th??? ???? t???n t???i");
                                    edt_themten.requestFocus();
                                }
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


//                //quay lai giao dien truoc
////                onBackPressed();
//                Intent intent = new Intent(view.getContext(), PesticideInfoActivity.class);
//                intent.putExtra("pesticide", pesticide);
////                intent.putExtra("tab", MainActivity.getCurrentFragment());
//                startActivity(intent);


            }
        });

        return view;
    }
}