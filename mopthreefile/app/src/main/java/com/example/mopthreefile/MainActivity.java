package com.example.mopthreefile;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity

        implements TextToSpeech.OnInitListener {
    TextToSpeech tts;
    EditText editText;
    static int count;
    static int count2;
    static Calendar time;
    String result2;
    int num;
    String format_time1;
    SimpleDateFormat format1;
    static Date dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        tts = new TextToSpeech(this, this);


    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale locale = Locale.getDefault();
            if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE)
                tts.setLanguage(locale);
            else
                Toast.makeText(this,
                        "지원하지 않는 언어 오류", Toast.LENGTH_LONG).show();
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this,
                    "음성 합성 초기화 오류", Toast.LENGTH_LONG).show();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) tts.shutdown();
    }
    public void onClick(View v){
        String str=editText.getText().toString();
        if(str.length() >0) {
            //이미 말하고 있다면, 기존 음성 합성을 정지 시킨다.
            if(tts.isSpeaking()) tts.stop();
            //음성합성 시작
            tts.setSpeechRate(1.0f);
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    public void onClick2(View v) {

//        SimpleDateFormat format1 = new SimpleDateFormat ( "HH:mm");

        Calendar time2 = Calendar.getInstance();
        int hour = time2.get(Calendar.HOUR);
        int minute = time2.get(Calendar.MINUTE);
        String hour_str = Integer.toString(hour);
        String minute_str = Integer.toString(minute);
        String result = hour_str+":"+minute_str;

//        String format_time1 = format1.format(time.getTime());

        EditText _edit = (EditText)findViewById(R.id.editText);
        _edit.setText(result, TextView.BufferType.EDITABLE);
        if (hour > 0) {
            if (tts.isSpeaking()) tts.stop();
            tts.setSpeechRate(1.0f);
            tts.speak(result, TextToSpeech.QUEUE_FLUSH, null);

        }
    }
    public void onClick3(View v) {

        format1 = new SimpleDateFormat ( "yyyy/MM/dd EE요일");

        time = Calendar.getInstance();
        num = time.get(Calendar.DATE)-1;      //현재요일

        dates = time.getTime();
        format_time1 = format1.format(dates);      //현재날짜

        result2 = format_time1;


        EditText _edit = (EditText)findViewById(R.id.editText);
        _edit.setText(result2, TextView.BufferType.EDITABLE);
        if (num > 0) {
            if (tts.isSpeaking()) tts.stop();
            tts.setSpeechRate(1.0f);
            tts.speak(result2, TextToSpeech.QUEUE_FLUSH, null);

        }
    }
    public void onClick4(View v) {

        time.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
        String d = format1.format(time.getTime()); // String으로 저장


        EditText _edit = (EditText)findViewById(R.id.editText);
        _edit.setText(d, TextView.BufferType.EDITABLE);
        if (num > 0) {
            if (tts.isSpeaking()) tts.stop();
            tts.setSpeechRate(1.0f);
            tts.speak(result2, TextToSpeech.QUEUE_FLUSH, null);

        }
    }
    public void onClick5(View v) {

        time.add(Calendar.DATE, +1); // 오늘날짜로부터 -1
        String d = format1.format(time.getTime()); // String으로 저장


        EditText _edit = (EditText)findViewById(R.id.editText);
        _edit.setText(d, TextView.BufferType.EDITABLE);
        if (num > 0) {
            if (tts.isSpeaking()) tts.stop();
            tts.setSpeechRate(1.0f);
            tts.speak(result2, TextToSpeech.QUEUE_FLUSH, null);

        }
    }



}


