package com.example.covid19app.oct;


import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.covid19app.R;

public class functions {
    public static boolean check_radios(RadioButton rb1, RadioButton rb2){
        if (rb1.isChecked() || rb2.isChecked()) {
            return true;
        }
        return false;
    }

    public static void replace_fragment(FragmentActivity activity, Fragment fragment1, Fragment fragment2, int point){
        Bundle bundle = new Bundle();
        bundle.putInt("point",point);
        fragment2.setArguments(bundle);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment2);
        fragmentTransaction.commit();
    }

    public static int increase_point(RadioButton rb1, int point, int how_much_increase) {
        if (rb1.isChecked()) {
            return point+how_much_increase;
        }
        return point;
    }

    public static int increase_point(Bundle bundle, RadioButton rb1, int point, int how_much_increase) {
        if (rb1.isChecked()) {
            try {
                point = bundle.getInt("point");
                return point+how_much_increase;
            } catch (Exception e) {

            }

        }
        return point;
    }
    public static void execute(FragmentActivity activity,Fragment from_fragment, Fragment target_fragment,  RadioButton rb1, RadioButton rb2, int point, int how_much_increase){
        boolean state = functions.check_radios(rb1,rb2);
        if(state){
            point = functions.increase_point(rb1,point, how_much_increase);
            functions.replace_fragment(activity,from_fragment, target_fragment, point);
        }
        else{
            show_alert(activity,"Warning!","Please choose an option.","OK");
        }
    }

    public static void execute(FragmentActivity activity,Bundle bundle, Fragment from_fragment, Fragment target_fragment,  RadioButton rb1, RadioButton rb2, int point, int how_much_increase){
        boolean state = functions.check_radios(rb1,rb2);
        if(state){
            point = functions.increase_point(bundle,rb1,point, how_much_increase);
            functions.replace_fragment(activity,from_fragment, target_fragment, point);
        }
        else{
            show_alert(activity,"Warning!","Please choose an option.","OK");
        }
    }
    private static void show_alert(FragmentActivity activity, String title, String message, String button_text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }
}
