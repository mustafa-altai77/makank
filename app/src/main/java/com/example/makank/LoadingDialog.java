package com.example.makank;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mustafa on 4/24/2020.
 */

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    TextView textView;
    Typeface typeface;

    public LoadingDialog(Activity myaActivity) {
        activity = myaActivity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.custom_dialog2, null));
        View view = inflater.inflate(R.layout.custom_dialog2, null);
        builder.setView(view);
        textView = view.findViewById(R.id.txtWa);
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Hacen-Algeria.ttf");
        textView.setTypeface(typeface);
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
    public void startLoadingDialog(boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.custom_dialog2, null));
        View view = inflater.inflate(R.layout.custom_dialog2, null);
        builder.setView(view);
        textView = view.findViewById(R.id.txtWa);
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Hacen-Algeria.ttf");
        textView.setTypeface(typeface);
        dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

   /*LoadingDialog(Activity myaActivity) {
       activity = myaActivity;
   }
   void startLoadingDialog() {
       LayoutInflater factory = LayoutInflater.from(activity);
       View DialogView = factory.inflate(R.layout.custom_dialog2, null);
       Dialog main_dialog = new Dialog(activity,R.style.SpinKitView_Large_CubeGrid);
       main_dialog.setContentView(DialogView);

       dialog=(ProgressBar)DialogView.findViewById(R.id.spinKit);
       main_dialog.setCancelable(false);
       main_dialog.setCanceledOnTouchOutside(false);
       dialog.setProgress(0);
       dialog.setMax(100);
       main_dialog.show();
   }*/
}