package com.example.mopfourfile;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import android.speech.tts.TextToSpeech;
public class MainActivity extends AppCompatActivity
        implements TextToSpeech.OnInitListener {

    EditText editText;
    TextToSpeech tts;
    static float values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);
        editText = (EditText)findViewById(R.id.editText);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);

        seekBar.setMax(5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView textView = (TextView)findViewById(R.id.textView);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float val = (float) progress * .5f;

                values =val+0.5f;


                textView.setText("말하기 속도= "+values+"배속");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    public void onClick(View v){
        try{
            //음성 인식 인텐트 실행
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, 0);

        }catch (Exception e){
            Toast.makeText(this,
                    "구글 앱이 설치되지 않았습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void onClick_Count(View v){
        try{
            //음성 인식 인텐트 실행
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, 0);

        }catch (Exception e){
            Toast.makeText(this,
                    "구글 앱이 설치되지 않았습니다.", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode ==0 && resultCode == RESULT_OK){
            ArrayList <String> results =
                    data.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS);
            String str = results.get(0);
            String[] str_split = results.get(0).split(" \\+ ");
            if(str.contains("+")){
                int sum = Integer.parseInt(str_split[0])+Integer.parseInt(str_split[1]);
                String str2 = str + " = "+sum;
                String str3 = "정답은"+sum+"입니다";
                editText.setText(str2);
                if(str.length() >0) {
                    //이미 말하고 있다면, 기존 음성 합성을 정지 시킨다.
                    if(tts.isSpeaking()) tts.stop();
                    //음성합성 시작
                    tts.setSpeechRate(values);

                    tts.speak(str3, TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            else {
                editText.setText(str);
                if (str.length() > 0) {
                    //이미 말하고 있다면, 기존 음성 합성을 정지 시킨다.
                    if (tts.isSpeaking()) tts.stop();
                    //음성합성 시작
                    tts.setSpeechRate(values);

                    tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
