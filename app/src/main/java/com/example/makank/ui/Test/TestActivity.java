package com.example.makank.ui.Test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makank.Alert;
import com.example.makank.R;
import com.example.makank.SharedPref;
import com.example.makank.ui.activity.SendNotifActivity;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView txtQuestion,txtCount,txtFinal,txtCheck,txtCheck1;
    RadioButton rda, rdb, rdc, rdd;
    Button butNext;
    Alert alert;
    Typeface typeface;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        DbHelper db = new DbHelper(this);
        quesList = db.getAllQuestions();
        currentQ = quesList.get(qid);
        txtQuestion = (TextView) findViewById(R.id.textView1);
        rda = (RadioButton) findViewById(R.id.radio0);
        rdb = (RadioButton) findViewById(R.id.radio1);
        rdc = (RadioButton) findViewById(R.id.radio2);
        rdd = (RadioButton) findViewById(R.id.radio3);
        butNext = (Button) findViewById(R.id.button1);
        txtCount=findViewById(R.id.count_id);
        txtFinal=findViewById(R.id.final_id);
        txtCheck=findViewById(R.id.check_id);
        txtCheck1=findViewById(R.id.check_id1);
        txtFinal.setVisibility(View.INVISIBLE);
        txtCount.setText(getResources().getString(R.string.step_1_11));
        setQuestionView();
        alert=new Alert(this);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen-Algeria.ttf");
        txtCount.setTypeface(typeface);
        txtQuestion.setTypeface(typeface);
        txtFinal.setTypeface(typeface);
        rda.setTypeface(typeface);
        rdb.setTypeface(typeface);
        rdc.setTypeface(typeface);
        rdd.setTypeface(typeface);
        txtCheck.setTypeface(typeface);
        txtCheck1.setTypeface(typeface);
        butNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);
                if (rda.isChecked() || rdb.isChecked() || rdc.isChecked() || rdd.isChecked()) {
                    RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
                    grp.clearCheck();
                    // if (grp.isClickable()) {
                    Log.d("yourans", currentQ.getANSWER() + " " + answer.getText());
                    if (currentQ.getANSWER().equals(answer.getText())) {
                        score++;
                        Log.d("score", "Your score" + score);
                    }
                    if (qid < 11) {
                        currentQ = quesList.get(qid);
                        setQuestionView();
                        txtCount.setText(" "+getResources().getString(R.string.step)+""+qid+getResources().getString(R.string.step_11));
                        defColor();
                        //Toast.makeText(TestActivity.this, ""+qid, Toast.LENGTH_SHORT).show();
                        //grp.clearCheck();
                    } else {
                        result(score);
                    }
                    if (qid>=7) txtFinal.setVisibility(View.VISIBLE);
                } else {
                    SpannableString efr = new SpannableString(getResources().getString(R.string.must_question_all));
                    efr.setSpan(new TypefaceSpan(typeface),0,efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //Toast.makeText(getApplicationContext(), efr, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(TestActivity.this, efr, Toast.LENGTH_SHORT).show();

                    Toast toast=Toast.makeText(TestActivity.this,efr,Toast.LENGTH_SHORT);
                    View view=toast.getView();
                    view.setBackgroundColor(Color.TRANSPARENT);
                    TextView text=(TextView) view.findViewById(android.R.id.message);
                    text.setShadowLayer(0,0,0,Color.TRANSPARENT);
                    text.setTextColor(Color.RED);
                    text.setTextSize(Integer.valueOf(20));
                    toast.show();

                }
            }
        });
    }
    private void setQuestionView() {
        txtQuestion.setText(currentQ.getQUESTION());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        if (currentQ.getOPTC().equals(""))
            rdc.setVisibility(View.INVISIBLE);
        else
            rdc.setVisibility(View.VISIBLE);
        rdc.setText(currentQ.getOPTC());

        if (currentQ.getOPTD().equals(""))
            rdd.setVisibility(View.INVISIBLE);

        else
            rdd.setVisibility(View.VISIBLE);
        rdd.setText(currentQ.getOPTD());

        qid++;
    }
    public void result(int result)
    {
        if (score== 0 || score <=3)
        {
           alert.showSuccessDialog(getResources().getString(R.string.result_check),getResources().getString(R.string.do_not_worry),1);
        }else if(score==4 || score <=6){
            alert.showSuccessDialog(getResources().getString(R.string.result_check),getResources().getString(R.string.catch_cold),1);
        }
        else
        {
            alert.showSuccessDialog(getResources().getString(R.string.result_check),getResources().getString(R.string.effected),2);

        }
    }

    @SuppressLint("ResourceAsColor")
    public void onRadioButtonClick(View view) {
        if (rda.isChecked())
        {
           rda.setTextColor(Color.parseColor("#C7212D"));
        }
        else
        {
            rda.setTextColor(Color.parseColor("#5a5a5a"));
        }
        if (rdb.isChecked())
        {
            rdb.setTextColor(Color.parseColor("#C7212D"));
        }
        else
        {
            rdb.setTextColor(Color.parseColor("#5a5a5a"));
        }
        if (rdc.isChecked())
        {
            rdc.setTextColor(Color.parseColor("#C7212D"));
        }
        else
        {
            rdc.setTextColor(Color.parseColor("#5a5a5a"));
        }
        if (rdd.isChecked())
        {
            rdd.setTextColor(Color.parseColor("#C7212D"));
        }
        else
        {
            rdd.setTextColor(Color.parseColor("#5a5a5a"));
        }
    }
    public void defColor()
    {
        rda.setTextColor(Color.parseColor("#5a5a5a"));
        rdb.setTextColor(Color.parseColor("#5a5a5a"));
        rdc.setTextColor(Color.parseColor("#5a5a5a"));
        rdd.setTextColor(Color.parseColor("#5a5a5a"));
    }
}




