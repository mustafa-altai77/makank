package com.example.makank.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.LoadingDialog;
import com.example.makank.R;
import com.example.makank.data.model.Disease;
import com.example.makank.data.model.Person;
import com.example.makank.data.network.ApiClient;
import com.example.makank.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WINDOW_SERVICE;
import static com.example.makank.SharedPref.SHARED_PREF_NAME;
import static com.example.makank.SharedPref.USER_ID;
import static com.example.makank.SharedPref.mCtx;


public class PersonalFragment extends Fragment {
    TextView F_name, gen, age_, ph, personalID, TxtPhone, TxtAge, TxtGender,statusName,disease_list;
    ImageView qrImage;
    CircleImageView statusImage;
    ListView listView;
    LoadingDialog loadingDialog;
    Alert alert;
    private List<Disease> diseases;

    String inputValue;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    Typeface typeface;
    private Person personList;
    ArrayList<String> disease_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new Alert(getActivity());
        loadingDialog = new LoadingDialog(getActivity());
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        listView = view.findViewById(R.id.disease_list);
        F_name = view.findViewById(R.id.f_na);
        gen = view.findViewById(R.id.gender_pref);
        age_ = view.findViewById(R.id.age_prf);
        ph = view.findViewById(R.id.phone);
        qrImage = view.findViewById(R.id.qr_person);
        statusImage = view.findViewById(R.id.img_status);
        personalID = view.findViewById(R.id.personal_id);
        TxtPhone = view.findViewById(R.id.txtPhone);
        TxtAge = view.findViewById(R.id.txtAge);
        TxtGender = view.findViewById(R.id.txtGender);
        statusName = view.findViewById(R.id.stautsName);
        disease_list = view.findViewById(R.id.disease_listt);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Hacen-Algeria.ttf");
        F_name.setTypeface(typeface);
        gen.setTypeface(typeface);
        age_.setTypeface(typeface);
        ph.setTypeface(typeface);
        personalID.setTypeface(typeface);
        TxtPhone.setTypeface(typeface);
        TxtAge.setTypeface(typeface);
        TxtGender.setTypeface(typeface);
        statusName.setTypeface(typeface);
        disease_list.setTypeface(typeface);
        disease_name = new ArrayList<>();

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String my_id = sharedPreferences.getString(USER_ID, "id");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<Person> call = apiService.getMyData(my_id);
        loadingDialog.startLoadingDialog();
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    loadingDialog.dismissDialog();
                    personList =  response.body();
//                        final String my_id = personList.getLocal_id();
                        final String f_name = personList.getFirst_name();
                        final String s_name = personList.getSecond_name();
                        final String l_name = personList.getLast_name();
                        final String num = personList.getPhone();
                        final String gender = personList.getGender();
                        final String age = personList.getAge();
                        final String status = personList.getStatus();
                        F_name.setText("" + f_name + " " + s_name + " " + l_name);
                        gen.setText(gender);
                        personalID.setText("الرقم التعريفي : " + my_id);
                        age_.setText(age);
                        ph.setText(num);

                        if (status.equals("1")) {
                            statusImage.setImageResource(R.color.colorAccent);
                            statusName.setText("مصاب");

                        } else if (status.equals("2")) {
                            statusImage.setImageResource(R.color.yellow);
                            statusName.setText("مخالط");

                        } else if (status.equals("3")) {
                            statusImage.setImageResource(R.color.green);
                            statusName.setText("سليم");
                        }
                    }

                }


            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                loadingDialog.dismissDialog();

                alert.showAlertError("تــأكد من إتصالك بإنترنت");
                //Toast.makeText(getContext(), "خطاء في النظام الخارجي" + t, Toast.LENGTH_SHORT).show();
            }
        });

//        private void fetchCity(){
//            loadingDialog.startLoadingDialog();

        final String id = sharedPreferences.getString(USER_ID, "id");
           try {


            Call<List<Disease>> call2 = apiService.getMydisease(id);
            call2.enqueue(new Callback<List<Disease>>() {
                @Override
                public void onResponse(Call<List<Disease>> call, Response<List<Disease>> response) {

                    loadingDialog.dismissDialog();
//                if (!response.isSuccessful()) {

                    diseases = response.body();
                    for (int i =0;i<diseases.size();i++) {
                         disease_name.add(diseases.get(i).getName());
                        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(
                                getContext(), android.R.layout.simple_list_item_1, disease_name
                        );
                        listView.setAdapter(itemsAdapter );
                    }


                }
                @Override
                public void onFailure(Call<List<Disease>>call, Throwable t) {
                    loadingDialog.dismissDialog();

                    Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();

                }
            });
           } catch (Exception e) {
               e.printStackTrace();
           }


        //final String id = sharedPreferences.getString(USER_ID, "id");

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

