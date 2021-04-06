package org.techtown.catpracticeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    EditText editNumber; //고양이 체중 입력창
    double RER;
    double States;
    double DER;
    EditText Cal;
    double OneDayCalories;
    EditText result;
    String resul;

    String[] items = {"선택", "운동량이 많은 성묘", "중성화 받지 않은 성묘", "적정 체중의 성묘"
            , "비만묘", "4개월 미만", "4-6개월", "7-12개월"};

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Spinner spinner = findViewById(R.id.spinner);

        //스피너 어댑터및 스피너 배열목록 선택시 반응--------------------------------------------------
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //아이템선택시 반응정의 메소드
                onCatStates(items[position]);
                if (States != 0.0) {
                    DER = States * RER;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //아무것도 선택안했을시 메소드
            }
        });
        //------------------------------------------------------------------------------------------


        editNumber = findViewById(R.id.editNumber);
        //체중받아서 고양이 기초대사량 구하기----------------------------------------------------------
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kg = editNumber.getText().toString();
                double kg1 = Double.parseDouble(kg);
                RER = (30 * kg1) + 70;
            }
        });

        Cal = findViewById(R.id.Cal);
        result = findViewById(R.id.result);//오류원인은 최종값을 받는 텍스트를 인플레이션을 안해둬서
        //강제종료가 났던것이다.
        //칼로리 받아서 하루사료량(g) 구하기----------------------------------------------------------
        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kcal = Cal.getText().toString();
                double KCAL = Double.parseDouble(kcal);
                OneDayCalories = (DER * 1000.0) / KCAL;
                int react = (int) OneDayCalories;
                resul = String.valueOf(react); //int타입으로 변환된게 잘 스트링타입으로 변환되서 잘 전달된다.
                result.setText(resul);

            }
        });
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    public double onCatStates(String items) {
        if ("운동량이 많은 성묘".equals(items)) {
            return States = 1.6;
        } else if ("중성화 받지 않은 성묘".equals(items)) {
            return States = 1.4;
        } else if ("적정 체중의 성묘".equals(items)) {
            return States = 1.0;
        } else if ("비만묘".equals(items)) {
            return States = 0.8;
        } else if ("4개월 미만".equals(items)) {
            return States = 3.0;
        } else if ("4-6개월".equals(items)) {
            return States = 2.5;
        } else if ("7-12개월".equals(items)) {
            return States = 2.0;
        }

        return States;
    }

}