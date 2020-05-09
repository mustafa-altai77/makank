package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Disease;
import com.example.makank.adapter.DiseaseAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class InstructionsActivity extends AppCompatActivity {
    WebView webView;
    Typeface typeface;
    TextView question;
    LoadingDialog loadingDialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        question=findViewById(R.id.quet);
        webView=findViewById(R.id.web_view_);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://primaryKeysd.com/covid/index.html");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        question.setTypeface(typeface);
        loadingDialog=new LoadingDialog(this);
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
          //  loadingDialog.startLoadingDialog();
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
          //  loadingDialog.dismissDialog();
        }
        super.onBackPressed();
    }
}
