package com.example.covid19app.oct;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.covid19app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link q_fragment7#newInstance} factory method to
 * create an instance of this fragment.
 */
public class q_fragment7 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioButton rb1;
    RadioButton rb2;
    Button button;
    int point;

    public q_fragment7() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment q_fragment7.
     */
    // TODO: Rename and change types and number of parameters
    public static q_fragment7 newInstance(String param1, String param2) {
        q_fragment7 fragment = new q_fragment7();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_fragment7, container, false);
        Bundle bundle = this.getArguments();
        point = bundle.getInt("point");
        System.out.println(point);
        rb1 = view.findViewById(R.id.radioButton);
        rb2 = view.findViewById(R.id.radioButton2);
        button = view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                execute();
            }
        });

        return view;
    }

    public void execute() {
        Bundle bundle = this.getArguments();
        functions.execute(getActivity(),bundle,this, new q_fragment8(), rb1, rb2, point,3);
    }
}