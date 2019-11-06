package com.example.misemengeparsing;



import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {
    TextView tv;
    String strHtml ="";
    Document doc;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=(TextView)findViewById(R.id.textView2);

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

                URL aURL = new URL("https://search.naver.com/search.naver?sm=tab_drt&where=nexearch&query=%EB%AF%B8%EC%84%B8%EB%A8%BC%EC%A7%80");
                BufferedReader in = new BufferedReader(new InputStreamReader(aURL.openStream() ));
                while((strLine = in.readLine()) != null)
                    if(strLine.contains("data-local-name"))
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
            int first, second = 0;
            int three, four = 0;


            for(int i=0; i<=16; i++){
                first = strHtml.indexOf("data-local-name", second);
                second = strHtml.indexOf("data-grade",first);
                strContent += strHtml.substring(first+17,second-2)+" : ";

                three = strHtml.indexOf("life_index", four);
                four = strHtml.indexOf("</span>",three);
                strContent += strHtml.substring(three+18,four)+"\n";
            }

            tv.setText("지역별 현재 미세먼지 측정값..."+"\n\n"+strContent);
        }catch (Exception e){
            tv.setText("파싱 에러: "+e.toString());
        }
    }
}


