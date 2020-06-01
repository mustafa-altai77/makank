package com.example.makank;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.makank.ui.activity.ContactActivity;
import com.example.makank.ui.activity.HomeActivity;
import com.example.makank.ui.activity.PhoneNumberActivity;
import com.example.makank.ui.activity.SendNotifActivity;
import com.example.makank.ui.activity.VolunteerActivity;
import com.example.makank.ui.contact.ContactFragment;
import com.example.makank.ui.home.Home;
import com.example.makank.ui.news.NewsFragment;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Mustafa on 4/26/2020.
 */

public class Alert {
    private Activity activity;
    private AlertDialog dialog;
    int flag;

    TextView t1, t2;
    Button button;

    public Alert(Activity myaActivity) {
        activity = myaActivity;

    }

    public void showSuccessDialog(String title, String message, int whichActivityNeed) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Hacen-Algeria.ttf");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.layout_success_dialog, null);
        builder.setView(view);

        t1 = view.findViewById(R.id.textTitle);
        t1.setText(title);
        t2 = view.findViewById(R.id.textMessage);
        t2.setText(message);
        button = view.findViewById(R.id.buttonAction);
        button.setText(activity.getResources().getString(R.string.ok));
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        button.setTypeface(typeface);
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whichActivityNeed == 1) {
                    Intent intent = new Intent(activity, HomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
                if (whichActivityNeed == 2) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:221"));
                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    alertDialog.dismiss();
                }
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void showErrorDialog(String message) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Hacen-Algeria.ttf");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.layout_error_dialog, null);
        builder.setView(view);

        t1 = view.findViewById(R.id.textTitle);
        t1.setText(activity.getResources().getString(R.string.error));
        t2 = view.findViewById(R.id.textMessage);
        t2.setText(message);
        button = view.findViewById(R.id.buttonAction);
        button.setText(activity.getResources().getString(R.string.ok));
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        button.setTypeface(typeface);
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_error);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void showWarningDialog() {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Hacen-Algeria.ttf");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.layout_warning_dialog, null);
        builder.setView(view);

        t1 = view.findViewById(R.id.textTitle);
        t1.setText(activity.getResources().getString(R.string.error_network));
        t2 = view.findViewById(R.id.textMessage);
        t2.setText(activity.getResources().getString(R.string.error_network_message));
        button = view.findViewById(R.id.buttonNo);
        button.setText(activity.getResources().getString(R.string.try_again));
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        button.setTypeface(typeface);
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void showRegisterDialog() {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Hacen-Algeria.ttf");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.layout_register_dialog, null);
        builder.setView(view);
        t1 = view.findViewById(R.id.textTitle);
        t1.setText(activity.getResources().getString(R.string.register));
        t2 = view.findViewById(R.id.textMessage);
        t2.setText(activity.getResources().getString(R.string.registerMessage));
        Button buttonNo = view.findViewById(R.id.buttonNo);
        buttonNo.setText(activity.getResources().getString(R.string.cancel));
        Button buttonYes = view.findViewById(R.id.buttonYes);
        buttonYes.setText(activity.getResources().getString(R.string.ok));
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        buttonNo.setTypeface(typeface);
        buttonYes.setTypeface(typeface);
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_person);
        final AlertDialog alertDialog = builder.create();
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PhoneNumberActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
    //___________________________Testing Activity
    public void showAlertSuccess(String title,String message) {
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(activity.getResources().getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent=new Intent(activity,HomeActivity.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }
                })
                .show();
    }

    public void  showAlertError(String title,String message) {
        activity.setFinishOnTouchOutside(false);
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(activity.getResources().getString(R.string.ok))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:221"));
                        activity.startActivity(intent);
                        activity.finish();
                        sDialog.cancel();
                    }
                })
                .show();
    }
}