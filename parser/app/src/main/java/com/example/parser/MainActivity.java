package com.example.parser;

import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler; // 동일한 이름의 클래스가 여러개 존재하기 때문에
import android.os.Message; // auto import 안됨 그래서
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;// 직접 써줘야함
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    String result = "";
    TextView tv;
    static int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) this.findViewById(R.id.textview);
        Handler h = new Handler() {
            public void  handleMessage(Message msg){

                tv.setText("전체 기사 수 = "+count+"\n"+result);
            }
        };
        new WorkerThread(h).start();
    }
    class WorkerThread extends  Thread{
        Handler h;

        WorkerThread(Handler h){
            this.h = h;
        }
        public void run(){
            try{
                URL url = new URL(
                        "http://biz.heraldcorp.com/common_prog/rssdisp.php?ct=010000000000.xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("item");
                count = nodeList.getLength();
                for(int i=0; i<nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    NodeList childNodeList = node.getChildNodes();

                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Node childNode = childNodeList.item(j);
                        if (childNode.getNodeName().equals("title"))
                            result += (i+1)+"번 기사 제목:" + childNode.getFirstChild().getNodeValue() + "\n";
                        if (childNode.getNodeName().equals("description"))
                            result += (i+1)+"번 기사 내용:" + childNode.getFirstChild().getNodeValue() + "\n";
                    }
                    result += "\n";
                }
                h.sendMessage(new Message());
            }catch (Exception e){
                tv.setText("파싱에러");
            }
        }
    }
}
