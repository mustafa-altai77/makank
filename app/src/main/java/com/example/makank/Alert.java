package com.example.makank;

import android.app.Activity;
import android.app.AlertDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Mustafa on 4/26/2020.
 */

public class Alert {
    private Activity activity;
    private AlertDialog dialog;

    public Alert(Activity myaActivity) {
        activity = myaActivity;
    }
    public void showAlertSuccess(String y) {
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("تم اتمام العملية بنجاح")
                .setContentText(y)
                .setConfirmText("موافق")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                    }
                })
                .show();
    }

    public void  showAlertError(String x) {
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(x)
                .showCancelButton(true)
                .setCancelText("موافق")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

}