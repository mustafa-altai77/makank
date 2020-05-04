package com.example.makank.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

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
    TextView F_name,gen,age_,ph ,personalID,TxtPhone,TxtAge,TxtGender;
    ImageView qrImage ;
    CircleImageView statusImage;

    String inputValue;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    Typeface typeface;
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
        gen = view.findViewById(R.id.gender_pref);
        age_ = view.findViewById(R.id.age_prf);
        ph = view.findViewById(R.id.phone);
        qrImage = view.findViewById(R.id.qr_person);
        statusImage=  view.findViewById(R.id.img_status);
        personalID = view.findViewById(R.id.personal_id);

        TxtPhone = view.findViewById(R.id.txtPhone);
        TxtAge = view.findViewById(R.id.txtAge);
        TxtGender = view.findViewById(R.id.txtGender);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Hacen-Algeria.ttf");
        F_name.setTypeface(typeface);
        gen.setTypeface(typeface);
        age_.setTypeface(typeface);
        ph.setTypeface(typeface);
        personalID.setTypeface(typeface);

        TxtPhone.setTypeface(typeface);
        TxtAge.setTypeface(typeface);
        TxtGender.setTypeface(typeface);

        SharedPreferences idPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        final String my_id = idPref.getString(USER_ID, "id");
        final String f_name = idPref.getString(F_NAME, "f_name");
        final String s_name = idPref.getString(S_NAME, "s_name");
        final String l_name = idPref.getString(L_NAME, "l_name");
        final String num = idPref.getString(PHONE, "phone");
        final String gender = idPref.getString(GENDER, "gender");
        final String age = idPref.getString(AGE, "age");
        final String status = idPref.getString(STATUS, "status");

        F_name.setText("الإسم ثلاثي : "+f_name+" "+s_name+" "+l_name);
        gen.setText(gender);
        personalID.setText("الرقم التعريفي : "+ my_id);
        age_.setText(status);
        ph.setText(num);
        Toast.makeText(getActivity(), ""+age, Toast.LENGTH_SHORT).show();

        if (status.equals("1")) {
            statusImage.setBackground(ContextCompat.getDrawable(this.getActivity(),R.drawable.red));

        } else if (status.equals("2")) {
            statusImage.setBackground(ContextCompat.getDrawable(this.getActivity(),R.drawable.yellowc));

        } else if (status.equals("3")) {
            statusImage.setBackground(ContextCompat.getDrawable(this.getActivity(),R.drawable.greenc));
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
