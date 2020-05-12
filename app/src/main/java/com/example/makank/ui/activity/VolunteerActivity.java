package com.example.makank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;
import com.example.makank.data.model.Filresponse;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.makank.SharedPref.F_NAME;
import static com.example.makank.SharedPref.L_NAME;
import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.S_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class VolunteerActivity extends AppCompatActivity implements View.OnClickListener, OnLoadCompleteListener, OnPageChangeListener, OnPageErrorListener {
    private int pageNumber = 0;

    private ImageView ivImage;
    private TextView tvFileName;
    private Button ok;
    private String pdfFileName;
    private PDFView pdfView;
    private CheckBox aprove;
    public static final int REQUEST_PERMISSION = 200;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    TextView volunteerName, condition, message, approve, conn, txtSTEP, volLable;
    Typeface typeface;
    LoadingDialog loadingDialog;
    Alert alert;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        ivImage = findViewById(R.id.ivImage);
        tvFileName = findViewById(R.id.txt_message);
        pdfView = findViewById(R.id.pdfView);
        ok = findViewById(R.id.confirm);
        aprove =findViewById(R.id.txtAprove);
        tvFileName.setOnClickListener(this);
        txtSTEP = findViewById(R.id.txtSteps);
        txtSTEP.setText("يسعدنا انضمامكم للحملة الوطنية لمكافحة فيروس \nكورونا (كوفيد-19) و رداً على التساؤلات بشأن كيفية \nالمشاركة في الجهود الحكومية والمساعدة في حملة مكافحة فيروس كورونا المنتشر في دولة السودان .\nبدأت الحملة الوطنية لمكافحة فيروس كورونا" +
                "(كوفيد-19) بتطوير الأنشطة التطوعية وتحديد مجالات العمل ,يمكنك أدناه ارفاع المستند الخاص بك ولكم فرصة المشاركة في الجهود الوطنية من خلال الموقة" +
                " ");
        conn = findViewById(R.id.txtCon);
        ok = findViewById(R.id.confirm);
        volunteerName = findViewById(R.id.voluName);
        condition = findViewById(R.id.txtCon);
        message = findViewById(R.id.txt_message);
        approve = findViewById(R.id.txtAprove);
        volLable = findViewById(R.id.infoVol);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        txtSTEP.setTypeface(typeface);
        volunteerName.setTypeface(typeface);
        condition.setTypeface(typeface);
        message.setTypeface(typeface);
        approve.setTypeface(typeface);
        conn.setTypeface(typeface);
        txtSTEP.setTypeface(typeface);
        volLable.setTypeface(typeface);
        ok.setTypeface(typeface);

        ok.setOnClickListener(this);
        SharedPreferences idPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        final String my_id = idPref.getString(USER_ID, "id");
        final String f_name = idPref.getString(F_NAME, "f_name");
        final String s_name = idPref.getString(S_NAME, "s_name");
        final String l_name = idPref.getString(L_NAME, "l_name");
        volunteerName.setText("مرحبا : " + f_name +" "+ s_name +" "+ l_name);
        initDialog();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        alert = new Alert(this);
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivImage) {
            launchPicker();
        }
        if (v == ok) {
            SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String my_id = sharedPreference.getString(USER_ID, "id");
            if (aprove.isChecked()) {

                uploadImage(my_id);
            }else alert.showAlertError("يجب الموافقة على الشروط");
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
            displayFromFile(file);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                // Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
                alert.showAlertSuccess("تم", "إرفاق الملف قم بالضغط على أوافق للإرسال", "موافق");
                tvFileName.setText(path);
            }
        }

    }

    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);

        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange((OnPageChangeListener) this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError((OnPageErrorListener) this)
                .load();
    }

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
            //Toast.makeText(this, "please select file", Toast.LENGTH_LONG).show();
            alert.showAlertError("الرجاء إرفاق الملف");
            return;
        } else {
//            showpDialog();
            loadingDialog.startLoadingDialog();

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(pdfPath);
            // Parsing any Media type file
            //RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            //map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
           /* MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);*/
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("cvfile", file.getName(), requestFile);

            // add another part within the multipart request
            RequestBody fullName =
                    RequestBody.create(MediaType.parse("text/plain"), pdfFileName);

            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
            Call<Filresponse> call = apiService.upload(id, id, body);
            call.enqueue(new Callback<Filresponse>() {
                @Override
                public void onResponse(Call<Filresponse> call, Response<Filresponse> response) {
                    if (response.isSuccessful()) {
                        loadingDialog.dismissDialog();
                        if (response.body() != null) {
                            // hidepDialog();
                            alert.showAlertInTest("تــم","إرسال السيرة الذاتية بنجاح","موافـق");
                            Filresponse serverResponse = response.body();
                            //Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // hidepDialog();
                        loadingDialog.dismissDialog();

                        Log.e("error_req", call.toString());
                        Log.e("error_res", response.toString());
                        alert.showAlertError("اسم الملف طويل للغاية");
                    }
                }

                @Override
                public void onFailure(Call<Filresponse> call, Throwable t) {
                    // hidepDialog();
                    Log.v("Response gotten is", t.getMessage());
                    loadingDialog.dismissDialog();

                    // Toast.makeText(getApplicationContext(), "problem uploading file " + t.getMessage(), Toast.LENGTH_SHORT).show();
                  //  alert.showAlertError("صيغة الملف غير صحيحة");
                   // alert.showAlertSuccess("تم إرسال السيرة الذاتية بنجاح");

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
        // setTitle(String.format("%s  / %s", page + 1, pageCount));
    }

    @Override
    public void onPageError(int page, Throwable t) {

    }
}
