package com.example.makank.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makank.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;
import static com.example.makank.SharedPref.AGE;
import static com.example.makank.SharedPref.F_NAME;
import static com.example.makank.SharedPref.GENDER;
import static com.example.makank.SharedPref.L_NAME;
import static com.example.makank.SharedPref.PHONE;
import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.STATUS;
import static com.example.makank.SharedPref.S_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;


public class PersonalFragment extends Fragment {
    TextView F_name,S_name,L_name, F_name2,S_name2,L_name2,gen,age_,ph ,personalID;
    ImageView qrImage ,statusImage;

    String inputValue;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        F_name = view.findViewById(R.id.f_na);
        S_name = view.findViewById(R.id.s_na);
        L_name = view.findViewById(R.id.l_na);

        F_name2 = view.findViewById(R.id.f_name);
        S_name2 = view.findViewById(R.id.s_name);
        L_name2 = view.findViewById(R.id.l_name);
        gen = view.findViewById(R.id.gender_pref);
        age_ = view.findViewById(R.id.age_prf);
        ph = view.findViewById(R.id.phone);
        qrImage = view.findViewById(R.id.qr_person);
        statusImage=  view.findViewById(R.id.stat);
        personalID = view.findViewById(R.id.personal_id);

        SharedPreferences idPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        final String my_id = idPref.getString(USER_ID, "id");
        final String f_name = idPref.getString(F_NAME, "f_name");
        final String s_name = idPref.getString(S_NAME, "s_name");
        final String l_name = idPref.getString(L_NAME, "l_name");
        final String num = idPref.getString(PHONE, "phone");
        final String gender = idPref.getString(GENDER, "gender");
        final String age = idPref.getString(AGE, "age");
        final String status = idPref.getString(STATUS, "status");

        F_name.setText(f_name);
        S_name.setText(s_name);
        L_name.setText(l_name);
        F_name2.setText(f_name);
        S_name2.setText(s_name);
        L_name2.setText(l_name);
        gen.setText(gender);
        personalID.setText(my_id);
        age_.setText(age);
        ph.setText(num);
        if (status.equals("3")) {
            statusImage.setBackgroundColor(R.color.green);
        } else if (status.equals("2")) {
            statusImage.setBackgroundColor(R.color.yellow);
        } else if (status.equals("1")) {
            statusImage.setBackgroundColor(R.color.colorAccent);
        }
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString(USER_ID, "id");

        getActivity();
        personalID.setText(id);
        inputValue = id;
//                inputValue = edtValue.getText().toString().trim();
        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //edtValue.setError(getResources().getString(R.string.value_required));
        }
        return view;
    }

}
