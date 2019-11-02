package com.example.mobileprogramingclass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    String DB_NAME = "toeic.db";

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
    public void onClick(View view){

        EditText edit = (EditText) findViewById(R.id.edittext);
        String eng = edit.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery(
                "SELECT 단어,해석 FROM 토익 WHERE 단어 like '"+ eng + "%';",null);

        TextView text = (TextView)findViewById(R.id.resulttext);
        TextView text2 = (TextView)findViewById(R.id.resulttext2);
        text2.setText(" ");
        if(cursor.getCount() > 0){
            String result = "";
            while(cursor.moveToNext()) {
                String strWord = cursor.getString(0);
                String strMean = cursor.getString(1);
                result += (strWord + ":" + strMean + "\n");
            }
            text.setText(result);
            text.setTextColor(Color.BLUE);
            text.setTextSize(20);
        }
        else text.setText("DB에 없는 단어입니다.");
    }


    public void onRandom(View view){
        Random random = new Random();
        int x = random.nextInt(1101);
        Log.v("클릭",x + "");

        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(
                "SELECT 단어,해석 FROM 토익 WHERE no ='" + x + "';",null);


        TextView text = (TextView)findViewById(R.id.resulttext);
        TextView text2 = (TextView)findViewById(R.id.resulttext2);

        String result = "";
        String result2 = "";
        cursor.moveToNext();
        String strWord = cursor.getString(0);
        String strMean = cursor.getString(1);
        result = strWord;
        result2 = strMean;



        text.setText(result2);
        text.setTextColor(Color.BLUE);
        text.setTextSize(20);

        text2.setText(result);
        text2.setTextColor(Color.RED);
        text2.setTextSize(30);
    }

    public void on7Random(View view){
        Random random = new Random();
        int x = random.nextInt(1101);
        Log.v("클릭",x + "");

        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
// Cursor cursor = db.rawQuery(
// "SELECT 해석,단어 FROM 토익 WHERE no ='" + x + "';",null);
//랜덤
        Cursor cursor = db.rawQuery(
                "SELECT 해석,단어 FROM 토익 WHERE length(단어) >= 7;",null);

        TextView text = (TextView)findViewById(R.id.resulttext);
        TextView text2 = (TextView)findViewById(R.id.resulttext2);
        String result = "";

        int count = cursor.getCount();//7글자 이상인 단어의 개수를 파악한다.
        x = random.nextInt(count);//7글자 이상인 단어들 중에서 랜덤 선택
        cursor.moveToPosition(x);//커서의 위치를 7글자 단어들 중에서만 랜덤으로 이동

// cursor.moveToNext();
        String strWord = cursor.getString(0);
        String strMean = cursor.getString(1);

        cursor.close();

        int length = strMean.length();

        int lengthR = random.nextInt(length);
        int lengthR2 = random.nextInt(length);
        int lengthR3 = random.nextInt(length);

//있으면 3번 없으면 4번 (while문)
        while(true){
            if(Math.abs(lengthR - lengthR2) <= 2 || Math.abs(lengthR - lengthR3) <= 2 || Math.abs(lengthR2 - lengthR3) <= 2){
                lengthR = random.nextInt(length);
                lengthR2 = random.nextInt(length);
                lengthR3 = random.nextInt(length);
            }
            else
                break;
        }
        StringBuilder randomstr = new StringBuilder(strMean);



        int [] a = new int[3];
        a[0] = lengthR;
        a[1] = lengthR2;
        a[2] = lengthR3;

        Arrays.sort(a);

        String _str = "";
        _str += randomstr.charAt(a[0]);
        _str += " ";
        _str += randomstr.charAt(a[1]);
        _str += " ";
        _str += randomstr.charAt(a[2]);


        result += strWord;

        randomstr.setCharAt(lengthR, '_');
        randomstr.setCharAt(lengthR2, '_');
        randomstr.setCharAt(lengthR3, '_');

        text.setText(result);
        text.setTextColor(Color.BLUE);
        text.setTextSize(20);

        text2.setText(randomstr);
        text2.setTextColor(Color.RED);
        text2.setTextSize(30);
        //밑은 7번



        TextView resulttext = (TextView)findViewById(R.id.resulttext3);
        resulttext.setText("정답단어 :" + strMean + "\n" + "빈칸순서대로 :" + _str);

//        ----------------------------------------------8장(7번)
        Button buttons[] = new  Button[12];
        buttons[0] = (Button)findViewById(R.id.bt1); buttons[1] = (Button)findViewById(R.id.bt2); buttons[2] = (Button)findViewById(R.id.bt3); buttons[3] = (Button)findViewById(R.id.bt4);
        buttons[4] = (Button)findViewById(R.id.bt5); buttons[5] = (Button)findViewById(R.id.bt6); buttons[6] = (Button)findViewById(R.id.bt7); buttons[7] = (Button)findViewById(R.id.bt8);
        buttons[8] = (Button)findViewById(R.id.bt9); buttons[9] = (Button)findViewById(R.id.bt10); buttons[10] = (Button)findViewById(R.id.bt11); buttons[11] = (Button)findViewById(R.id.bt12);


        boolean duplicated = false;
        int[] array = new int[26];
        for (int i=0; i<array.length; i++)  {
            do {
                duplicated = false;  // 중복되지 않았다고 가정
                array[i] = random.nextInt(array.length) + 1; // 숫자하나 추출한 후
                for (int j=0; j<i; j++)      // 앞의 숫자들과 비교하여
                    if (array[i]==array[j])  // 같은 것이 존재하면
                        duplicated = true;   // 중복되었다고 체크함
            } while(duplicated);
        }

        char charAlpha = 0;
        for(int i=0; i<buttons.length; i++){

            charAlpha = (char)(array[i]+96);
            buttons[i].setText(""+charAlpha);
        }
    }

 }










