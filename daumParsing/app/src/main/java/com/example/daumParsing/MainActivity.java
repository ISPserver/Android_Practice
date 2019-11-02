package com.example.daumParsing;

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

        tv=(TextView)findViewById(R.id.textView2);
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
                URL aURL = new URL("https://www.daum.net/?mobile");
                BufferedReader in = new BufferedReader(new InputStreamReader(aURL.openStream() ));
                while((strLine = in.readLine()) != null)
                    if(strLine.contains("class=\"link_issue\"") && !strLine.contains("tabindex"))
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
            int start, end = 0;
            for(int i=1; i<=10; i++){
                start = strHtml.indexOf("class=\"link_issue\"", end);
                end = strHtml.indexOf("</a>",start);
                strContent += "실시간검색어"+i+"위: "+strHtml.substring(start+19,end)+"\n";
            }
            tv.setText(strContent);
        }catch (Exception e){
            tv.setText("파싱 에러: "+e.toString());
        }
    }
}