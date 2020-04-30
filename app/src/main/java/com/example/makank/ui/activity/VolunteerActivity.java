package com.example.makank.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.service.autofill.FillResponse;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.makank.FilePath;
import com.example.makank.R;
import com.example.makank.data.ApiClient;
import com.example.makank.data.ApiInterface;
import com.example.makank.data.Filresponse;
import com.example.makank.data.PdfClass;
import com.example.makank.data.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.webkit.WebSettings.RenderPriority.HIGH;
import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;

public class VolunteerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String APP_FONT = "fonts/jana.ttf";
    private static final String TAG = "UploadImageActivity";
    private static final int PICK_FILE_REQUEST = 1;
    public static final int REQUEST_PERMISSION = 200;
    private String mCurrentPhotoPath;
    private ImageView ivImage;
    private TextView tvFileName;
    private Button ok;
    private static final int PDF_REQUEST = 777;
    private Bitmap bitmap;
    private static final int REQUEST_GALLERY_IMAGE = 1456;

    public int PDF_REQ_CODE = 1;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        ivImage = findViewById(R.id.ivImage);
        tvFileName = findViewById(R.id.txt_message);
        ok = findViewById(R.id.confirm);
        tvFileName.setOnClickListener(this);
        ok.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivImage.setImageBitmap(bitmap);

            mCurrentPhotoPath = getRealPathFromURI(data.getData());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @Override
    public void onClick(View v) {
        if (v == ivImage) {
            Intent intent = new  Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_GALLERY_IMAGE);        }
        if (v == ok) {
            SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String my_id = sharedPreference.getString(USER_ID, "id");
            PdfClass imageUploadInputModel = new PdfClass();
            imageUploadInputModel.setUserID(my_id);
            File imgFile = new File(mCurrentPhotoPath);

            if(imgFile.exists())

            {

                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivImage.setImageBitmap(bitmap);

            }            imageUploadInputModel.setImage(imgFile);
            if (mCurrentPhotoPath != null) {

                uploadImage(imageUploadInputModel);
            }
        }
    }

    private void selectPdf() {
        Intent intent = new Intent();

        intent.setType("application/pdf");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);
    }

    public void uploadImage(final PdfClass imageUploadInputModel) {

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), imageUploadInputModel.toString());
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), imageUploadInputModel.getImage());
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);

        final Call<Filresponse> response = apiService.uploadImage(userId, image);

        response.enqueue(new Callback<Filresponse>() {
            @Override
            public void onResponse(Call<Filresponse> call, Response<Filresponse> response) {
                if (response.body() != null && response.body().equals(200)) {
                    Log.v("", "success " + response.code() + "body: " + response.body().toString());
                    // send call back

                } else {
                    if (response != null && response.body() != null && response.body().getError() != null)
                        Toast.makeText(VolunteerActivity.this, response.body() + "", Toast.LENGTH_SHORT).show();
                    // Handle error here
                }
            }

            @Override
            public void onFailure(Call<Filresponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.v("", "Network Error");
                }
            }

        });
    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    }








