package com.example.parser2;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    TextView tv;
    String strHtml ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=(TextView)this.findViewById(R.id.textView2);
        tv.setText("검색중...");
        Handler h = new Handler(){
            public void handleMessage(Message msg){
                HTMLParsing();
            }
        };
        new WorkerThread(h).start();
    }
    class WorkerThread extends Thread{
        Handler h; String strLine;
        WorkerThread(Handler h) {this.h = h;}
        public void run(){
            try{
                //www.naver.com: 모바일 페이지로 자동 접속되어 실시간 검색어가 없다!
                //모바일 환경에서 실시간 검색어가 나타나는 pc버전을 사용해야 한다!
                URL aURL = new URL("https://www.naver.com/?mobile");
                BufferedReader in = new BufferedReader(new InputStreamReader(aURL.openStream() ));
                while((strLine = in.readLine()) != null)
                    if(strLine.contains("ah_k"))
                        strHtml += strLine;
                    in.close();
                    h.sendMessage(new Message());
            }catch (Exception e){
                tv.setText("네트워크 에러: "+ e.toString() );
            }
        }
    }
    void HTMLParsing(){
        try {
            String strContent = "";
            int start = 0, end = 0;
            for(int i=1; i<=10; i++){
                start = strHtml.indexOf("ah_k", end);
                end = strHtml.indexOf("</span>",start);
                strContent += "실시간검색어"+i+"위:"+strHtml.substring(start+6,end)+"\n";
            }
            tv.setText(strContent);
        }catch (Exception e){
            tv.setText("파싱 에러: "+e.toString());
        }
    }
}
//
//public class MainActivity extends AppCompatActivity {
//    TextView tv;
//    String strHtml ="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tv=(TextView)this.findViewById(R.id.textView2);
//        new WorkerThread().start();
//    }
//    public void onClick(View v){
//        Button bt1= (Button)findViewById(R.id.button1); Button bt2= (Button)findViewById(R.id.button2);
//        Button bt3= (Button)findViewById(R.id.button3); Button bt4= (Button)findViewById(R.id.button4);
//        Button bt5= (Button)findViewById(R.id.button5);
//        Button vBtn = (Button)v;
//        String vBtnText = vBtn.getText().toString();
//
//        HTMLParsing(vBtnText);
//    }
//
//    class WorkerThread extends Thread{
//        String strLine;
//        public void run(){
//            try{
//                URL url = new URL("http://www.halla.ac.kr/mbs/kr/jsp/restaurant/restaurant.jsp?configIdx=23342&id=kr_050301000000");
//                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream() ));
//                while((strLine = in.readLine()) != null)
//                    strHtml += strLine;
//                in.close();
//            } catch (Exception e){
//                tv.setText("네트워크 오류");
//            }
//        }
//    }
//    public void HTMLParsing(String vBtnText){
//        String strMenu = vBtnText+"요일 식단표\n\n";
//        try {
//            int Start = strHtml.indexOf(vBtnText+"요일 식단표");
//            int BreakfastStart = strHtml.indexOf("-&nbsp;", Start);
//            int BreakfastEnd = strHtml.indexOf("</p>", BreakfastStart);
//            String BreakfastFood = strHtml.substring(BreakfastStart + 7, BreakfastEnd);
//            strMenu += "조식 : " + BreakfastFood + "\n\n";
//
//            int LunchStandardStart = strHtml.indexOf("-&nbsp;", BreakfastEnd);
//            int LunchStandardEnd = strHtml.indexOf("</p>", LunchStandardStart);
//            String LunchStandardFood = strHtml.substring(
//                    LunchStandardStart + 7, LunchStandardEnd);
//            strMenu += "중식(일반식): " + LunchStandardFood + "\n\n";
//
//            int LunchSpecialStart = strHtml.indexOf("-&nbsp;", LunchStandardEnd);
//            int LunchSpecialEnd = strHtml.indexOf("</p>", LunchSpecialStart);
//            String LunchSpecialFood = strHtml.substring(
//                    LunchSpecialStart + 7, LunchSpecialEnd);
//            strMenu += "중식(특식): " + LunchSpecialFood + "\n\n";
//
//            int LunchnowStart = strHtml.indexOf("-&nbsp;", LunchSpecialEnd);
//            int LunchnowEnd = strHtml.indexOf("</p>", LunchnowStart);
//            String LunchnowFood = strHtml.substring(
//                    LunchnowStart + 7, LunchnowEnd);
//            strMenu += "중식(특식): " + LunchnowFood + "\n\n";
//
//            int dinnerStart = strHtml.indexOf("-&nbsp;", LunchnowEnd);
//            int dinnerEnd = strHtml.indexOf("</p>", dinnerStart);
//            String dinnerFood = strHtml.substring(
//                    dinnerStart + 7, dinnerEnd);
//            strMenu += "중식(특식): " + dinnerFood + "\n\n";
//
//
//            tv.setText(strMenu);
//        }catch(Exception e){
//            tv.setText("파싱 오류");
//        }
//    }

