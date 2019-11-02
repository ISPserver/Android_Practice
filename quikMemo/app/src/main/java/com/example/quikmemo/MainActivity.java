package com.example.quikmemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;




public class MainActivity extends AppCompatActivity {
    String DB_NAME = "toeic.db";
    static String[] words;
    static String[] sum_alpha;

    private void copyDatabase(File dbFile){
        try {
            String folderPath = "/data/data" + getPackageName() + "/databases";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();
            InputStream is = getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) os.write(buffer);
            os.flush();
            is.close();
            os.close();
        }
        catch (Exception e)
        {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File dbFile = getDatabasePath(DB_NAME);
        if(!dbFile.exists()) copyDatabase(dbFile);
    }

    public void on7Random(final View view){
        RadioGroup rsad = findViewById(R.id.radiogroup);
        rsad.removeAllViews();
        Random random = new Random();
        int x = random.nextInt(1101);
        int y = random.nextInt(9 - 1 + 1) + 1;


        Log.v("클릭",x + "");

        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(
                "SELECT 단어,해석 FROM 토익 WHERE no ='" + x + "';",null);


        TextView resulttext = (TextView)findViewById(R.id.textView);
        TextView resulttext2 = (TextView)findViewById(R.id.textView2);

        cursor.moveToNext();
        final String strWord = cursor.getString(0);
        final String strMean = cursor.getString(1);

        sum_alpha = new String[y+1];
        sum_alpha[0] = strWord;

        for(int i=0; i<y; i++){
            int g = random.nextInt(1101);
            Log.v("클릭",g + "");
            Cursor cursor2 = db.rawQuery(
                    "SELECT 단어,해석 FROM 토익 WHERE no ='" + g + "';",null);
            cursor2.moveToNext();
            String strWord_multi = cursor2.getString(0);
            words = new String[y];
            words[i] = strWord_multi;
            sum_alpha[i+1] = words[i];
        }

        Arrays.sort(sum_alpha);

        resulttext.setText(strMean);
        resulttext.setTextSize(30);

        resulttext2.setText("확인용정답 :" + strWord);
        resulttext.setTextSize(20);


        for(int i=0;i<sum_alpha.length;i++) {
            RadioGroup radiogroup = (RadioGroup)findViewById(R.id.radiogroup);

            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(i);
            rdbtn.setText(sum_alpha[i]);
            radiogroup.addView(rdbtn);
            if(sum_alpha[i] == strWord)  //정답인건 레드컬러
            { rdbtn.setTextColor(Color.RED); }
            else{   //정답 아닌건 블루컬러
                rdbtn.setTextColor(Color.BLUE); }
        }
        RadioGroup radiogroup2 = (RadioGroup)findViewById(R.id.radiogroup);
        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, int id) {
                for(int k=0; k<sum_alpha.length; k++){
                    RadioButton select = (RadioButton)findViewById(id);
                    final String answer = select.getText().toString();
                    select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(answer.equals(strWord)){
                                Toast.makeText(MainActivity.this, "축하합니다!",Toast.LENGTH_LONG).show();
                                radioGroup.removeAllViews();
                                on7Random(view);
                            }
                            else if(!answer.equals(strWord)) {
                                Toast.makeText(MainActivity.this, "오답입니다.다시 선택하세요!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }


            }
        });




    }


}









