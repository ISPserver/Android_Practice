package com.example.customview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SearchActivity extends AppCompatActivity {
    TextView regiontext;
    TextView listtext;
    String region = "";
    Intent intent2;
    String searchurl = "";

    String [] regionList = new String[4];

    StringBuilder sb = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        regiontext = (TextView) findViewById(R.id.regiontext);
        listtext = (TextView) findViewById(R.id.listtext);
        final Intent intent = getIntent();

        region = intent.getExtras().getString("region");
        regiontext.setText(region);

        String temp = region + "지역명소"; //지역이름 넣기
        searchurl = "https://www.google.com/search?q=" + temp + "&oq=" + temp + "&aqs=chrome..69i57j0l6j69i61.3286j0j9&sourceid=chrome&ie=UTF-8";

        Button bt = (Button)findViewById(R.id.showRegion);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainPageTask().execute();
            }
        });

        //자세히버튼 누르면 넘어가게. ( 지역명소 list 랑 , 지역 이름).
        Button lastbt = (Button)findViewById(R.id.showbt);
        intent2 = new Intent(getApplicationContext(), thirdActivity.class);
        lastbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra("regionlist", regionList);
                intent2.putExtra("region", region);
                startActivity(intent2);
            }
        });
    }

    private class MainPageTask extends AsyncTask<Void, Void, Void>{
        private Elements elements;


        @Override
        protected Void doInBackground(Void... params){
            try{
                Document doc = Jsoup.connect(searchurl).get();
                elements = doc.select("div.gRTukd.T2uyV");

            }
            catch(IOException e){
                e.printStackTrace();
            }
            sb.append("\n");
            for(Element element : elements){
                sb.append(element.text()).append("\n\n");
            }

            //관광명소를 배열형태로 저장해두기위해.
            String temp= sb.toString();
            for(int i = 0; i < regionList.length; i++) {
                regionList = temp.split("\n");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            if(elements.text() == ""){
                listtext.setText("검색결과가 없습니다.");
            }
            else listtext.setText(sb.toString());
        }
    }
}
