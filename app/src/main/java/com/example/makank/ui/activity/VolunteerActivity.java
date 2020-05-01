package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Filresponse;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class VolunteerActivity extends AppCompatActivity implements View.OnClickListener, OnLoadCompleteListener,OnPageChangeListener,OnPageErrorListener {
    private int pageNumber = 0;

    private ImageView ivImage;
    private TextView tvFileName;
    private Button ok;
    private String pdfFileName;
    private PDFView pdfView;
    public static final int REQUEST_PERMISSION = 200;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        ivImage = findViewById(R.id.ivImage);
        tvFileName = findViewById(R.id.txt_message);
        ok = findViewById(R.id.confirm);
        tvFileName.setOnClickListener(this);
        ok.setOnClickListener(this);
        initDialog();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

    }
    @Override
    public void onClick(View v) {
        if (v == ivImage) {
            launchPicker();
        }
        if (v == ok) {
            SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String my_id = sharedPreference.getString(USER_ID, "id");
            uploadImage(my_id);
        }

    }
    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Select PDF file")
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
//            displayFromFile(file);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
            }
        }

    }
//    private void displayFromFile(File file) {
//
//        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
//        pdfFileName = getFileName(uri);
//
//        pdfView.fromFile(file)
//                .defaultPage(pageNumber)
//                .onPageChange((OnPageChangeListener) this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .spacing(10) // in dp
//                .onPageError((OnPageErrorListener) this)
//                .load();
//    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


    public void uploadImage(String id) {
        if (pdfPath == null) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        } else {
            showpDialog();

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(pdfPath);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
            Call<Filresponse> call = apiService.upload(id, map);
            call.enqueue(new Callback<Filresponse>() {
                @Override
                public void onResponse(Call<Filresponse> call, Response<Filresponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            hidepDialog();
                            Filresponse serverResponse = response.body();
                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), "problem image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Filresponse> call, Throwable t) {
                    hidepDialog();
                    Log.v("Response gotten is", t.getMessage());
                    Toast.makeText(getApplicationContext(), "problem uploading image " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading..");
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void onPageError(int page, Throwable t) {

    }
}








