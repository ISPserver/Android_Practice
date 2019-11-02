package com.example.mopsecondfile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;



public class MainActivity extends AppCompatActivity {

    String DB_NAME = "toeic.db";
    EditText lengthEdit;
    static  char[] Answerword;
    static Cursor cursor;
    static int[] aa;
    static char charAlpha = 0;
    static int o;
    static int num;
    static int num2;
    static int num3;
    static int[] array2;
    static int lengthR; static int lengthR2; static int lengthR3;
    static int length;
    static int[] a;
    static StringBuilder randomstr;
    static String strMean;
    static char first; static char second; static char three;
    static String p; static String p2; static String p3;
    static TextView text2; static char[] values;
    int score = 0;
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
        text2 = (TextView)findViewById(R.id.resulttext2);
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

    public void on7Random(final View view){
        final Random random = new Random();
        int x = random.nextInt(1101);
        Log.v("클릭",x + "");
        lengthEdit = (EditText)findViewById(R.id.editText2);
        String editString = lengthEdit.getText().toString();
        int editInt = Integer.parseInt(editString);

        EditText selAlpha = (EditText)findViewById(R.id.editText3);
        String selAlpha2 = selAlpha.getText().toString();
        char selAlpha3 = selAlpha2.charAt(0);


        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
// Cursor cursor = db.rawQuery(
// "SELECT 해석,단어 FROM 토익 WHERE no ='" + x + "';",null);
//랜덤

        cursor = db.rawQuery(
                "SELECT 해석,단어 FROM 토익 WHERE length(단어)= " +editInt+ " AND 단어 LIKE '%" +selAlpha3+ "%';",null);

//        length(단어) ="+ editInt +";",null);  이건 13번


        TextView text = (TextView)findViewById(R.id.resulttext);
        final TextView text2 = (TextView)findViewById(R.id.resulttext2);
        String result = "";

        int count = cursor.getCount();//7글자 이상인 단어의 개수를 파악한다.
        x = random.nextInt(count);//7글자 이상인 단어들 중에서 랜덤 선택
        cursor.moveToPosition(x);//커서의 위치를 7글자 단어들 중에서만 랜덤으로 이동

// cursor.moveToNext();
        String strWord = cursor.getString(0);
        strMean = cursor.getString(1);

        EditText alphanumber = (EditText)findViewById(R.id.editText4);
        String alphanum2 = alphanumber.getText().toString();
        int alpha3 = Integer.parseInt(alphanum2);
        int count_num =0;
        for(int u=0; u<strMean.length(); u++){
            if(strMean.charAt(u) == selAlpha3) count_num++;
        }
        if(!(count_num == alpha3)){
            on7Random(view);
        }
        cursor.close();




        length = strMean.length();

        lengthR = random.nextInt(length);
        lengthR2 = random.nextInt(length);
        lengthR3 = random.nextInt(length);

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
        randomstr = new StringBuilder(strMean);



        a = new int[3];
        a[0] = lengthR;
        a[1] = lengthR2;
        a[2] = lengthR3;

        aa = new int[3];
        aa[0] = lengthR+1;
        aa[1] = lengthR+1;
        aa[2] = lengthR+1;

        Arrays.sort(a);
        Arrays.sort(aa);






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
        final Button buttons[] = new  Button[12];
        buttons[0] = (Button)findViewById(R.id.bt1); buttons[1] = (Button)findViewById(R.id.bt2); buttons[2] = (Button)findViewById(R.id.bt3); buttons[3] = (Button)findViewById(R.id.bt4);
        buttons[4] = (Button)findViewById(R.id.bt5); buttons[5] = (Button)findViewById(R.id.bt6); buttons[6] = (Button)findViewById(R.id.bt7); buttons[7] = (Button)findViewById(R.id.bt8);
        buttons[8] = (Button)findViewById(R.id.bt9); buttons[9] = (Button)findViewById(R.id.bt10); buttons[10] = (Button)findViewById(R.id.bt11); buttons[11] = (Button)findViewById(R.id.bt12);

        String[] aaa = _str.split(" ");
        first = aaa[0].charAt(0);
        second = aaa[1].charAt(0);
        three = aaa[2].charAt(0);

        Answerword = new char[3];
        Answerword[0] = first;
        Answerword[1] = second;
        Answerword[2] = three;




        num = (int)first-96;
        num2 = (int)second-96;
        num3 = (int)three-96;


        boolean duplicated = false;
        int[] array = new int[26];
        array[0] = num; array[1] = num2; array[2] = num3;

        for (int i=3; i<array.length; i++)  {   //빈칸값 중복시 한개만 적용
            if(array[0] == array[1]){
                array[1] = array[2];
                i--; }
            else if(array[0] == array[2]){
                i--; }
            else if(array[1] == array[2]){
                i--; }
            do {
                duplicated = false;  // 중복되지 않았다고 가정
                array[i] = random.nextInt(array.length) + 1; // 숫자하나 추출한 후
                for (int j=0; j<i; j++)      // 앞의 숫자들과 비교하여
                    if (array[i]==array[j])  // 같은 것이 존재하면
                        duplicated = true;   // 중복되었다고 체크함
            } while(duplicated);
        }


        array2 = new int[12];     //array 배열 복사(길이12까지만)
        for(int i=0; i<12; i++){
            array2[i] = array[i];
        }

        Arrays.sort(array2);            //array[0].[1].[2]에는 빈칸값이 있기때문에 정렬.



        for(int i=0; i<12; i++){
            charAlpha = (char)(array2[i]+96);
            buttons[i].setText(""+charAlpha);
            if(array2[i] == num || array2[i] == num2 || array2[i] == num3)  //정답인건 레드컬러
            { buttons[i].setTextColor(Color.RED); }
            else{   //정답 아닌건 블루컬러
                 buttons[i].setTextColor(Color.BLUE); }
        }

            /*for(z=0; z<12; z++){      //버튼값 Toast메시지 뿌리기
                final int finalZ = z;
                buttons[z].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, buttons[finalZ].getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }*/

    }
    public void btClick(View view){
        Button vBtn = (Button)view;

        int x;
        int j = 0;

        //바꾸어야할 곳의 위치를 저장
        StringBuilder tempsb = new StringBuilder(randomstr);
        int length = tempsb.length();
        int[] _count = new int[3];

        for(x = 0; x < length; x++){
            if(tempsb.charAt(x) == '_'){
                _count[j] = x;
                j++;
            }
        }
        //바꿔지면 바뀌는 값으로 바꿔지게
        TextView mixtext = (TextView)findViewById(R.id.resulttext2);
        StringBuilder mixsb = new StringBuilder(mixtext.getText().toString());

        //바꿔야할 위치를 _count[]에 넣고,
        //바꿔야할것들은 -> Answerword
        //만약 버튼을 누르면 그 위치에 alpha[i]가 들어가게.

        //밑에서 비교하기위해 character형으로 해야함.
        //CharSequence (연속되는문자?)
        //vbtnText는 한글자 짜리임. charAt(0) 만 가능.

        CharSequence vbtnText = vBtn.getText();
        TextView scoreText = (TextView)findViewById(R.id.textView2);
        Character [] tempAnswerword = new Character[3];
        tempAnswerword[0] = Answerword[0];
        tempAnswerword[1] = Answerword[1];
        tempAnswerword[2] = Answerword[2];


        for(x = 0; x < 3; x++){

            if(tempAnswerword[x] == vbtnText.charAt(0) ) {
                 /*
                  example
                         _re_ce_t
                         crescent
                         count0에 0 3 6
                         temp 0에 c s n
                         이때 버튼 클릭한건 n이면
                  둘이 맞는 것을 찾으면 i=2일때 둘이 같아진다.
                  count[2] -> 6  temp[2] -> n
                */
                if(mixsb.charAt(_count[x])=='_') {
                    score += 100;
                }
                else{
                    if(Answerword[0] == Answerword[1]) {
                        if(Answerword[2]==vbtnText.charAt(0)){
                            score -= 50;
                        }
                        else {
                            score -= 25;
                        }
                    }
                    else if(Answerword[0] == Answerword[2]) {
                        if(Answerword[1]==vbtnText.charAt(0)){
                            score -= 50;
                        }
                        else {
                            score -= 25;
                        }
                    }
                    else if(Answerword[1] == Answerword[2]) {
                        if(Answerword[0]==vbtnText.charAt(0)){
                            score -= 50;
                        }
                        else {
                            score -= 25;
                        }
                    }
                    else score -= 50;
                }
                mixsb.setCharAt(_count[x], Answerword[x]);


                mixtext.setText(mixsb);
                Toast.makeText(this, vbtnText, Toast.LENGTH_SHORT).show();
            }
            scoreText.setText(Integer.toString(score));
        }

        if(vBtn.getTextColors().getDefaultColor() == Color.BLUE){
            score-= 50;
            scoreText.setText(Integer.toString(score));

        }

        Boolean bool = true;
        //클릭하면 클릭한 알파벳 토스트로 뜨게함
        Toast.makeText(this, vbtnText, Toast.LENGTH_SHORT).show();

        for(x = 0; x < mixsb.length(); x++){
            if(mixsb.charAt(x) != '_'){
            }
            else bool = false;
        }

        if(bool == true){
            Toast.makeText(getApplicationContext(), "축하합니다", Toast.LENGTH_SHORT).show();
            on7Random(view);
        }
    }

}









