package com.example.nfcexample;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements TextToSpeech.OnInitListener {
    TextView tv_money;
    TextToSpeech tts;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts =new TextToSpeech(this,this);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this, "NFC지원불가>종료", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(nfcAdapter.isEnabled() == false)
            Toast.makeText(this,"NFC읽기/쓰기 모드 설정하세요",
                    Toast.LENGTH_LONG).show();
        else if(getIntent().getAction().equals(NfcAdapter.ACTION_TECH_DISCOVERED))
            checkCard(getIntent());
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

    void checkCard(Intent in){
        TextView tv_number = (TextView)findViewById(R.id.tv_number);
        tv_money = (TextView)findViewById(R.id.tv_money);
        try{
            Tag t = in.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            IsoDep id = IsoDep.get(t);
            byte[] number_code = {0x00, (byte)0xA4, 0x00, 0x00, 0x02, 0x42, 0x00};
            byte[] money_code = {(byte)0x90,0x4C,0x00, 0x00, 0x04};
            id.connect();
            byte[] number = id.transceive(number_code);
            byte[] money = id.transceive(money_code);
            id.close();
            tv_number.setText( calcNumber( number ));
            tv_money.setText( values( money) );
            onClick(v);
        }catch (Exception e){
            tv_number.setText("에러 발생:"+e.toString());
        }
    }
    String calcNumber(byte[] data)
    {
        String number = "";
        String hex = "0123456789ABCDEF";

        for(int i=8; i<=15; i++){
            int n = (data[i] < 0)? data[i]+256 : data[i];
            number += hex.charAt(n / 16); //앞자리
            number += hex.charAt(n % 16); //뒷자리
            if( i%2 != 0 && i <15) number += "-";
        }
        //for문장을 실행하고 나면 number 변수에 카드번호 문자열이 저장된다.
        return number; // 카드번호를 1040-0099-1496-0077 형식으로 완성하여 리턴
    }
    String values(byte[] data)
    {
        String number = "";
        int number_num = 0;
        int val = 6;
        for(int i=0; i<=3; i++){
            int n = (data[i] < 0)? data[i]+256 : data[i];
            number_num += n*(Math.pow(16,val));
            val -= 2;
        }
        number = number_num +"원";
        return number;
    }
    public void onClick(View v){
        String str=tv_money.getText().toString();
        if(str.length() >0) {
            //이미 말하고 있다면, 기존 음성 합성을 정지 시킨다.
            if(tts.isSpeaking()) tts.stop();
            //음성합성 시작
            tts.setSpeechRate(1.0f);
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
