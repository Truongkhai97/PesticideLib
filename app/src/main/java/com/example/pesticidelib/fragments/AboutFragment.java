package com.example.pesticidelib.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.pesticidelib.R;
import com.example.pesticidelib.utilities.HideVirtualKeyBoard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment implements HideVirtualKeyBoard {
    EditText your_name, your_email, your_subject, your_message;
    Button email;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        your_name = (EditText) view.findViewById(R.id.your_name);
        your_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        your_email = (EditText) view.findViewById(R.id.your_email);
        your_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        your_subject = (EditText) view.findViewById(R.id.your_subject);
        your_subject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        your_message = (EditText) view.findViewById(R.id.your_message);
        your_message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        email = (Button) view.findViewById(R.id.post_message);


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = your_name.getText().toString();

                String email = your_email.getText().toString();

                String subject = your_subject.getText().toString();

                String message = your_message.getText().toString();

                if (TextUtils.isEmpty(name)) {

                    your_name.setError("Tên không được để trống");

                    your_name.requestFocus();

                    return;

                }

                Boolean onError = false;

                if (!isValidEmail(email)) {

                    onError = true;

                    your_email.setError("Email không hợp lệ");

                    return;

                }

                if (TextUtils.isEmpty(subject)) {

                    your_subject.setError("Nhập vào tiêu đề");

                    your_subject.requestFocus();

                    return;

                }

                if (TextUtils.isEmpty(message)) {

                    your_message.setError("Nhập vào nội dung");

                    your_message.requestFocus();

                    return;

                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                sendEmail.setType("plain/text");

                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"truongkhai0708@gmail.com"});

                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,

                        "name:" + name + '\n' + "Email ID:" + email + '\n' + "Message:" + '\n' + message);

                /* Send it off to the Activity-Chooser */

                startActivity(Intent.createChooser(sendEmail, "Send mail..."));

            }

        });
        return view;
    }

    private boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }

    @Override
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}